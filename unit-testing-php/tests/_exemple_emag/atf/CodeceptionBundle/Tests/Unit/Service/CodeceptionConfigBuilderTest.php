<?php
namespace Emag\Core\CodeceptionBundle\Tests\Unit\Service;

use Emag\Core\BaseBundle\Exception\AtfException;
use Emag\Core\CodeceptionBundle\Entity\CodeBranch;
use Emag\Core\CodeceptionBundle\Entity\CodeceptionEnvironment;
use Emag\Core\CodeceptionBundle\Entity\CodeceptionImport;
use Emag\Core\CodeceptionBundle\Entity\CodeceptionInstance;
use Emag\Core\CodeceptionBundle\Entity\Test;
use Emag\Core\CodeceptionBundle\Service\CodeceptionConfig;
use Emag\Core\CodeceptionBundle\Service\CodeceptionConfigBuilder;
use Emag\Core\CodeceptionBundle\Service\CodeceptionConfigDumper;
use Emag\Core\CodeceptionBundle\Service\CodeceptionConfigParser;
use Emag\Core\CodeceptionBundle\Service\CodeceptionEnvironmentsService;
use Emag\Core\CodeceptionBundle\Service\CodeceptionImportService;
use Emag\Core\JobBundle\Entity\Job;
use Emag\Core\JobBundle\Entity\JobInfo;
use Symfony\Component\Filesystem\Filesystem;

class CodeceptionConfigBuilderTest extends \PHPUnit_Framework_TestCase
{
    /**
     * @var CodeceptionConfigBuilder
     */
    private $codeceptionConfigBuilder;

    /**
     * @var CodeceptionEnvironmentsService|\PHPUnit_Framework_MockObject_MockObject
     */
    private $codeceptionEnvironmentServiceMock;

    /**
     * @var CodeceptionImportService|\PHPUnit_Framework_MockObject_MockObject
     */
    private $codeceptionImportServiceMock;

    /**
     * @var CodeceptionConfig|\PHPUnit_Framework_MockObject_MockObject
     */
    private $configMock;

    /**
     * @var Filesystem|\PHPUnit_Framework_MockObject_MockObject
     */
    private $filesystemMock;

    protected function setUp()
    {
        $this->codeceptionConfigBuilder = new CodeceptionConfigBuilder();

        /* @var CodeceptionConfigParser|\PHPUnit_Framework_MockObject_MockObject $codeceptionConfigParserMock */
        $codeceptionConfigParserMock = $this->getMockBuilder(CodeceptionConfigParser::class)
            ->disableOriginalConstructor()->getMock();
        $this->codeceptionConfigBuilder->setCodeceptionConfigParser($codeceptionConfigParserMock);

        /** @var CodeceptionConfigDumper|\PHPUnit_Framework_MockObject_MockObject $codeceptionConfigDumperMock */
        $codeceptionConfigDumperMock = $this->getMockBuilder(CodeceptionConfigDumper::class)
            ->disableOriginalConstructor()->getMock();
        $this->codeceptionConfigBuilder->setCodeceptionConfigDumper($codeceptionConfigDumperMock);

        /** @var CodeceptionEnvironmentsService|\PHPUnit_Framework_MockObject_MockObject codeceptionEnvironmentServiceMock */
        $this->codeceptionEnvironmentServiceMock = $this->getMockBuilder(CodeceptionEnvironmentsService::class)
            ->disableOriginalConstructor()
            ->getMock();
        $this->codeceptionConfigBuilder->setCodeceptionEnvironmentsService($this->codeceptionEnvironmentServiceMock);

        /** @var CodeceptionImportService|\PHPUnit_Framework_MockObject_MockObject codeceptionImportServiceMock */
        $this->codeceptionImportServiceMock = $this->getMockBuilder(CodeceptionImportService::class)->disableOriginalConstructor()->getMock();
        $this->codeceptionConfigBuilder->setCodeceptionImportService($this->codeceptionImportServiceMock);

        $this->filesystemMock = $this->getMockBuilder(Filesystem::class)->disableOriginalConstructor()->getMock();
        $this->codeceptionConfigBuilder->setFilesystem($this->filesystemMock);

        $this->configMock = $this->getMockBuilder(CodeceptionConfig::class)
            ->disableOriginalConstructor()->getMock();
        $codeceptionConfigParserMock->expects($this->any())
            ->method('parse')
            ->willReturn($this->configMock);
    }

    public function testBuildConfigWillDisableTheRecorderExtensionWhenTakeSnapshotsIsOff()
    {
        $jobInfo = new JobInfo();
        $jobInfo->setJobInfoType(JobInfo::JOB_INFO_TYPE_CEPT);
        $jobInfo->setTakeSnapshots(false);

        $this->configMock->expects($this->once())
            ->method('disableExtension')
            ->with(CodeceptionConfigBuilder::CODECEPTION_EXTENSION_RECORDER);

        $this->codeceptionConfigBuilder->buildConfig($jobInfo);
    }

    public function testBuildConfigWillSetTheRecorderExtensionToNotDeleteScreenshotsWhenTakeSnapshotsIsOn()
    {
        $jobInfo = new JobInfo();
        $jobInfo->setJobInfoType(JobInfo::JOB_INFO_TYPE_CEPT);
        $jobInfo->setTakeSnapshots(true);

        $this->configMock
            ->expects($this->never())
            ->method('disableExtension');

        $this->configMock
            ->expects($this->once())
            ->method('configExtension')
            ->with(CodeceptionConfigBuilder::CODECEPTION_EXTENSION_RECORDER, ['delete_successful' => false]);

        $this->codeceptionConfigBuilder->buildConfig($jobInfo);
    }

    public function testBuildEnvironmentWithCests() {
        $browser = Job::BROWSER_FIREFOX;

        $jobInfo = new JobInfo();
        $jobInfo->setJobInfoType(JobInfo::JOB_INFO_TYPE_CEST);

        $job = new Job();
        $job->setBrowser($browser);

        $jobInfo->setJob($job);

        $test = new Test();
        $test->setSuite(Test::SUITE_ACCEPTANCE)
            ->setCodeceptionInstance(new CodeceptionInstance())
            ->setBranch(new CodeBranch());
        $jobInfo->setTest($test);

        $this->codeceptionImportServiceMock
            ->expects($this->once())
            ->method('findLatestImportByBranch')
            ->willReturn(new CodeceptionImport());

        $this->codeceptionImportServiceMock
            ->expects($this->once())
            ->method('getEnvironmentsPath')
            ->willReturn('environments_path');

        $env = new CodeceptionEnvironment();
        $env->setHost('192.168.152.157')
            ->setPort(55500)
            ->setBrowser($browser);

        $this->codeceptionEnvironmentServiceMock
            ->expects($this->once())
            ->method('findOneBy')
            ->willReturn($env);

        $this->filesystemMock
            ->expects($this->once())
            ->method('dumpFile');

        $this->assertEquals($browser, $this->codeceptionConfigBuilder->buildEnvironment($jobInfo));
    }

    public function testBuildEnvironmentWithCeptsAndTestOfAcceptanceSuite() {
        $browser = Job::BROWSER_FIREFOX;

        $jobInfo = new JobInfo();
        $jobInfo->setJobInfoType(JobInfo::JOB_INFO_TYPE_CEPT);

        $job = new Job();
        $job->setBrowser($browser);

        $jobInfo->setJob($job);

        $test = new Test();
        $test->setSuite(Test::SUITE_ACCEPTANCE)
            ->setCodeceptionInstance(new CodeceptionInstance())
            ->setBranch(new CodeBranch());
        $jobInfo->setTest($test);

        $this->codeceptionEnvironmentServiceMock
            ->expects($this->once())
            ->method('findOneBy')
            ->willReturn(new CodeceptionEnvironment());

        $this->assertEquals($browser, $this->codeceptionConfigBuilder->buildEnvironment($jobInfo));
    }

    public function testBuildEnvironmentWithCeptsAndNotTestOfAcceptanceSuite() {
        $browser = Job::BROWSER_FIREFOX;

        $jobInfo = new JobInfo();
        $jobInfo->setJobInfoType(JobInfo::JOB_INFO_TYPE_CEPT);

        $job = new Job();
        $job->setBrowser($browser);

        $jobInfo->setJob($job);

        $test = new Test();
        $test->setSuite(Test::SUITE_API)
            ->setCodeceptionInstance(new CodeceptionInstance())
            ->setBranch(new CodeBranch());
        $jobInfo->setTest($test);

        $this->assertEquals('', $this->codeceptionConfigBuilder->buildEnvironment($jobInfo));
    }

    public function testBuildEnvironmentWithCestsWithBogusBrowser() {
        $browser = Job::BROWSER_PHANTOM;

        $jobInfo = new JobInfo();
        $jobInfo->setJobInfoType(JobInfo::JOB_INFO_TYPE_CEST);

        $job = new Job();
        $job->setBrowser($browser);

        $jobInfo->setJob($job);

        $test = new Test();
        $test->setSuite(Test::SUITE_ACCEPTANCE)
            ->setCodeceptionInstance(new CodeceptionInstance())
            ->setBranch(new CodeBranch());
        $jobInfo->setTest($test);

        $this->setExpectedException(AtfException::class);

        $this->codeceptionConfigBuilder->buildEnvironment($jobInfo);
    }

    public function testBuildEnvironmentWithCestsWithNoEnvsInDB() {
        $browser = Job::BROWSER_FIREFOX;

        $jobInfo = new JobInfo();
        $jobInfo->setJobInfoType(JobInfo::JOB_INFO_TYPE_CEST);

        $job = new Job();
        $job->setBrowser($browser);

        $jobInfo->setJob($job);

        $test = new Test();
        $test->setSuite(Test::SUITE_ACCEPTANCE)
            ->setCodeceptionInstance(new CodeceptionInstance())
            ->setBranch(new CodeBranch());
        $jobInfo->setTest($test);

        $this->codeceptionImportServiceMock
            ->expects($this->once())
            ->method('findLatestImportByBranch')
            ->willReturn(new CodeceptionImport());

        $this->codeceptionImportServiceMock
            ->expects($this->once())
            ->method('getEnvironmentsPath')
            ->willReturn('environments_path');

        $this->codeceptionEnvironmentServiceMock
            ->expects($this->once())
            ->method('findOneBy')
            ->willReturn(null);

        $this->setExpectedException(AtfException::class);

        $this->codeceptionConfigBuilder->buildEnvironment($jobInfo);
    }

    public function testBuildEnvironmentWithCestsDifferentSuites() {
        $browser = Job::BROWSER_FIREFOX;

        $jobInfo = new JobInfo();
        $jobInfo->setJobInfoType(JobInfo::JOB_INFO_TYPE_CEST);

        $job = new Job();
        $job->setBrowser($browser);

        $jobInfo->setJob($job);

        $test = new Test();
        $test->setSuite(Test::SUITE_API)
            ->setCodeceptionInstance(new CodeceptionInstance())
            ->setBranch(new CodeBranch());
        $jobInfo->setTest($test);

        $this->assertNull($this->codeceptionConfigBuilder->buildEnvironment($jobInfo));
    }

    public function testBuildEnvironmentWithBogusJobInfoType() {
        $jobInfo = new JobInfo();
        $jobInfo->setJobInfoType('bogus');

        $this->setExpectedException(AtfException::class);

        $this->codeceptionConfigBuilder->buildEnvironment($jobInfo);
    }
}
