<?php
namespace Emag\Core\CodeceptionBundle\Tests\Unit\Service;

use Emag\Core\CodeceptionBundle\Entity\CodeBranch;
use Emag\Core\CodeceptionBundle\Entity\CodeceptionImport;
use Emag\Core\CodeceptionBundle\Entity\CodeceptionInstance;
use Emag\Core\CodeceptionBundle\Entity\Test;
use Emag\Core\CodeceptionBundle\Service\CodeceptionCommandBuilder;
use Emag\Core\CodeceptionBundle\Service\CodeceptionConfigBuilder;
use Emag\Core\CodeceptionBundle\Service\CodeceptionImportService;
use Emag\Core\JobBundle\Entity\Job;
use Emag\Core\JobBundle\Entity\JobInfo;

class CodeceptionCommandBuilderTest extends \PHPUnit_Framework_TestCase
{
    public const JOB_INFO_ID = 123;

    /**
     * @var CodeceptionCommandBuilder
     */
    private $codeceptionCommandBuilder;

    /**
     * @var CodeceptionImportService|\PHPUnit_Framework_MockObject_MockObject
     */
    private $codeceptionImportServiceMock;

    /**
     * @var CodeceptionConfigBuilder|\PHPUnit_Framework_MockObject_MockObject
     */
    private $codeceptionConfigBuilderMock;

    protected function setUp()
    {
        $this->codeceptionCommandBuilder = new CodeceptionCommandBuilder();
        $this->codeceptionCommandBuilder->setRootDir('/var/static');
        $this->codeceptionCommandBuilder->setCodeceptionImportService($this->makeCodeceptionImportServiceMock());
        $this->codeceptionCommandBuilder->setCodeceptionConfigBuilder($this->makeCodeceptionConfigBuilderMock());
    }

    /**
     * @param $testType
     * @param $jobInfoType
     * @param $relativeTestPath
     * @param $testName
     * @dataProvider getRunCommandParamsAndExpected
     */
    public function testGetCommand($testType, $jobInfoType, $relativeTestPath, $testName)
    {
        $codeBranch = new CodeBranch();
        $codeceptionInstance = new CodeceptionInstance();

        /** @var Test $test */
        $test = new Test();
        $test->setType($testType);
        $test->setCodeceptionInstance($codeceptionInstance);
        $test->setBranch($codeBranch);
        $test->setCodePath($relativeTestPath);

        /** @var Job $job */
        $job = new Job();
        $job->setBrowser('firefox');

        /** @var JobInfo $jobInfo */
        $jobInfo = new JobInfo();
        $reflectionProperty = new \ReflectionProperty($jobInfo, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($jobInfo, static::JOB_INFO_ID);
        $jobInfo->setJob($job);
        $jobInfo->setTest($test);
        $jobInfo->setJobInfoType($jobInfoType);
        $jobInfo->setFilePath($relativeTestPath);

        /** @var CodeceptionImport $codeceptionImport */
        $codeceptionImport = new CodeceptionImport();

        $this->codeceptionImportServiceMock
            ->expects($jobInfoType == JobInfo::JOB_INFO_TYPE_CEST ? $this->once() : $this->never())
            ->method('getCodeceptionImport')
            ->with($jobInfo)
            ->willReturn($codeceptionImport);

        $this->codeceptionImportServiceMock
            ->expects($jobInfoType == JobInfo::JOB_INFO_TYPE_CEST ? $this->once() : $this->never())
            ->method('getCodecept')
            ->with($codeceptionImport)
            ->willReturn(null);

        $this->codeceptionConfigBuilderMock
            ->expects($this->once())
            ->method('buildConfig')
            ->with($jobInfo)
            ->willReturn('/some/dir/config/codeception.yml');
        $this->codeceptionConfigBuilderMock
            ->expects($this->once())
            ->method('buildEnvironment')
            ->with($jobInfo)
            ->willReturn('firefox');

        $this->assertEquals(
            "php /var/static/../bin/codecept run acceptance \"$testName\"" .
            ' --config /some/dir/config/codeception.yml' .
            ' --debug' .
            ' --no-colors' .
            ' --env firefox' .
            ' --json=report.json' .
            ' --html=report.html',
            $this->codeceptionCommandBuilder->getCommand($jobInfo)
        );
    }

    /**
     * @return array
     */
    public function getRunCommandParamsAndExpected()
    {
        return [
            [Test::TYPE_GROUP, JobInfo::JOB_INFO_TYPE_CEPT, 'tests/acceptance/LoginCest.php', 'LoginCest'],
            [Test::TYPE_IMPORTED, JobInfo::JOB_INFO_TYPE_CEST, 'tests/acceptance/SomeCest.php', 'SomeCest'],
            [Test::TYPE_IMPORTED, JobInfo::JOB_INFO_TYPE_CEST, 'tests/acceptance/Module/SomeCest.php', 'Module/SomeCest'],
        ];
    }

    /**
     * @return CodeceptionImportService|\PHPUnit_Framework_MockObject_MockObject
     */
    private function makeCodeceptionImportServiceMock()
    {
        $this->codeceptionImportServiceMock = $this->getMockBuilder(CodeceptionImportService::class)
            ->disableOriginalConstructor()->getMock();

        return $this->codeceptionImportServiceMock;
    }

    /**
     * @return CodeceptionConfigBuilder|\PHPUnit_Framework_MockObject_MockObject
     */
    private function makeCodeceptionConfigBuilderMock()
    {
        $this->codeceptionConfigBuilderMock = $this->getMockBuilder(CodeceptionConfigBuilder::class)
            ->disableOriginalConstructor()->getMock();

        return $this->codeceptionConfigBuilderMock;
    }
}
