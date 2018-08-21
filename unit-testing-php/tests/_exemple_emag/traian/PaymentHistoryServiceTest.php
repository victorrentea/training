<?php declare(strict_types=1);

namespace Tests\DefaultBundle\Service\Payment;

use MktpFinance\DefaultBundle\Entity\Payment;
use MktpFinance\DefaultBundle\Entity\PaymentLogEntry;
use MktpFinance\DefaultBundle\Service\Payment\PaymentHistoryService;
use Symfony\Bundle\FrameworkBundle\Test\KernelTestCase;

/**
 * Class PaymentHistoryServiceTest
 * @package Tests\DefaultBundle\Service\Payment
 */
class PaymentHistoryServiceTest extends KernelTestCase
{
    /** @var PaymentHistoryService $service */
    private $service;

    public function setUp()
    {
        self::bootKernel();
        $this->service = new PaymentHistoryService();
    }

    /**
     * @throws \ReflectionException
     */
    public function testParsePaymentHistories()
    {
        $em = static::$kernel->getContainer()->get('doctrine')->getManager('mktp_finance_slave');
        $payment = $em->getRepository(Payment::class)->findOneBy([]);
        $paymentHistories = $em->getRepository(PaymentLogEntry::class)->getLogEntries($payment);
        $this->service->parsePaymentHistories([]);
        $this->service->parsePaymentHistories($paymentHistories);
    }
}