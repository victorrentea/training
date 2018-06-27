<?php
namespace Emag\Core\JobBundle\Tests\Unit\Service;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Persistence\ObjectRepository;
use Doctrine\ORM\EntityManagerInterface;
use Emag\Core\CodeceptionBundle\Entity\Test;
use Emag\Core\CodeceptionBundle\Entity\TestingPlan;
use Emag\Core\CodeceptionBundle\Service\CodeceptionService;
use Emag\Core\JobBundle\Entity\Job;
use Emag\Core\JobBundle\Entity\JobInfo;
use Emag\Core\JobBundle\Event\JobEvent;
use Emag\Core\JobBundle\Service\JobService;
use Psr\Log\LoggerInterface;
use Symfony\Component\EventDispatcher\EventDispatcherInterface;
use Symfony\Component\Routing\Router;

require_once __DIR__ . '/../../../../CodeceptionBundle/Tests/Unit/Service/Overrides.php';

class JobServiceTest extends \PHPUnit_Framework_TestCase
{
    /**
     * @var Job
     */
    private $currentJob;
    /**
     * @var JobInfo
     */
    private $currentJobInfo;

    public static $fileContents = [
        '/codeception_template.yml' => '
paths:
    tests: ../../tests
    log: ../../tests/_output
    data: ../../tests/_data
    support: ../../tests/_support
    envs: ../../tests/_envs
',
        '/tests/_output/123/GeneratedTest_0_1__Cest.json' => '',
        '/tests/_output/123/GeneratedTest_0_3__Cest.json'
        => '{}{"event": "test", "status": "pass", "output": "output"}',
        //Test pass
        '/tests/_output/123/GeneratedTest_0_4__Cest.json'
        => '{}{"event": "test", "status": "fail", "output": "output"}',
        //Test fail
        '/tests/_output/123/GeneratedTest_0_5__Cest.json'
        => '{}{"event": "test", "status": "error", "output": "output"}',
        //Test error
        '/tests/_output/123/GeneratedTest_0_6__Cest.json'
        => '{}{"event": "test", "status": "error", "output": "output", "message":"extra error message"}',
        //Test error with extra message
    ];

    public function setUp()
    {
        $GLOBALS['className'] = 'Emag\Core\JobBundle\Tests\Unit\Service\JobServiceTest';
    }

    public function testGetScreenshotsFolderFromLog()
    {
        $test = new Test();
        $test->setType('imported');

        $jobInfo = new JobInfo();
        $jobInfo->setJobInfoType(JobInfo::JOB_INFO_TYPE_CEPT);
        $jobInfo->setTest($test);
        $jobInfo->setFilePath('/var/www/tests/acceptance/SomeCept.php');

        /** @var CodeceptionService|\PHPUnit_Framework_MockObject_MockObject $codeceptionServiceMock */
        $codeceptionServiceMock = $this->getMockBuilder(CodeceptionService::class)->disableOriginalConstructor()->getMock();

        $jobService = new JobService();
        $jobService->setCodeceptionService($codeceptionServiceMock);
    }

    /**
     * @expectedException \Emag\Core\BaseBundle\Exception\AtfException
     * @expectedExceptionMessage JobInfo jobInfoId not found
     */
    public function testExecuteTestCycleWithoutJobInfo()
    {
        $repositoryMock = $this->getMockBuilder(ObjectRepository::class)->getMock();

        $managerMock = $this->getMockBuilder(EntityManagerInterface::class)->getMock();
        $managerMock->expects($this->any())
            ->method('getRepository')
            ->willReturn($repositoryMock);

        $jobService = $this->getJobService($managerMock, 0);

        $jobService->executeTestCycleJob($this->getGearmanJobMock());
    }

    public function testParseImportedTestsParams()
    {
        $repositoryMock = $this->getMockBuilder(ObjectRepository::class)->getMock();

        $managerMock = $this->getMockBuilder(EntityManagerInterface::class)->getMock();
        $managerMock->expects($this->any())
            ->method('getRepository')
            ->willReturn($repositoryMock);

        $jobService = $this->getJobService($managerMock, 0);

        $this->assertEquals([36 => ['STACK_ID' => 16, 'ATF\Page\Login' => ['username' => 'atf.test', 'password' => 'TestingUserATF2015'], 'ATF\Page\TestGroupAdd' => ['jiraKey' => 'OAM-0001', 'distribution' => 'atf'], 'ATF\Page\TestPlanAdd' => ['jiraKey' => 'OAM-0002'], 'ATF\Page\TestAddNewCycle' => ['jiraKey' => 'Jira-t001']]], $jobService->parseImportedTestsParams('params%5B36%5D%5BSTACK_ID%5D=16&params%5B36%5D%5BATF%5CPage%5CLogin%7C%7Cusername%5D=atf.test&params%5B36%5D%5BATF%5CPage%5CLogin%7C%7Cpassword%5D=TestingUserATF2015&params%5B36%5D%5BATF%5CPage%5CTestGroupAdd%7C%7CjiraKey%5D=OAM-0001&params%5B36%5D%5BATF%5CPage%5CTestGroupAdd%7C%7Cdistribution%5D=atf&params%5B36%5D%5BATF%5CPage%5CTestPlanAdd%7C%7CjiraKey%5D=OAM-0002&params%5B36%5D%5BATF%5CPage%5CTestAddNewCycle%7C%7CjiraKey%5D=Jira-t001'));
    }

    public function testSetParamsStackWithParamsAndStackId()
    {
        $repositoryMock = $this->getMockBuilder(ObjectRepository::class)->getMock();

        $managerMock = $this->getMockBuilder(EntityManagerInterface::class)->getMock();
        $managerMock->expects($this->any())
            ->method('getRepository')
            ->willReturn($repositoryMock);

        $jobService = $this->getJobService($managerMock, 0);

        $this->assertEquals([36 => ['STACK_ID' => 13, 'ATF\Page\Login' => ['username' => 'atf.test', 'password' => 'TestingUserATF2015'], 'ATF\Page\TestGroupAdd' => ['jiraKey' => 'OAM-0001', 'distribution' => 'atf'], 'ATF\Page\TestPlanAdd' => ['jiraKey' => 'OAM-0002'], 'ATF\Page\TestAddNewCycle' => ['jiraKey' => 'Jira-t001']]], $jobService->setParamsStack([36 => ['STACK_ID' => '16', 'ATF\Page\Login' => ['username' => 'atf.test', 'password' => 'TestingUserATF2015'], 'ATF\Page\TestGroupAdd' => ['jiraKey' => 'OAM-0001', 'distribution' => 'atf'], 'ATF\Page\TestPlanAdd' => ['jiraKey' => 'OAM-0002'], 'ATF\Page\TestAddNewCycle' => ['jiraKey' => 'Jira-t001']]], 13));
    }

    public function testGetTestUrlWhenJobHaveTest() {
        $testMock = $this->getMockBuilder(Test::class)
            ->disableOriginalConstructor()
            ->getMock();

        $testMock->expects($this->any())
            ->method('getId')
            ->willReturn(1);

        $jobMock = $this->getMockBuilder(Job::class)
            ->disableOriginalConstructor()
            ->getMock();
        $jobMock->expects($this->any())
            ->method('getTest')
            ->willReturn($testMock);

        $jobServiceMock = $this->getMockBuilder(JobService::class)
            ->disableOriginalConstructor()
            ->setMethods(["generateUrl"])
            ->getMock();

        $routerMock = $this->getMockBuilder(Router::class)
            ->disableOriginalConstructor()
            ->getMock();

        $routerMock->expects($this->any())
            ->method('generate')
            ->willReturn('link');

        $jobServiceMock->setRouter($routerMock);

        $testUrl = $jobServiceMock->getTestUrl($jobMock);
        $this->assertEquals($testUrl, 'link');
    }

    public function testGetTestingPlanUrlWhenJobHaveTestingPlan() {
        $testMock = $this->getMockBuilder(Test::class)
            ->disableOriginalConstructor()
            ->getMock();

        $testMock->expects($this->any())
            ->method('getId')
            ->willReturn(1);

        $jobMock = $this->getMockBuilder(Job::class)
            ->disableOriginalConstructor()
            ->getMock();
        $jobMock->expects($this->any())
            ->method('getTestingPlan')
            ->willReturn($testMock);

        $jobServiceMock = $this->getMockBuilder(JobService::class)
            ->disableOriginalConstructor()
            ->setMethods(["generateUrl"])
            ->getMock();

        $routerMock = $this->getMockBuilder(Router::class)
            ->disableOriginalConstructor()
            ->getMock();

        $routerMock->expects($this->any())
            ->method('generate')
            ->willReturn('link');

        $jobServiceMock->setRouter($routerMock);

        $testUrl = $jobServiceMock->getTestUrl($jobMock);
        $this->assertEquals($testUrl, 'link');
    }

    public function testGetTestingPlanUrlReturnNull() {
        $jobMock = $this->getMockBuilder(Job::class)
            ->disableOriginalConstructor()
            ->getMock();
        $jobMock->expects($this->any())
            ->method('getTestingPlan')
            ->willReturn(null);
        $jobMock->expects($this->any())
            ->method('getTest')
            ->willReturn(null);

        $jobServiceMock = $this->getMockBuilder(JobService::class)
            ->disableOriginalConstructor()
            ->setMethods(["generateUrl"])
            ->getMock();

        $routerMock = $this->getMockBuilder(Router::class)
            ->disableOriginalConstructor()
            ->getMock();

        $routerMock->expects($this->any())
            ->method('generate')
            ->willReturn('link');

        $jobServiceMock->setRouter($routerMock);

        $testUrl = $jobServiceMock->getTestUrl($jobMock);
        $this->assertNull($testUrl);
    }

    private function getJobService($manager, $expectedEvents = 1, $codeceptionService = null)
    {
        $jobService = new JobService();
        /** @var EventDispatcherInterface|\PHPUnit_Framework_MockObject_MockObject $eventDispatcherMock */
        $eventDispatcherMock = $this->getMockBuilder(EventDispatcherInterface::class)->getMock();
        $eventDispatcherMock->expects($this->exactly($expectedEvents))
            ->method('dispatch')
            ->with(JobEvent::FINISH_JOB_INFO_EVENT, $this->anything());

        $jobService->setManager($manager)->setEventDispatcher($eventDispatcherMock);
        if (is_null($codeceptionService)) {
            $codeceptionService = new CodeceptionService();
        }
        $jobService->setCodeceptionService($codeceptionService);

        /** @var LoggerInterface|\PHPUnit_Framework_MockObject_MockObject $loggerMock */
        $loggerMock = $this->getMockBuilder(LoggerInterface::class)->disableOriginalConstructor()->getMock();
        $jobService->setLogger($loggerMock);

        return $jobService;
    }

    /**
     * @param string $jobInfoId
     * @return \PHPUnit_Framework_MockObject_MockObject|\GearmanJob
     */
    private function getGearmanJobMock($jobInfoId = 'jobInfoId')
    {
        static $gearmanJobMockBuilder = null;
        if (is_null($gearmanJobMockBuilder)) {
            $gearmanJobMockBuilder = $this->getMockBuilder(\GearmanJob::class)
                ->disableOriginalConstructor();
        }

        $gearmanJob = $gearmanJobMockBuilder->getMock();
        $gearmanJob->expects($this->once())
            ->method('workload')
            ->willReturn(json_encode(['jobInfoId' => $jobInfoId]));

        return $gearmanJob;
    }


    private function getManagerMock($stackId = 1)
    {
        $jobInfo = $this->createTestJobInfo($stackId);

        $repositoryMock = $this->getMockBuilder(ObjectRepository::class)->getMock();
        $repositoryMock->expects($this->once())
            ->method('find')
            ->willReturn($jobInfo);

        $managerMock = $this->getMockBuilder(EntityManagerInterface::class)->getMock();
        $managerMock->expects($this->any())
            ->method('getRepository')
            ->willReturn($repositoryMock);

        return $managerMock;
    }

    private function createTestJobInfo($stackId = 1, $browser = 'browser')
    {
        $stack = $this->createStackWithId($stackId);
        $jobInfo = new JobInfo();
        $jobInfo->setJobInfoType(JobInfo::JOB_INFO_TYPE_CEPT);
        $job = new Job();
        $job->setStack($stack)
            ->setJobInfos(new ArrayCollection(array($jobInfo)))
            ->setBrowser($browser);
        $jobInfo->setJob($job)
            ->setJobInfoType(JobInfo::JOB_INFO_TYPE_CEST);
        $this->currentJob = $job;
        $this->currentJobInfo = $jobInfo;

        return $jobInfo;
    }

    private function createStackWithId($id = 1)
    {
        //This is how Doctrine creates entities
        return unserialize(sprintf("O:40:\"Emag\Core\CodeceptionBundle\Entity\Stack\":8:{s:7:\"\000*\000name\";N;s:15:\"\000*\000environments\";O:43:\"Doctrine\Common\Collections\ArrayCollection\":1:{s:53:\"\000Doctrine\Common\Collections\ArrayCollection\000elements\";a:0:{}}s:10:\"\000*\000readOnly\";N;s:9:\"\000*\000linkId\";i:0;s:5:\"\000*\000id\";i:%s;s:12:\"\000*\000createdAt\";N;s:13:\"\000*\000modifiedAt\";N;s:9:\"\000*\000status\";i:1;}",
            $id));
    }

    public static function file_get_contents($filename)
    {
        if (!isset(static::$fileContents[$filename])) {
            return false;
        }

        return static::$fileContents[$filename];
    }

    public static function is_readable($filename)
    {
        return isset(static::$fileContents[$filename]);
    }

    public static function is_file($filename)
    {
        return isset(static::$fileContents[$filename]);
    }
}

//Catch Emag\Core\CodeceptionBundle\Service\CodeceptionCodeBuilderService file system calls
namespace Emag\Core\CodeceptionBundle\Service;

use Emag\Core\JobBundle\Tests\Unit\Service\JobServiceTest;

function copy()
{
    return true;
}

function file_put_contents()
{
    return true;
}

function exec()
{
    return true;
}

function system($command, &$return)
{
    $return = 0;

    return true;
}

function is_readable($filename)
{
    return JobServiceTest::is_readable($filename);
}

function is_file($filename)
{
    return JobServiceTest::is_readable($filename);
}

function mkdir($pathname, $mode = 0777, $recursive = false, $context = null)
{
}
