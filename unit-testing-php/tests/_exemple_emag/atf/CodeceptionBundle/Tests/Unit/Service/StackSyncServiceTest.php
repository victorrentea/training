<?php
namespace Emag\Core\CodeceptionBundle\Tests\Unit\Service;

use Emag\Core\CodeceptionBundle\Entity\Stack;
use Emag\Core\CodeceptionBundle\Service\StackService as AtfStackService;
use Emag\Core\CodeceptionBundle\Service\StackSyncService;
use Emag\Core\CoreApiBundle\Entity\StackApplication;
use Emag\Core\CoreApiBundle\Entity\StackInfo;
use Emag\Core\CoreApiBundle\Service\StackService as CoreStackService;
use Emag\Core\CodeceptionBundle\Service\DistributionService;

class StackSyncServiceTest extends \PHPUnit_Framework_TestCase
{
    /**
     * @var StackSyncService
     */
    private $stackSyncService;

    /**
     * @var CoreStackService|\PHPUnit_Framework_MockObject_MockObject
     */
    private $coreStackServiceMock;

    /**
     * @var DistributionService|\PHPUnit_Framework_MockObject_MockObject
     */
    private $distributionServiceMock;

    /**
     * @var AtfStackService|\PHPUnit_Framework_MockObject_MockObject
     */
    private $stackServiceMock;

    protected function setUp()
    {
        $this->coreStackServiceMock = $this->getMockBuilder(CoreStackService::class)
                                           ->disableOriginalConstructor()
                                           ->getMock();

        $this->stackServiceMock = $this->getMockBuilder(AtfStackService::class)
                                       ->disableOriginalConstructor()
                                       ->getMock();

        $this->distributionServiceMock = $this->getMockBuilder(DistributionService::class)
                                            ->disableOriginalConstructor()
                                            ->getMock();

        $this->distributionServiceMock
            ->expects($this->any())
            ->method('filterStackCoreDistributions')
            ->will($this->returnCallback(function($coreDistributions) {
                return $coreDistributions;
            }));

        $this->stackSyncService = new StackSyncService();
        $this->stackSyncService->setCoreStackService($this->coreStackServiceMock);
        $this->stackSyncService->setStackService($this->stackServiceMock);
        $this->stackSyncService->setDistributionService($this->distributionServiceMock);

    }

    public function testImportStackWillReturnFalseIfCodeIsNotFoundInCore()
    {
        $code = 'no-such-stack';
        $this->coreStackServiceMock
            ->expects($this->once())
            ->method('getStack')
            ->with($code)
            ->willReturn(null);

        $ret = $this->stackSyncService->importStack($code);

        $this->assertFalse($ret);
    }

    public function testImportStackWillReturnFalseIfStackHasNoApplicationsYet()
    {
        $code = 'empty-stack';
        $this->coreStackServiceMock
            ->expects($this->once())
            ->method('getStack')
            ->with($code)
            ->willReturn(new StackInfo());

        $this->coreStackServiceMock
            ->expects($this->once())
            ->method('getStackApplications')
            ->with($code)
            ->willReturn([]);

        $ret = $this->stackSyncService->importStack($code);

        $this->assertFalse($ret);
    }

    public function testImportStackWillTryToSaveTheStackWhenCoreStackAndApplicationsAreAvailable()
    {
        $code = 'my-stack';
        $this->coreStackServiceMock
            ->expects($this->once())
            ->method('getStack')
            ->with($code)
            ->willReturn(new StackInfo());

        $stackApps = ['app1', 'app2'];
        $this->coreStackServiceMock
            ->expects($this->once())
            ->method('getStackApplications')
            ->with($code)
            ->willReturn($stackApps);
        $allocationTokens = [];
        foreach ($stackApps as $stackApp) {
            $allocationTokens["$stackApp:http-ext"] = "http://$stackApp.emag.network";
        }

        $this->coreStackServiceMock
            ->expects($this->once())
            ->method('getStackAllocationTokens')
            ->with($code)
            ->willReturn($allocationTokens);

        $newStack = new Stack();
        $this->stackServiceMock
            ->expects($this->once())
            ->method('addStack')
            ->willReturn($newStack);

        $ret = $this->stackSyncService->importStack($code);

        $this->assertInstanceOf(Stack::class, $ret);
    }

    public function testRemoveStackWillReturnFalseWhenThereIsNoStackWithThatCode()
    {
        $code = 'no-such-stack';
        $this->stackServiceMock
            ->expects($this->once())
            ->method('findOneBy')
            ->with(['coreCode' => $code])
            ->willReturn(null);

        $ret = $this->stackSyncService->removeStack($code);

        $this->assertFalse($ret);
    }

    public function testRemoveStackWillDeleteAnExistingStackAndReturnTrue()
    {
        $code = 'existing-stack';
        $existingStack = new Stack();
        $this->stackServiceMock
            ->expects($this->once())
            ->method('findOneBy')
            ->with(['coreCode' => $code])
            ->willReturn($existingStack);

        $this->stackServiceMock
            ->expects($this->once())
            ->method('deleteStack')
            ->with($existingStack);

        $ret = $this->stackSyncService->removeStack($code);

        $this->assertTrue($ret);
    }
}
