<?php
namespace Emag\Core\JobBundle\Tests\Unit\EventListener;

use Doctrine\Common\Persistence\ObjectManager;
use Emag\Core\CodeceptionBundle\Entity\Stack;
use Emag\Core\JobBundle\Entity\Job;
use Emag\Core\JobBundle\Entity\JobInfo;
use Emag\Core\JobBundle\Event\JobEvent;
use Emag\Core\JobBundle\EventListener\JobExecutionListener;
use Emag\Core\JobBundle\Service\JobService;
use Symfony\Component\EventDispatcher\EventDispatcherInterface;

class JobExecutionListenerTest extends \PHPUnit_Framework_TestCase
{

    public function testOnJobInfoFinished()
    {
        $managerMock = $this->getMockBuilder(ObjectManager::class)->getMock();
        $jobSerice = new JobService();
        $jobSerice->setManager($managerMock);

        $jobExecutionListener = new JobExecutionListener($managerMock, $jobSerice);

        foreach ($this->getJobInfoEvents() as $arguments) {
            $arguments[0]->getJob()->setStartDate(new \DateTime('2017-03-04 12:44:32'));
            $jobExecutionListener->onJobInfoFinished($arguments[0]);
            $this->assertEquals($arguments[1], $arguments[0]->getJob()->getJobStatus());
        }
    }

    public function getJobInfoEvents()
    {
        yield [$this->createJobEvent([]), Job::STATUS_PENDING];
        yield [$this->createJobEvent([Job::STATUS_PASS]), Job::STATUS_PASS];
        yield [$this->createJobEvent([Job::STATUS_FAIL]), Job::STATUS_FAIL];
        yield [$this->createJobEvent([Job::STATUS_ERROR]), Job::STATUS_ERROR];
        // At least one jobInfo in progress => job not finished
        yield [$this->createJobEvent([Job::STATUS_IN_PROGRESS], false), Job::STATUS_IN_PROGRESS];
        yield [$this->createJobEvent([Job::STATUS_FAIL, Job::STATUS_PASS]), Job::STATUS_FAIL];
        yield [$this->createJobEvent([Job::STATUS_PASS, Job::STATUS_FAIL]), Job::STATUS_FAIL];
        yield [$this->createJobEvent([Job::STATUS_ERROR, Job::STATUS_PASS]), Job::STATUS_ERROR];
        yield [$this->createJobEvent([Job::STATUS_PASS, Job::STATUS_ERROR]), Job::STATUS_ERROR];
        yield [$this->createJobEvent([Job::STATUS_PASS, Job::STATUS_PASS, Job::STATUS_ERROR]), Job::STATUS_ERROR];
    }

    private function createJobEvent(array $jobInfoStatuses = [], $dispatchJobFinishedEvent = true)
    {
        $jobEvent = new JobEvent(null, new Stack(), null, null, null, null);
        $jobEvent->setJobInfo(new JobInfo);
        $jobEvent->setDispatcher($this->createEventDispatcherMock($jobEvent, $dispatchJobFinishedEvent));
        $job = new Job();
        $jobInfos = [];
        foreach ($jobInfoStatuses as $jobInfoStatus) {
            $jobInfo = new JobInfo();
            $jobInfos[] = $jobInfo->setJobInfoStatus($jobInfoStatus);
        }
        $job->setJobInfos($jobInfos);
        $jobEvent->setJob($job);

        return $jobEvent;
    }

    /**
     * @param $jobEvent
     * @param $dispatchJobFinishedEvent
     * @return EventDispatcherInterface|\PHPUnit_Framework_MockObject_MockObject
     */
    private function createEventDispatcherMock($jobEvent, $dispatchJobFinishedEvent)
    {
        $eventDispatcherMock = $this->getMockBuilder(EventDispatcherInterface::class)->getMock();

        if ($dispatchJobFinishedEvent) {
            $expectedCalls = $this->once();
        } else {
            $expectedCalls = $this->never();
        }
        $eventDispatcherMock->expects($expectedCalls)
            ->method('dispatch')
            ->with(
                JobEvent::FINISH_JOB_EVENT,
                $this->callback(function (JobEvent $event) use ($jobEvent) {
                    return $event == $jobEvent;
                })
            );

        return $eventDispatcherMock;
    }
}
