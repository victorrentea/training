<?php
namespace Emag\Core\JobBundle\Tests\Unit\Service;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Persistence\ObjectManager;
use Emag\Core\CodeceptionBundle\Entity\{
    Stack, Test
};
use Emag\Core\CodeceptionBundle\Service\{
    CodeceptionCommandBuilder, CodeceptionImportService, CodeceptionService
};
use Emag\Core\JobBundle\Entity\{
    Job, JobInfo
};
use Emag\Core\JobBundle\Repository\JobInfoRepository;
use Emag\Core\JobBundle\Service\{
    GearmanService, JobInfoService, JobService, ScreenshotService
};
use Emag\Core\JobBundle\Tests\Util\JobInfoBuilder;
use GearmanJob;
use PHPUnit_Framework_MockObject_MockObject as Mock;
use Psr\Log\LoggerInterface;
use Symfony\Component\EventDispatcher\EventDispatcherInterface;

/**
 * Class JobServiceNewTest
 * @package Emag\Core\JobBundle\Tests\Unit\Service
 */
class JobServiceNewTest extends \PHPUnit_Framework_TestCase
{
    /**
     * @var JobService
     */
    private $jobService;

    /**
     * @var ObjectManager|Mock
     */
    private $objectManagerMock;

    /**
     * @var JobInfoRepository|Mock
     */
    private $jobInfoRepositoryMock;

    /**
     * @var LoggerInterface|Mock
     */
    private $loggerMock;

    /**
     * @var CodeceptionService|Mock
     */
    private $codeceptionServiceMock;

    /**
     * @var CodeceptionCommandBuilder|Mock
     */
    private $codeceptionCommandBuilderMock;

    /**
     * @var ScreenshotService|Mock
     */
    private $screenshotService;

    /**
     * @var EventDispatcherInterface|Mock
     */
    private $eventDispatcher;

    /**
     * @var GearmanService|Mock
     */
    private $gearmanServiceMock;

    /**
     * @var JobInfoService|Mock
     */
    private $jobInfoServiceMock;

    /** @var CodeceptionImportService|\PHPUnit_Framework_MockObject_MockObject $codeceptionImportServiceMock */
    private $codeceptionImportServiceMock;

    public function testExecuteTestCycleJobWillRunAndReturnZeroWhenEverythingIsMockedOutCorrectly()
    {
        $jobInfo = $this->getJobInfoBuilder()
            ->withId(42)
            ->withStackId(29)
            ->withBrowser('firefox')
            ->withTestType(Test::TYPE_IMPORTED)
            ->withJobInfoType(JobInfo::JOB_INFO_TYPE_CEST)
            ->getJobInfo();

        $this->jobInfoRepositoryMock
            ->expects($this->once())
            ->method('find')
            ->with($jobInfo->getId())
            ->willReturn($jobInfo);

        $job = new Job;
        $test = new Test;
        $test->setType(Test::TYPE_CYCLE);
        $job->setTest($test);
        $jobInfo->setJob($job);

        $ret = $this->jobService->executeTestCycleJob(
            $this->makeGearmanJobMockWithWorkload(
                json_encode(['jobInfoId' => $jobInfo->getId()])
            )
        );

        $this->assertEquals(0, $ret);
    }

    public function testWebdriverTestsShouldHaveStack()
    {
        $jobInfo = $this->getJobInfoBuilder()
            ->withId(42)
            ->withStackId(29)
            ->withBrowser('firefox')
            ->withTestType(Test::TYPE_CYCLE)
            ->withJobInfoType(JobInfo::JOB_INFO_TYPE_CEST)
            ->getJobInfo();

        $this->jobInfoRepositoryMock
            ->expects($this->once())
            ->method('find')
            ->with($jobInfo->getId())
            ->willReturn($jobInfo);

        $job = new Job;
        $test = new Test;
        $test->setType(Test::TYPE_CYCLE);
        $job->setTest($test);
        $jobInfo->setJob($job);

        $this->codeceptionImportServiceMock
            ->expects($this->once())
            ->method('isWebDriverSuite')
            ->willReturn(true);

        $this->jobService->executeTestCycleJob(
            $this->makeGearmanJobMockWithWorkload(
                json_encode(['jobInfoId' => $jobInfo->getId()])
            )
        );
    }

    public function testWebdriverTestsShouldHaveBrowser()
    {
        $jobInfo = $this->getJobInfoBuilder()
            ->withId(42)
            ->withStackId(29)
            ->withBrowser('firefox')
            ->withTestType(Test::TYPE_CYCLE)
            ->withJobInfoType(JobInfo::JOB_INFO_TYPE_CEST)
            ->getJobInfo();

        $this->jobInfoRepositoryMock
            ->expects($this->once())
            ->method('find')
            ->with($jobInfo->getId())
            ->willReturn($jobInfo);

        $job = new Job;
        $test = new Test;
        $test->setType(Test::TYPE_CYCLE);
        $job->setTest($test);
        $stack = new Stack;

        $reflectionProperty = new \ReflectionProperty(Stack::class, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($stack, 1);

        $job->setStack($stack);
        $jobInfo->setJob($job);

        $this->codeceptionImportServiceMock
            ->expects($this->once())
            ->method('isWebDriverSuite')
            ->willReturn(true);

        $this->jobService->executeTestCycleJob(
            $this->makeGearmanJobMockWithWorkload(
                json_encode(['jobInfoId' => $jobInfo->getId()])
            )
        );
    }

    public function testPublishOnGearmanQueueReturnsTrueWhenJobInfosArePublished()
    {
        $job = new Job();
        $this->gearmanServiceMock->expects($this->once())
            ->method('executeBackgroundTest')
            ->with($job)
            ->willReturn(['123']);

        $ret = $this->jobService->publishOnGearmanQueue($job);

        $this->assertTrue($ret);
    }

    public function testPublishOnGearmanQueueReturnsTrueWhenJobInfosAreNotPublished()
    {
        $job = new Job();
        $this->gearmanServiceMock->expects($this->once())
            ->method('executeBackgroundTest')
            ->with($job)
            ->willReturn([]);

        $ret = $this->jobService->publishOnGearmanQueue($job);

        $this->assertFalse($ret);
    }

    public function testIsJobFinishedWillReturnTrueWhenAllJobInfosAreFinished()
    {
        $job = new Job();
        $jobInfos = new ArrayCollection();
        for ($i = 0; $i < 3; $i++) {
            $jobInfo = new JobInfo();
            $jobInfos->add($jobInfo);
        }
        $jobInfos[0]->setJobInfoStatus(Job::STATUS_PASS);
        $jobInfos[1]->setJobInfoStatus(Job::STATUS_PASS);
        $jobInfos[2]->setJobInfoStatus(Job::STATUS_FAIL);
        $job->setJobInfos($jobInfos);

        $ret = $this->jobService->isJobFinished($job);
        
        $this->assertTrue($ret);
    }

    public function testIsJobFinishedWillReturnFalseWhenAtLeastOneJobInfoIsNotFinished()
    {
        $job = new Job();
        $jobInfos = new ArrayCollection();
        for ($i = 0; $i < 3; $i++) {
            $jobInfo = new JobInfo();
            $jobInfos->add($jobInfo);
        }
        $jobInfos[0]->setJobInfoStatus(Job::STATUS_PASS);
        $jobInfos[1]->setJobInfoStatus(Job::STATUS_FAIL);
        $jobInfos[2]->setJobInfoStatus(Job::STATUS_IN_PROGRESS);
        $job->setJobInfos($jobInfos);

        $ret = $this->jobService->isJobFinished($job);

        $this->assertFalse($ret);
    }

    public function testTriggerNextJobDoesNothingIfJobInfoHasNoNext()
    {
        $job = new Job();
        $jobInfo = new JobInfo();

        $this->gearmanServiceMock->expects($this->never())
            ->method('executeBackgroundTest');
        $this->jobInfoServiceMock->expects($this->never())
            ->method('queueJobInfo');

        $this->jobService->triggerNextJobInfo($job, $jobInfo);

        $this->assertFalse($jobInfo->hasNextJobInfo());
    }

    public function testTriggerNextJobDoesThingsIfJobInfoHasNextWithStatusWaiting()
    {
        $job = new Job();
        $jobInfo = new JobInfo();
        $nextJobInfo = new JobInfo();
        $nextJobInfo->setJobInfoStatus(Job::STATUS_WAITING);
        $jobInfo->setNextJobInfo($nextJobInfo);

        $this->gearmanServiceMock->expects($this->once())
            ->method('executeBackgroundTest')
            ->with($job);
        $this->jobInfoServiceMock->expects($this->once())
            ->method('queueJobInfo')
            ->with($nextJobInfo);

        $this->jobService->triggerNextJobInfo($job, $jobInfo);

        $this->assertTrue($jobInfo->hasNextJobInfo());
    }

    protected function setUp()
    {
        $this->jobService = new JobService();

        $this->setUpObjectManagerMock();
        $this->setUpLoggerMock();
        $this->setUpCodeceptionServiceMock();
        $this->setUpCodeceptionCommandBuilderMock();
        $this->setUpEventDispatcher();
        $this->setUpScreenshotService();
        $this->setUpGearmanService();
        $this->setUpJobInfoService();
        $this->setCodeceptionImportService();
    }

    private function setCodeceptionImportService()
    {
        $this->codeceptionImportServiceMock = $this->getMockBuilder(CodeceptionImportService::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->jobService->setCodeceptionImportService($this->codeceptionImportServiceMock);
    }

    private function setUpObjectManagerMock()
    {
        $this->objectManagerMock = $this->getMockBuilder(ObjectManager::class)
            ->disableOriginalConstructor()
            ->getMock();
        $this->jobService->setManager($this->objectManagerMock);
        $this->jobInfoRepositoryMock = $this->getMockBuilder(JobInfoRepository::class)
            ->disableOriginalConstructor()
            ->getMock();
        $this->objectManagerMock
            ->expects($this->any())
            ->method('getRepository')
            ->with(JobInfo::class)
            ->willReturn($this->jobInfoRepositoryMock);
    }

    private function setUpLoggerMock()
    {
        $this->loggerMock = $this->getMockBuilder(LoggerInterface::class)
            ->disableOriginalConstructor()
            ->getMock();
        $this->jobService->setLogger($this->loggerMock);
    }

    private function setUpCodeceptionServiceMock()
    {
        $this->codeceptionServiceMock = $this->getMockBuilder(CodeceptionService::class)
            ->disableOriginalConstructor()
            ->getMock();
        $this->jobService->setCodeceptionService($this->codeceptionServiceMock);
    }

    private function setUpEventDispatcher()
    {
        $this->eventDispatcher = $this->getMockBuilder(EventDispatcherInterface::class)
            ->disableOriginalConstructor()
            ->getMock();
        $this->jobService->setEventDispatcher($this->eventDispatcher);
    }

    private function setUpScreenshotService()
    {
        $this->screenshotService = $this->getMockBuilder(ScreenshotService::class)
            ->disableOriginalConstructor()
            ->getMock();
        $this->jobService->setScreenshotService($this->screenshotService);
    }

    private function setUpCodeceptionCommandBuilderMock()
    {
        $this->codeceptionCommandBuilderMock = $this->getMockBuilder(CodeceptionCommandBuilder::class)
            ->disableOriginalConstructor()
            ->getMock();
        $this->jobService->setCodeceptionCommandBuilder($this->codeceptionCommandBuilderMock);
    }

    /**
     * @param $workload
     * @return GearmanJob|Mock
     */
    private function makeGearmanJobMockWithWorkload($workload)
    {
        /** @var GearmanJob|Mock $gearmanJobMock */
        $gearmanJobMock = $this->getMockBuilder(GearmanJob::class)
            ->disableOriginalConstructor()
            ->getMock();
        $gearmanJobMock
            ->expects($this->once())
            ->method('workload')
            ->willReturn($workload);

        return $gearmanJobMock;
    }

    /**
     * @return JobInfoBuilder
     */
    private function getJobInfoBuilder()
    {
        return new JobInfoBuilder();
    }

    private function setUpGearmanService()
    {
        $this->gearmanServiceMock = $this->getMockBuilder(GearmanService::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->jobService->setGearmanService($this->gearmanServiceMock);
    }

    private function setUpJobInfoService()
    {
        $this->jobInfoServiceMock = $this->getMockBuilder(JobInfoService::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->jobService->setJobInfoService($this->jobInfoServiceMock);
    }
}
