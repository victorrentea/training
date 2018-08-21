<?php

namespace Emag\Core\CodeceptionBundle\Service;


use Doctrine\Common\Collections\ArrayCollection;
use Emag\Core\BaseBundle\Exception\AtfException;
use Emag\Core\CodeceptionBundle\Entity\Stack;
use Emag\Core\JobBundle\Entity\JobSchedule;
use Emag\Core\JobBundle\Service\JobScheduleService;
use Emag\Core\JobBundle\Service\JobService;

class ScheduleTriggerServiceTest extends \PHPUnit_Framework_TestCase
{
    /** @var ScheduleTriggerService */
    private $scheduleTriggerService;

    /** @var StackService|\PHPUnit_Framework_MockObject_MockObject $stackServiceMock */
    private $stackServiceMock;

    /** @var JobService|\PHPUnit_Framework_MockObject_MockObject $jobServiceMock */
    private $jobServiceMock;

    /** @var JobScheduleService|\PHPUnit_Framework_MockObject_MockObject $jobScheduleServiceMock */
    private $jobScheduleServiceMock;

    public function setUp() {
        $this->scheduleTriggerService = new ScheduleTriggerService();

        $this->stackServiceMock = $this->getMockBuilder(StackService::class)->disableOriginalConstructor()->getMock();

        $this->jobServiceMock = $this->getMockBuilder(JobService::class)->disableOriginalConstructor()->getMock();

        $this->jobScheduleServiceMock = $this->getMockBuilder(JobScheduleService::class)->disableOriginalConstructor()->getMock();

        $this->scheduleTriggerService->setStackService($this->stackServiceMock);
        $this->scheduleTriggerService->setJobService($this->jobServiceMock);
        $this->scheduleTriggerService->setJobScheduleService($this->jobScheduleServiceMock);
    }

    public function testHandleDeployWithStackFound() {
        $code = 'atf';

        $this->stackServiceMock->expects($this->once())->method('findOneBy')->with(['coreCode' => $code])->willReturn(new Stack());

        $schedules = new ArrayCollection([new JobSchedule(), new JobSchedule()]);
        $this->jobScheduleServiceMock->expects($this->once())->method('findBy')->willReturn($schedules);

        $this->jobServiceMock->expects($this->exactly($schedules->count()))->method('runTestPlan');

        $this->scheduleTriggerService->handleDeploy($code);
    }

    public function testHandleDeployWithStackNotFound() {
        $code = 'bogus';

        $this->stackServiceMock->expects($this->once())->method('findOneBy')->with(['coreCode' => $code])->willReturn(null);

        $this->jobScheduleServiceMock->expects($this->never())->method('findBy');

        $this->jobServiceMock->expects($this->never())->method('runTestPlan');

        $this->scheduleTriggerService->handleDeploy($code);
    }

    public function testHandleDeployWithStackFoundAndNoSchedules() {
        $code = 'atf';

        $this->stackServiceMock->expects($this->once())->method('findOneBy')->with(['coreCode' => $code])->willReturn(new Stack());

        $this->jobScheduleServiceMock->expects($this->once())->method('findBy')->willReturn(new ArrayCollection());

        $this->jobServiceMock->expects($this->never())->method('runTestPlan');

        $this->scheduleTriggerService->handleDeploy($code);
    }
}