<?php declare(strict_types=1);

namespace Tests\DefaultBundle\Service\Sap;

use Doctrine\ORM\EntityManager;
use Emag\SapBundle\Dto\Finance\FinancePaymentsChangingDto;
use Emag\SapBundle\Service\Exception\SapErrorException;
use Emag\SapBundle\Service\SapGateway;
use MktpFinance\DefaultBundle\Entity\Payment;
use MktpFinance\DefaultBundle\Entity\SapPaymentUpdates;
use MktpFinance\DefaultBundle\Repository\SapPaymentUpdatesRepository;
use MktpFinance\DefaultBundle\Service\MktpFinanceLoggerService;
use MktpFinance\DefaultBundle\Service\Sap\UpdateSapPaymentsDeadlineService;
use \Mockery as Mockery;
use PHPUnit\Framework\TestCase;
use Symfony\Component\EventDispatcher\EventDispatcherInterface;

/**
 * Class UpdateSapPaymentDeadlineServiceTest
 * @package Tests\DefaultBundle\Service
 */
class UpdateSapPaymentDeadlineServiceTest extends TestCase
{
    /** @var UpdateSapPaymentsDeadlineService */
    private $service;

    /** @var SapPaymentUpdates */
    private $update;

    public function setUp()
    {
        $manager = Mockery::mock(EntityManager::class);
        $manager->shouldReceive('persist');
        $manager->shouldReceive('flush');

        $eventDispatcher = Mockery::mock(EventDispatcherInterface::class);
        $eventDispatcher->shouldReceive('dispatch');

        $this->service = new UpdateSapPaymentsDeadlineService($eventDispatcher, 'RO', '123');
        $logger = Mockery::mock(MktpFinanceLoggerService::class);
        $logger->shouldReceive(
            [
                'info'  => true,
                'error' => true,
            ]
        );

        $sapGateway = Mockery::mock(SapGateway::class);
        $sapGateway->shouldReceive('saveFinancePaymentsChanging')->andReturn(
            ['MESSAGE' => 'test', 'RETURN_CODE' => 0, 'T_RETURN' => [['MESSAGE' => 'testMessage', 'BELNR' => '123']]]
        );

        $this->update = Mockery::mock(SapPaymentUpdates::class);
        $this->update->shouldReceive(
            [
                'getId'             => 1,
                'getPaymentId'      => 11,
                'getDueDate'        => new \DateTime(),
                'getPaymentBlock'   => false,
                'getPayment'        => new Payment(),
                'setProcessMessage' => $this->update,
                'setStatus'         => $this->update,
            ]
        );

        $sapUpdatesRepo = Mockery::mock(SapPaymentUpdatesRepository::class);
        $sapUpdatesRepo->shouldReceive('findBy')->andReturn([]);

        $manager = Mockery::mock(EntityManager::class);
        $manager->shouldReceive('getRepository')->with(SapPaymentUpdates::class)->andReturn($sapUpdatesRepo);
        $manager->shouldReceive('persist');
        $manager->shouldReceive('flush');
        $manager->shouldReceive('clear');

        $this->service->setLogger($logger);
        $this->service->setSapGateway($sapGateway);
        $this->service->setEntityManager($manager);
    }

    public function testProcessExceptionMessage(): void
    {
        $this->service->processExceptionMessage('test', $this->update);
    }

    public function testProcessSuccessMessage(): void
    {
        $this->service->processSuccessMessage(
            ['MESSAGE' => 'test', 'RETURN_CODE' => 0, 'T_RETURN' => [['MESSAGE' => 'testMessage', 'BELNR' => '123']]],
            $this->update
        );
    }

    public function testProcess(): void
    {
        $this->service->process();
    }

    public function testAddSapItem(): void
    {
        $this->service->addSapItem($this->update);
    }

    public function testSendDataToSapWithSapErrorException(): void
    {
        $sapGateway = Mockery::mock(SapGateway::class);
        $sapGateway->shouldReceive('saveFinancePaymentsChanging')->andThrow(new SapErrorException('test'));
        $this->service->setSapGateway($sapGateway);
        $this->service->sendDataToSap(new FinancePaymentsChangingDto(), $this->update);
    }

    public function testSendDataToSapException(): void
    {
        $sapGateway = Mockery::mock(SapGateway::class);
        $sapGateway->shouldReceive('saveFinancePaymentsChanging')->andThrow(new \Exception('test'));
        $this->service->setSapGateway($sapGateway);
        $this->service->sendDataToSap(new FinancePaymentsChangingDto(), $this->update);
    }

    public function tearDown()
    {
        Mockery::close();
    }
}
