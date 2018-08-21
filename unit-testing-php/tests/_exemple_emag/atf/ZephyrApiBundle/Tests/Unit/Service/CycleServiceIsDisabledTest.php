<?php
namespace Emag\Core\ZephyrApiBundle\Tests\Unit\Service;

use Emag\Core\ZephyrApiBundle\Service\CycleService;
use Guzzle\Http\Client;

class CycleServiceIsDisabledTest extends \PHPUnit_Framework_TestCase
{
    /**
     * @var CycleService
     */
    private $cycleService;

    /**
     * @var Client|\PHPUnit_Framework_MockObject_MockObject
     */
    private $clientMock;

    public function testCycleServiceWillNotDoAnyRequestsWhenDisabled()
    {
        $this->clientMock
            ->expects($this->never())
            ->method('get');
        $this->clientMock
            ->expects($this->never())
            ->method('post');
        $this->clientMock
            ->expects($this->never())
            ->method('put');
        $this->clientMock
            ->expects($this->never())
            ->method('delete');

        $this->assertNull($this->cycleService->addTestsToCycle(1, 2, []));
        $this->assertNull($this->cycleService->create('Cycle', 2));
        $this->assertNull($this->cycleService->deleteExecution(123));
        $this->assertNull($this->cycleService->get('key'));
        $this->assertNull($this->cycleService->removeTestsFromCycle(1, []));
        $this->assertNull($this->cycleService->update(1, 'Cycle'));
    }

    protected function setUp()
    {
        $this->clientMock = $this->getMockBuilder(Client::class)
            ->disableOriginalConstructor()->getMock();
        $this->cycleService = new CycleService($this->clientMock, false);
    }
}
