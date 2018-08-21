<?php declare(strict_types=1);

namespace Tests\DefaultBundle\Service\Payment;

use Doctrine\ORM\EntityManager;
use MktpFinance\DefaultBundle\Entity\FileSource;
use MktpFinance\DefaultBundle\Entity\FileUpload;
use MktpFinance\DefaultBundle\Entity\IntermediaryBankAccount;
use MktpFinance\DefaultBundle\Entity\MktpOrder;
use MktpFinance\DefaultBundle\Entity\Order;
use MktpFinance\DefaultBundle\Entity\OrderAwb;
use MktpFinance\DefaultBundle\Entity\Payment;
use MktpFinance\DefaultBundle\Entity\SapPayment;
use MktpFinance\DefaultBundle\Entity\Seller;
use MktpFinance\DefaultBundle\Entity\SellerPayout;
use MktpFinance\DefaultBundle\Exception\InvalidArgumentException;
use MktpFinance\DefaultBundle\Exception\LogicException;
use MktpFinance\DefaultBundle\Repository\IntermediaryBankAccountRepository;
use MktpFinance\DefaultBundle\Repository\OrderAwbRepository;
use MktpFinance\DefaultBundle\Repository\OrderRepository;
use MktpFinance\DefaultBundle\Repository\PaymentRepository;
use MktpFinance\DefaultBundle\Repository\SapPaymentRepository;
use MktpFinance\DefaultBundle\Repository\SellerRepository;
use MktpFinance\DefaultBundle\Service\Lock\LockService;
use MktpFinance\DefaultBundle\Service\MktpFinanceLoggerService;
use MktpFinance\DefaultBundle\Service\Payment\PaymentService;
use MktpFinance\DefaultBundle\Service\Payment\PaymentValidatorService;
use MktpFinance\DefaultBundle\Service\PaymentParsing\FileService;
use MktpFinance\DefaultBundle\Service\PaymentParsing\PaymentFileParsingService;
use MktpFinance\DefaultBundle\Service\PaymentParsing\PaymentParsingValidatorService;
use MktpFinance\DefaultBundle\Service\PaymentPayoutMatchingService;
use MktpFinance\DefaultBundle\Service\RabbitMqService;
use MktpFinance\DefaultBundle\Service\Sap\SapService;
use Mockery\MockInterface;
use Predis\Client;
use Psr\Log\LoggerInterface;
use Symfony\Bundle\FrameworkBundle\Test\KernelTestCase;
use Symfony\Component\EventDispatcher\EventDispatcherInterface;
use Symfony\Component\Security\Core\Authentication\Token\Storage\TokenStorageInterface;
use Symfony\Component\Security\Core\Authentication\Token\TokenInterface;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Component\Translation\Translator;

/**
 * Class PaymentServiceTest
 * @package Tests\DefaultBundle\Service\Payment
 */
class PaymentServiceTest extends KernelTestCase
{
    /** @var string */
    public $testPath;

    /** @var \stdClass */
    protected $fullCodMessage;

    /** @var \stdClass */
    protected $fullCoMessage;

    /** @var EntityManager|MockInterface */
    private $em;

    /** @var SapService */
    private $sap;

    /** @var PaymentService $service */
    private $service;

    public function setUp()
    {
        self::bootKernel();

        $this->testPath = static::$kernel->getRootDir()
            . '/../tests/MktpFinance/DefaultBundle/Service/PaymentParsing/Files/';

        $this->fullCodMessage = (object) [
            'order_id'          => 1,
            'rma_id'            => 1,
            'message_type'      => Payment::PAYMENT_TYPE_REFUND_COD,
            'vendor_id'         => 9,
            'is_mktp_finance'   => true,
            'refund_request_id' => 1,
            'amount'            => 1,
            'is_paid'           => 1,
        ];

        $this->fullCoMessage = (object) [
            'order_id'      => 1,
            'rma_id'        => 1,
            'message_type'  => Payment::PAYMENT_TYPE_REFUND_CO,
            'vendor_orders' => [
                (object) [
                    'vendor_id'          => 9,
                    'merchant_id'        => 'MKTPFINN',
                    'second_merchant_id' => 123,
                    'terminal_bank'      => 'BCR',
                    'sum'                => '100',
                ],
            ],
        ];


        $seller = \Mockery::mock(Seller::class);
        $seller->shouldReceive(
            [
                'isBlack'                     => false,
                'isNotEligibleForMktpFinance' => false,
                'getDisplayName'              => 'Test',
                'getPaymentDeadlineCo'        => Seller::PAYMENT_DEADLINE_DAILY,
                'getPaymentDeadlineCod'       => Seller::PAYMENT_DEADLINE_DAILY,
                'getMktpSellerId'             => 9,
                'getSupplierId'               => 1,
            ]
        );

        $sellerRepo = \Mockery::mock(SellerRepository::class);
        $sellerRepo->shouldReceive('getByMktpSellerId')->andReturn($seller);
        $sellerRepo->shouldReceive('findOneByMktpSellerId')->andReturn($seller);

        $order = \Mockery::mock(Order::class);
        $order->shouldReceive(
            [
                'getId'                  => 1,
                'getEmagOrderId'         => 1,
                'getSuborderPaymentMode' => Order::PAYMENT_MODE_CARD,
                'getSeller'              => $seller,
                'getMerchantExternalId'  => 1,
                'addPayment'             => $order,
            ]
        );

        $orderRepo = \Mockery::mock(OrderRepository::class);
        $orderRepo->shouldReceive('findOneBy')->andReturn($order);
        $orderRepo->shouldReceive('findByOrderAndSeller')->andReturn($order);

        $payment = \Mockery::mock(Payment::class);
        $payment->shouldReceive(
            [
                'getId'                  => 1,
                'getSeller'              => $seller,
                'getIntermediaryAccount' => new IntermediaryBankAccount(),
                'getTerminalBank'        => 'BCR',
                'setTerminalBank'        => $payment,
                'setFinancialStatus'     => $payment,
                'setCashingDate'         => $payment,
                'setCashedValue'         => $payment,
                'setSupplierId'          => $payment,
                'isCashed'               => false,
            ]
        );

        $paymentRepo = \Mockery::mock(PaymentRepository::class);
        $paymentRepo->shouldReceive('findOneBy')->andReturn($payment);

        $interBank = \Mockery::mock(IntermediaryBankAccount::class);

        $interRepo = \Mockery::mock(IntermediaryBankAccountRepository::class);
        $interRepo->shouldReceive('getByTerminalBank')->andReturn($interBank);

        $awbRepo = \Mockery::mock(OrderAwbRepository::class);
        $awbRepo->shouldReceive('findOneBy')->andReturn(null);

        $spRepo = \Mockery::mock(SapPaymentRepository::class);
        $spRepo->shouldReceive('findOneBy')->andReturn(null);

        $this->em = \Mockery::mock(EntityManager::class);
        $this->em->shouldReceive('getRepository')->with(Seller::class)->andReturn($sellerRepo)->byDefault();
        $this->em->shouldReceive('getRepository')->with(Order::class)->andReturn($orderRepo)->byDefault();
        $this->em->shouldReceive('getRepository')->with(Payment::class)->andReturn($paymentRepo)->byDefault();
        $this->em->shouldReceive('getRepository')->with(OrderAwb::class)->andReturn($awbRepo)->byDefault();
        $this->em->shouldReceive('getRepository')->with(SapPayment::class)->andReturn($spRepo)->byDefault();
        $this->em->shouldReceive('getRepository')->with(IntermediaryBankAccount::class)
            ->andReturn($interRepo)->byDefault();
        $this->em->shouldReceive('persist', 'flush', 'clear');

        $this->sap = \Mockery::mock(SapService::class);
        $this->sap->shouldReceive(
            [
                'saveSapPayment'        => new SapPayment(),
                'reverseSapPayment'     => $this->sap,
                'saveSapCourierPayment' => $this->sap,
            ]
        );
    }

    private function instantiateService(): void
    {
        $trans = \Mockery::mock(Translator::class);
        $trans->shouldReceive('trans');

        $pvs = \Mockery::mock(PaymentValidatorService::class);
        $pvs->shouldReceive('isValidPayment')->andReturn(true);

        $pfps = static::$kernel->getContainer()->get(PaymentFileParsingService::ID);

        $ed = \Mockery::mock(EventDispatcherInterface::class);
        $ed->shouldReceive('dispatch');

        $log = \Mockery::mock(LoggerInterface::class);
        $log->shouldReceive('info', 'debug', 'warning', 'error');

        $flog = \Mockery::mock(MktpFinanceLoggerService::class);
        $flog->shouldReceive('info', 'debug', 'warning', 'error');

        $ppv = static::$kernel->getContainer()->get(PaymentParsingValidatorService::ID);

        $rbmq = \Mockery::mock(RabbitMqService::class);
        $rbmq->shouldReceive('publishRefreshOrderMessage');
        $rbmq->shouldReceive('publishSendAwbToInvoicingMessage');

        $user = \Mockery::mock(UserInterface::class);
        $user->shouldReceive('getUserName')->andReturn('test');

        $token = \Mockery::mock(TokenInterface::class);
        $token->shouldReceive('getUser')->andReturn($user);

        $ts = \Mockery::mock(TokenStorageInterface::class);
        $ts->shouldReceive('getToken')->andReturn($ts);
        $ts->shouldReceive('getUsername')->andReturn('test');

        $this->service = new PaymentService(
            $this->em,
            $this->em,
            $trans,
            $this->sap,
            $pvs,
            $pfps,
            $ed,
            $log,
            $log,
            $ppv,
            $flog,
            $rbmq,
            $ts,
            'MKTPFINN'
        );

        $this->service->setLockService(new LockService(new Client(), 99));

        $fs = \Mockery::mock(FileService::class);
        $fs->shouldReceive('moveOnDuplicateAppendName')->andReturn('test');

        $this->service->setFileService($fs);

        $ppms = static::$kernel->getContainer()->get(PaymentPayoutMatchingService::SERVICE_NAME);

        $this->service->setPaymentPayoutMatchingService($ppms);
    }

    public function testCreateCardOnlinePayment(): void
    {
        $this->instantiateService();

        $order = \Mockery::mock(Order::class);
        $order->shouldReceive(
            [
                'getEmagOrderId'           => 1,
                'getSeller'                => new Seller(),
                'getSuborderCapturedValue' => 1,
                'getMerchantInternalId'    => 1,
                'getMerchantExternalId'    => 1,
                'getPaymentDeadline'       => 1,
                'addPayment'               => $order,
            ]
        );
        $mktpOrder = \Mockery::mock(MktpOrder::class);
        $mktpOrder->shouldReceive(
            [
                'getTerminalBank'        => 'BCR',
                'getIntermediaryAccount' => new IntermediaryBankAccount(),
            ]
        );
        $this->assertInstanceOf(Payment::class, $this->service->createCardOnlinePayment($order, $mktpOrder));
    }

    public function testCreateCodPayment(): void
    {
        $this->instantiateService();

        $order = \Mockery::mock(Order::class);
        $order->shouldReceive(
            [
                'getEmagOrderId'        => 1,
                'getSeller'             => new Seller(),
                'getMerchantInternalId' => 1,
                'getMerchantExternalId' => 1,
                'getPaymentDeadline'    => 1,
                'addPayment'            => $order,
            ]
        );
        $this->assertInstanceOf(Payment::class, $this->service->createCodPayment($order, 1, 1));
    }

    /**
     * @throws \Exception
     */
    public function testInsertPaymentWithBlackException(): void
    {
        $seller = \Mockery::mock(Seller::class);
        $seller->shouldReceive('isBlack')->andReturn(true);

        $sellerRepo = \Mockery::mock(SellerRepository::class);
        $sellerRepo->shouldReceive('getByMktpSellerId')->andReturn($seller);

        $this->em->shouldReceive('getRepository')->with(Seller::class)->andReturn($sellerRepo);

        $this->instantiateService();

        $this->expectException(InvalidArgumentException::class);
        $this->service->insertPayment(new Payment(), 9);
    }

    /**
     * @throws \Exception
     */
    public function testInsertPaymentWithOrderException(): void
    {
        $repo = \Mockery::mock(OrderRepository::class);
        $repo->shouldReceive('findOneBy');

        $this->em->shouldReceive('getRepository')->with(Order::class)->andReturn($repo);

        $this->instantiateService();

        $this->expectException(InvalidArgumentException::class);
        $this->service->insertPayment(new Payment(), 9);
    }

    /**
     * @throws \Exception
     */
    public function testInsertPaymentWithOrderPaymentModeCod(): void
    {
        $order = \Mockery::mock(Order::class);
        $order->shouldReceive(
            [
                'getId'                  => 1,
                'getEmagOrderId'         => 1,
                'getSuborderPaymentMode' => Order::PAYMENT_MODE_RAMBURS,
                'getSeller'              => new Seller(),
                'getMerchantExternalId'  => 1,
                'addPayment'             => $order,
            ]
        );

        $repo = \Mockery::mock(OrderRepository::class);
        $repo->shouldReceive('findOneBy')->andReturn($order);

        $this->em->shouldReceive('getRepository')->with(Order::class)->andReturn($repo);

        $this->instantiateService();

        $this->service->insertPayment(new Payment(), 9);
    }

    /**
     * @dataProvider insertPaymentDataProvider
     *
     * @param array $data
     *
     * @throws \Exception
     */
    public function testInsertPayment(array $data): void
    {
        $this->instantiateService();

        $payment = \Mockery::mock(Payment::class);
        $payment->shouldReceive(
            [
                'getId'                    => 1,
                'getEmagOrderId'           => 1,
                'isRefundCO'               => $data['isRefundCO'],
                'setIntermediaryAccount'   => $payment,
                'setTerminalBank'          => $payment,
                'setOrder'                 => $payment,
                'setMktpSellerId'          => $payment,
                'setMerchantExternalId'    => $payment,
                'setSellerPaymentDeadline' => $payment,
                'setFinancialStatus'       => $payment,
                'setCashingDate'           => $payment,
                'setCashedValue'           => $payment,
                'setSupplierId'            => $payment,
                'isMoneyOrder'             => $data['isMoneyOrder'],
                'getCapturedValue'         => 1,
            ]
        );
        $this->service->insertPayment($payment, 9);
    }

    /**
     * @depends testInsertPayment
     *
     * @return array
     */
    public function insertPaymentDataProvider(): array
    {
        return [
            [
                [
                    'isRefundCO'   => true,
                    'isMoneyOrder' => false,
                ],
            ],
            [
                [
                    'isRefundCO'   => false,
                    'isMoneyOrder' => true,
                ],
            ],
        ];
    }

    /**
     * @throws \Exception
     */
    public function testProcessRefundsWithOrderInvalidArgumentException(): void
    {
        $repo = \Mockery::mock(OrderRepository::class);
        $repo->shouldReceive('findByOrderAndSeller');

        $this->em->shouldReceive('getRepository')->with(Order::class)->andReturn($repo);

        $this->instantiateService();

        $message = (object) [
            'order_id'          => 1,
            'rma_id'            => 1,
            'message_type'      => Payment::PAYMENT_TYPE_REFUND_COD,
            'vendor_id'         => 9,
            'is_mktp_finance'   => true,
            'refund_request_id' => 1,
            'amount'            => 1,
            'is_paid'           => 1,
        ];

        $this->expectException(InvalidArgumentException::class);
        $this->service->processRefunds($message);
    }

    /**
     * @throws \Exception
     */
    public function testProcessRefundsWithCashedPayment(): void
    {
        $payment = \Mockery::mock(Payment::class);
        $payment->shouldReceive(
            [
                'getId'                  => 1,
                'getSeller'              => new Seller(),
                'getIntermediaryAccount' => new IntermediaryBankAccount(),
                'getTerminalBank'        => 'BCR',
                'setTerminalBank'        => $payment,
                'setFinancialStatus'     => $payment,
                'setCashingDate'         => $payment,
                'setCashedValue'         => $payment,
                'setSupplierId'          => $payment,
                'isCashed'               => true,
            ]
        );

        $paymentRepo = \Mockery::mock(PaymentRepository::class);
        $paymentRepo->shouldReceive('findOneBy')->andReturn($payment);

        $this->em->shouldReceive('getRepository')->with(Payment::class)->andReturn($paymentRepo);

        $this->instantiateService();

        $this->service->processRefunds($this->fullCodMessage);
    }

    /**
     * @throws \Exception
     */
    public function testProcessRefundsWithNewPayment(): void
    {
        $paymentRepo = \Mockery::mock(PaymentRepository::class);
        $paymentRepo->shouldReceive('findOneBy');

        $this->em->shouldReceive('getRepository')->with(Payment::class)->andReturn($paymentRepo);

        $this->instantiateService();

        $this->service->processRefunds($this->fullCodMessage);
    }

    /**
     * @throws \Exception
     */
    public function testProcessRefundsCoWithNoOrder(): void
    {
        $repo = \Mockery::mock(OrderRepository::class);
        $repo->shouldReceive('findByOrderAndSeller');

        $this->em->shouldReceive('getRepository')->with(Order::class)->andReturn($repo);

        $this->instantiateService();

        $this->service->processRefunds($this->fullCoMessage);
    }

    /**
     * @throws \Exception
     */
    public function testProcessRefundsCoWithNewPayment(): void
    {
        $paymentRepo = \Mockery::mock(PaymentRepository::class);
        $paymentRepo->shouldReceive('findOneBy');

        $this->em->shouldReceive('getRepository')->with(Payment::class)->andReturn($paymentRepo);

        $this->instantiateService();

        $this->service->processRefunds($this->fullCoMessage);
    }

    /**
     * @dataProvider processRefundsDataProvider
     *
     * @param array $data
     *
     * @throws \Exception
     */
    public function testProcessRefunds(array $data): void
    {
        $this->instantiateService();

        if (isset($data[1])) {
            $this->expectException($data[1]);
        }
        $this->service->processRefunds($data[0]);
    }

    /**
     * @depends testProcessRefunds
     *
     * @return array
     */
    public function processRefundsDataProvider(): array
    {
        return [
            [
                [
                    (object) [],
                    LogicException::class,
                ],
            ],
            [
                [
                    (object) [
                        'order_id' => 1,
                    ],
                    LogicException::class,
                ],
            ],
            [
                [
                    (object) [
                        'order_id' => 1,
                        'rma_id'   => 1,
                    ],
                    LogicException::class,
                ],
            ],
            [
                [
                    (object) [
                        'order_id'     => 1,
                        'rma_id'       => 1,
                        'message_type' => 'test',
                    ],
                    InvalidArgumentException::class,
                ],
            ],
            [
                [
                    (object) [
                        'order_id'     => 1,
                        'rma_id'       => 1,
                        'message_type' => Payment::PAYMENT_TYPE_REFUND_COD,
                    ],
                    InvalidArgumentException::class,
                ],
            ],
            [
                [
                    (object) [
                        'order_id'          => 1,
                        'rma_id'            => 1,
                        'message_type'      => Payment::PAYMENT_TYPE_REFUND_COD,
                        'vendor_id'         => 9,
                        'refund_request_id' => 1,
                    ],
                    InvalidArgumentException::class,
                ],
            ],
            [
                [
                    (object) [
                        'order_id'          => 1,
                        'rma_id'            => 1,
                        'message_type'      => Payment::PAYMENT_TYPE_REFUND_COD,
                        'vendor_id'         => 9,
                        'is_mktp_finance'   => true,
                        'refund_request_id' => 1,
                        'amount'            => 1,
                        'is_paid'           => 1,
                    ],
                ],
            ],
            [
                [
                    (object) [
                        'order_id'      => 1,
                        'rma_id'        => 1,
                        'message_type'  => Payment::PAYMENT_TYPE_REFUND_CO,
                        'vendor_orders' => [(object) []],
                    ],
                ],
            ],
            [
                [
                    (object) [
                        'order_id'      => 1,
                        'rma_id'        => 1,
                        'message_type'  => Payment::PAYMENT_TYPE_REFUND_CO,
                        'vendor_orders' => [(object) ['vendor_id' => 9, 'merchant_id' => 'test']],
                    ],
                ],
            ],
            [
                [
                    (object) [
                        'order_id'      => 1,
                        'rma_id'        => 1,
                        'message_type'  => Payment::PAYMENT_TYPE_REFUND_CO,
                        'vendor_orders' => [(object) ['vendor_id' => 9, 'merchant_id' => 'MKTPFINN']],
                    ],
                ],
            ],
            [
                [
                    (object) [
                        'order_id'     => 1,
                        'rma_id'       => 1,
                        'message_type' => Payment::PAYMENT_TYPE_RETURN_COD,
                    ],
                    InvalidArgumentException::class,
                ],
            ],
            [
                [
                    (object) [
                        'order_id'      => 1,
                        'rma_id'        => 1,
                        'seller_id'     => 9,
                        'mktp_order_id' => 1,
                        'amount'        => 1,
                        'reservation'   => 1,
                        'awb_number'    => 'TEST',
                        'message_type'  => Payment::PAYMENT_TYPE_RETURN_COD,
                    ],
                ],
            ],
        ];
    }

    public function testProcessPaymentFileWithInvalidFile(): void
    {
        $this->instantiateService();

        $file = \Mockery::mock(FileUpload::class);
        $file->shouldReceive(
            [
                'getFilePath'         => '___test___',
                'getId'               => 1,
                'getOriginalFileName' => 'test',
                'setStatus'           => $file,
                'setProcessMessage'   => $file,
                'getSource'           => (new FileSource()),
            ]
        );

        $this->service->processPaymentFile($file);
    }

    public function testProcessPaymentFileWithPayUFile(): void
    {
        $this->instantiateService();

        $file = \Mockery::mock(FileUpload::class);
        $file->shouldReceive(
            [
                'getFilePath'         => $this->testPath . 'payu_demo.xlsx',
                'getId'               => 1,
                'getOriginalFileName' => 'test',
                'setStatus'           => $file,
                'setProcessMessage'   => $file,
                'getSource'           => (new FileSource())->setSource(FileSource::PAYU),
            ]
        );

        $this->service->processPaymentFile($file);
    }

    public function testProcessPaymentFileWithCourierFile(): void
    {
        $this->instantiateService();

        $file = \Mockery::mock(FileUpload::class);
        $file->shouldReceive(
            [
                'getFilePath'         => $this->testPath . 'fancurier.csv',
                'getId'               => 1,
                'getOriginalFileName' => 'test',
                'setStatus'           => $file,
                'setProcessMessage'   => $file,
                'getSource'           => (new FileSource())->setSource(FileSource::FAN_CURIER),
            ]
        );

        $this->service->processPaymentFile($file);
    }

    public function testConvertTabbedTextFiles(): void
    {
        $this->instantiateService();
        $testFile = sys_get_temp_dir() . '/tabbed.csv';
        copy($this->testPath . 'tabbed.csv', $testFile);

        $this->service->convertTabbedTextFiles($testFile);

        @unlink($testFile);
    }

    public function testUndoCashingWithInvalidPayout(): void
    {
        $this->instantiateService();

        $payment = (new Payment())->setSellerPayout((new SellerPayout())->setStatus(SellerPayout::STATUS_SENT_TO_SAP));

        $this->expectException(LogicException::class);
        $this->service->undoCashing($payment);
    }

    public function testUndoCashingWithNoLastSapPayment(): void
    {
        $this->instantiateService();

        $payment = (new Payment())->setSellerPayout((new SellerPayout())->setStatus(SellerPayout::STATUS_OPEN))
            ->setPaymentType(Payment::PAYMENT_TYPE_PAYMENT);

        $this->expectException(LogicException::class);
        $this->service->undoCashing($payment);
    }

    public function testUndoCashing(): void
    {
        $spRepo = \Mockery::mock(SapPaymentRepository::class);
        $spRepo->shouldReceive('findOneBy')->andReturn(new SapPayment());
        $this->em->shouldReceive('getRepository')->with(SapPayment::class)->andReturn($spRepo);

        $this->instantiateService();

        $payment = (new Payment())->setSellerPayout((new SellerPayout())->setStatus(SellerPayout::STATUS_OPEN))
            ->setPaymentType(Payment::PAYMENT_TYPE_PAYMENT)
            ->setOrder(new Order());

        $this->service->undoCashing($payment);
    }

    public function testUndoCashingWithStorno(): void
    {
        $spRepo = \Mockery::mock(SapPaymentRepository::class);
        $spRepo->shouldReceive('findOneBy')->andReturn(new SapPayment());
        $this->em->shouldReceive('getRepository')->with(SapPayment::class)->andReturn($spRepo);

        $this->instantiateService();

        $payment = (new Payment())->setSellerPayout((new SellerPayout())->setStatus(SellerPayout::STATUS_FINALIZED))
            ->setPaymentType(Payment::PAYMENT_TYPE_PAYMENT)
            ->setOrder(new Order());

        $this->service->undoCashing($payment);
    }

    public function tearDown()
    {
        \Mockery::close();
        parent::tearDown();
    }
}