<?php

namespace Emag\Core\NotificationBundle\Tests\Unit\EventListener;

use Emag\Core\CodeceptionBundle\Entity\Distribution;
use Emag\Core\CodeceptionBundle\Entity\Stack;
use Emag\Core\CodeceptionBundle\Entity\Test;
use Emag\Core\JobBundle\Entity\Job;
use Emag\Core\JobBundle\Entity\JobInfo;
use Emag\Core\JobBundle\Entity\JobSchedule;
use Emag\Core\JobBundle\Event\JobEvent;
use Emag\Core\NotificationBundle\EventListener\JobListener;
use Emag\Core\NotificationBundle\Notifier\Mailer;
use Emag\Core\UserBundle\Entity\User;
use OldSound\RabbitMqBundle\RabbitMq\ProducerInterface;
use Symfony\Component\Templating\EngineInterface;

class JobListenerTest extends \PHPUnit_Framework_TestCase
{
    public const USER_EMAIL = 'user.email@example.com';
    public const NOTIFICATION_EMAIL = 'notification.email@example.com';
    public const CORE_TEST = 'core-test';

    /**
     * @var Mailer|\PHPUnit_Framework_MockObject_MockObject $mailerMock
     */
    private $mailerMock;

    /**
     * @var EngineInterface|\PHPUnit_Framework_MockObject_MockObject $engineMock
     */
    private $engineMock;

    /**
     * @var ProducerInterface|\PHPUnit_Framework_MockObject_MockObject $publisherMock
     */
    private $publisherMock;

    /**
     * @var JobListener
     */
    private $jobListener;

    protected function setUp()
    {
        $this->mailerMock = $this->getMockBuilder(Mailer::class)->disableOriginalConstructor()->getMock();
        $this->engineMock = $this->getMockBuilder(EngineInterface::class)->getMock();
        $this->publisherMock = $this->getMockBuilder(ProducerInterface::class)->getMock();

        $this->jobListener = new JobListener($this->mailerMock, $this->engineMock, $this->publisherMock, static::CORE_TEST);
    }

    public function testOnJobFinishedWillSendAnEmailToTheUsersEmailWhenTheJobWasTriggeredManually()
    {
        $user = new User;
        $user->setEmail(JobListenerTest::USER_EMAIL);
        $job = new Job;
        $job->setUser($user);
        $jobEvent = new JobEvent(null, new Stack(), null, null, null);
        $jobEvent->setJob($job);
        $jobEvent->setJobInfo(new JobInfo);

        $this->engineMock->expects($this->once())
            ->method('render')
            ->with(
                'EmagCoreNotificationBundle:Email:job-finished.html.twig',
                ['job' => $job]
            )
            ->willReturn('email body');

        $this->mailerMock->expects($this->once())
            ->method('send')
            ->with(JobListenerTest::USER_EMAIL, 'Job finished', 'email body');

        $this->jobListener->onJobFinished($jobEvent);
    }

    public function testOnJobFinishedWillSendAnEmailToTheJobNotificationEmailWhenTheJobWasTriggeredViaApi()
    {
        $job = new Job;
        $job->setUser(null);
        $job->setNotificationEmails(JobListenerTest::NOTIFICATION_EMAIL);
        $jobEvent = new JobEvent(null, new Stack(), null, null, null);
        $jobEvent->setJob($job);
        $jobEvent->setJobInfo(new JobInfo);

        $this->engineMock->expects($this->once())
            ->method('render')
            ->with(
                'EmagCoreNotificationBundle:Email:job-finished.html.twig',
                ['job' => $job]
            )
            ->willReturn('email body');

        $this->mailerMock->expects($this->once())
            ->method('send')
            ->with([JobListenerTest::NOTIFICATION_EMAIL], 'Job finished', 'email body');

        $this->jobListener->onJobFinished($jobEvent);
    }

    /**
     * @param $shouldPublish
     * @param $jobStatus
     * @param $jobSchedule
     * @param $stackCoreCode
     *
     * @dataProvider provideConditionsForPublishingASupportTicket
     */
    public function testOnJobFinishedWillPublishOrNotASupportTicketBasedOnTheProvidedConditions($shouldPublish, $jobStatus, $jobSchedule, $stackCoreCode)
    {
        $job = new Job();
        $reflectionProperty = new \ReflectionProperty(Job::class, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($job, 123);
        $job->setUser(null);
        $job->setNotificationEmails(JobListenerTest::NOTIFICATION_EMAIL);
        $job->setJobStatus($jobStatus);
        $job->setJobSchedule($jobSchedule);
        $stack = new Stack();
        $stack->setCoreCode($stackCoreCode);
        $job->setStack($stack);
        $jobEvent = new JobEvent('firefox', $stack, null, null, null);
        $jobEvent->setJob($job);
        $test = new Test();
        $distribution = new Distribution();
        $reflectionProperty = new \ReflectionProperty(Distribution::class, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($distribution, 456);
        $test->setDistribution($distribution);
        $jobInfo = new JobInfo();
        $jobInfo->setTest($test);
        $jobInfo->setJobInfoStatus(5);
        $jobEvent->setJobInfo($jobInfo);
        $job->setJobInfos([$jobInfo]);
        $job->setCreateTicket(true);

        if (!$shouldPublish) {
            $this->publisherMock->expects($this->never())
                ->method('publish');
        } else {
            $this->publisherMock->expects($this->once())
                ->method('publish')
                ->with(json_encode([
                    'jobId'          => $job->getId(),
                    'distributionId' => $jobInfo->getTest()->getDistribution()->getId(),
                ]));
        }

        $this->jobListener->onJobFinished($jobEvent);
    }

    public function provideConditionsForPublishingASupportTicket()
    {
        $jobScheduleOnTime = new JobSchedule();
        $jobScheduleOnTime->setTriggerEvent(JobSchedule::ON_TIME);
        $jobScheduleOnDeploy = new JobSchedule();
        $jobScheduleOnDeploy->setTriggerEvent(JobSchedule::ON_DEPLOY);

        return [
            [false, Job::STATUS_PASS, null, null],
            [false, Job::STATUS_FAIL, null, null],
            [false, Job::STATUS_ERROR, null, null],
            [false, Job::STATUS_FAIL, $jobScheduleOnTime, null],
            [false, Job::STATUS_ERROR, $jobScheduleOnTime, null],
            [false, Job::STATUS_FAIL, $jobScheduleOnDeploy, null],
            [false, Job::STATUS_ERROR, $jobScheduleOnDeploy, null],
            [true, Job::STATUS_FAIL, $jobScheduleOnDeploy, static::CORE_TEST],
            [true, Job::STATUS_ERROR, $jobScheduleOnDeploy, static::CORE_TEST],
        ];
    }
}
