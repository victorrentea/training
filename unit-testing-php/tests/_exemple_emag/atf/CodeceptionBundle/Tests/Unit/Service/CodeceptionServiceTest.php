<?php

namespace Emag\Core\CodeceptionBundle\Tests\Unit\Service;

use Emag\Core\CodeceptionBundle\Entity\CodeBranch;
use Emag\Core\CodeceptionBundle\Entity\CodeceptionImport;
use Emag\Core\CodeceptionBundle\Entity\CodeceptionInstance;
use Emag\Core\CodeceptionBundle\Entity\Test;
use Emag\Core\CodeceptionBundle\Entity\TestingPlan;
use Emag\Core\CodeceptionBundle\Exception\KnownException;
use Emag\Core\CodeceptionBundle\Exception\UnknownException;
use Emag\Core\CodeceptionBundle\Service\CodeceptionCommandBuilder;
use Emag\Core\CodeceptionBundle\Service\CodeceptionImportService;
use Emag\Core\CodeceptionBundle\Service\CodeceptionService;
use Emag\Core\CodeceptionBundle\Service\CodeService;
use Emag\Core\CodeceptionBundle\Service\ImportedTestService;
use Emag\Core\JobBundle\Entity\Job;
use Emag\Core\JobBundle\Entity\JobInfo;
use GuzzleHttp\Exception\ConnectException;

class CodeceptionServiceTest extends \PHPUnit_Framework_TestCase
{
    /**
     * @dataProvider getOutputWithNoMatch
     */
    public function testFindAndThrowExceptionsFromOutputWillSimplyReturnWhenTheOutputDoesNotMatch($output)
    {
        $codeceptionService = new CodeceptionService();

        try {
            $codeceptionService->findAndThrowExceptionsFromOutput($output);
        } catch (\Exception $exception) {
            $this->assertFalse(true, 'Wasn\'t supposed to throw any exception!');
        }
    }

    public function getOutputWithNoMatch()
    {
        return [
            [null],
            [''],
            ['Some random output'],
        ];
    }

    public function testFindAndThrowExceptionsFromOutputWillCorrectlyConstructAGuzzleConnectException()
    {
        $codeceptionService = new CodeceptionService();

        $output = "1) IproSmokeCest: Load ajax promo list grid page
 Test  ../../tests/smoke/IproSmokeCest.php:loadAjaxPromoListGridPage
                                                                                                                                                                                     
  [GuzzleHttp\\Exception\\ConnectException] cURL error 28: Operation timed out after 30000 milliseconds with 0 bytes received (see http://curl.haxx.se/libcurl/c/libcurl-errors.html)  
";

        try {
            $codeceptionService->findAndThrowExceptionsFromOutput($output);
        } catch (\Exception $exception) {
            $this->assertEquals(ConnectException::class, get_class($exception));
        }
    }

    public function testFindAndThrowExceptionsFromOutputWillAKnownExceptionForExceptionsWithMoreRequiredArguments()
    {
        $codeceptionService = new CodeceptionService();

        $output = "[GuzzleHttp\\Exception\\TooManyRedirectsException] Too many redirects";

        try {
            $codeceptionService->findAndThrowExceptionsFromOutput($output);
        } catch (\Exception $exception) {
            $this->assertEquals(KnownException::class, get_class($exception));
        }
    }

    /**
     * @param $output
     * @param $expectedException
     * @dataProvider getOutputWithExpectedException
     */
    public function testFindAndThrowExceptionsFromOutputWillThrowTheCorrectException($output, $expectedException)
    {
        $codeceptionService = new CodeceptionService();

        try {
            $codeceptionService->findAndThrowExceptionsFromOutput($output);
        } catch (\Exception $exception) {
            $this->assertEquals($expectedException, $exception);
        }

        $codeceptionService->findAndThrowExceptionsFromOutput(null);
    }

    public function getOutputWithExpectedException()
    {
        return [
            ['[Codeception\Exception\ConnectionException]', new \Codeception\Exception\ConnectionException()],
            ['[Codeception\Exception\ConfigurationException]', new \Codeception\Exception\ConfigurationException()],
            ['[RuntimeException]', new \RuntimeException()],
            ['[UnknownException] no body', new UnknownException('Unknown exception: [UnknownException] no body')],
            [
                '[Facebook\WebDriver\Exception\UnknownServerException] Element is not clickable at point (435, 513.5)',
                new \Facebook\WebDriver\Exception\UnknownServerException('Element is not clickable at point (435, 513.5)'),
            ],
            ['[LogicException]', new \LogicException()],
        ];
    }

    /**
     * @param int $jobInfoId
     * @param string $codeceptionPath
     * @param string $testPath
     * @param string $expectedJsonFilePath
     * @dataProvider provideForTestGetJsonFilePathWillReturnJsonFilePathBasedOnBasenamePartOfTestFilenameWhenJobInfoTypeIsCept
     */
    public function testGetJsonFilePathWillReturnJsonFilePathBasedOnBasenamePartOfTestFilenameWhenJobInfoTypeIsCept(
        $jobInfoId,
        $codeceptionPath,
        $testPath,
        $expectedJsonFilePath
    ) {
        $codeceptionService = new CodeceptionService();
        $codeceptionService->setCodeceptionPath($codeceptionPath);

        $jobInfo = new JobInfo();
        $reflectionProperty = new \ReflectionProperty(JobInfo::class, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($jobInfo, $jobInfoId);
        $jobInfo->setJobInfoType(JobInfo::JOB_INFO_TYPE_CEPT);
        $jobInfo->setFilePath($testPath);

        $this->assertEquals($expectedJsonFilePath, $codeceptionService->getJsonFilePath($jobInfo));
    }

    public function provideForTestGetJsonFilePathWillReturnJsonFilePathBasedOnBasenamePartOfTestFilenameWhenJobInfoTypeIsCept()
    {
        return [
            [
                // no trailing slash
                123,
                '/var/www/static/codeception',
                '/var/www/static/codeception/tests/acceptance/SomeCest_123_Cest.php',
                '/var/www/static/codeception/tests/_output/123/report.json'
            ],
            [
                // trailing slash, will be stripped
                123,
                '/var/www/static/codeception/',
                '/var/www/static/codeception/tests/acceptance/SomeCest_123_Cest.php',
                '/var/www/static/codeception/tests/_output/123/report.json'
            ],
        ];
    }

    public function testProvideForTestGetJsonFilePathWillReturnJsonFilePathBasedOnTestFilenameRelativeToAcceptanceFolderWhenJobInfoTypeIsCest()
    {
        $codeceptionService = new CodeceptionService();
        /** @var CodeceptionCommandBuilder|\PHPUnit_Framework_MockObject_MockObject $codeceptionCommandBuilderMock */
        $codeceptionCommandBuilderMock = $this->getMockBuilder(CodeceptionCommandBuilder::class)
            ->disableOriginalConstructor()->getMock();
        $codeceptionService->setCodeceptionCommandBuilder($codeceptionCommandBuilderMock);
        /** @var CodeceptionImportService|\PHPUnit_Framework_MockObject_MockObject $codeceptionImportServiceMock */
        $codeceptionImportServiceMock = $this->getMockBuilder(CodeceptionImportService::class)
            ->disableOriginalConstructor()->getMock();
        $codeceptionService->setCodeceptionImportService($codeceptionImportServiceMock);
        /** @var CodeService $codeServiceMock|\PHPUnit_Framework_MockObject_MockObject $codeceptionImportServiceMock */
        $codeServiceMock = $this->getMockBuilder(CodeService::class)
            ->disableOriginalConstructor()->getMock();
        $codeceptionService->setCodeService($codeServiceMock);

        $jobInfo = new JobInfo();
        $reflectionProperty = new \ReflectionProperty(JobInfo::class, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($jobInfo, 123);
        $jobInfo->setJobInfoType(JobInfo::JOB_INFO_TYPE_CEST);
        $test = new Test();
        $jobInfo->setTest($test);
        $codeceptionInstance = new CodeceptionInstance();
        $test->setCodeceptionInstance($codeceptionInstance);
        $branch = new CodeBranch();
        $test->setBranch($branch);

        $import = new CodeceptionImport();
        $codeceptionImportServiceMock
            ->expects($this->once())
            ->method('getCodeceptionImport')
            ->with($jobInfo)
            ->willReturn($import);

        $codeServiceMock
            ->expects($this->once())
            ->method('getImportPath')
            ->with($import)
            ->willReturn('/var/www/static/codeception_123456');

        $this->assertEquals(
            '/var/www/static/codeception_123456/tests/_output/123/report.json',
            $codeceptionService->getJsonFilePath($jobInfo)
        );
    }

    public function testGetLogFilePathImportedTest(){
        $codeceptionService = new CodeceptionService();
        /** @var CodeceptionCommandBuilder|\PHPUnit_Framework_MockObject_MockObject $codeceptionCommandBuilderMock */
        $codeceptionCommandBuilderMock = $this->getMockBuilder(CodeceptionCommandBuilder::class)
            ->disableOriginalConstructor()->getMock();
        $codeceptionService->setCodeceptionCommandBuilder($codeceptionCommandBuilderMock);
        /** @var CodeceptionImportService|\PHPUnit_Framework_MockObject_MockObject $codeceptionImportServiceMock */
        $codeceptionImportServiceMock = $this->getMockBuilder(CodeceptionImportService::class)
            ->disableOriginalConstructor()->getMock();
        $codeceptionService->setCodeceptionImportService($codeceptionImportServiceMock);
        /** @var CodeService|\PHPUnit_Framework_MockObject_MockObject $codeServiceMock */
        $codeServiceMock = $this->getMockBuilder(CodeService::class)
            ->disableOriginalConstructor()->getMock();
        $codeceptionService->setCodeService($codeServiceMock);

        $jobInfo = new JobInfo();
        $reflectionProperty = new \ReflectionProperty(JobInfo::class, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($jobInfo, 123);
        $jobInfo->setJobInfoType(JobInfo::JOB_INFO_TYPE_CEST);
        $test = new Test();
        $jobInfo->setTest($test);
        $codeceptionInstance = new CodeceptionInstance();
        $test->setCodeceptionInstance($codeceptionInstance);
        $branch = new CodeBranch();
        $test->setBranch($branch);

        $import = new CodeceptionImport();
        $codeceptionImportServiceMock
            ->expects($this->once())
            ->method('getCodeceptionImport')
            ->with($jobInfo)
            ->willReturn($import);

        $codeServiceMock
            ->expects($this->once())
            ->method('getImportPath')
            ->with($import)
            ->willReturn('/var/www/static/codeception_123456');

        $this->assertEquals(
            '/var/www/static/codeception_123456/tests/_output/123/123.log',
            $codeceptionService->getLogFilePath($jobInfo)
        );
    }

    public function testGetLogFilePathNotImportedTest(){
        $codeceptionService = new CodeceptionService();
        $codeceptionService->setCodeceptionPath('/var/www/static/codeception_123456');

        /** @var CodeceptionCommandBuilder|\PHPUnit_Framework_MockObject_MockObject $codeceptionCommandBuilderMock */
        $codeceptionCommandBuilderMock = $this->getMockBuilder(CodeceptionCommandBuilder::class)
            ->disableOriginalConstructor()->getMock();
        $codeceptionService->setCodeceptionCommandBuilder($codeceptionCommandBuilderMock);
        /** @var CodeceptionImportService|\PHPUnit_Framework_MockObject_MockObject $codeceptionImportServiceMock */
        $codeceptionImportServiceMock = $this->getMockBuilder(CodeceptionImportService::class)
            ->disableOriginalConstructor()->getMock();
        $codeceptionService->setCodeceptionImportService($codeceptionImportServiceMock);
        /** @var CodeService $codeServiceMock|\PHPUnit_Framework_MockObject_MockObject $codeceptionImportServiceMock */
        $codeServiceMock = $this->getMockBuilder(CodeService::class)
            ->disableOriginalConstructor()->getMock();
        $codeceptionService->setCodeService($codeServiceMock);

        $jobInfo = new JobInfo();
        $reflectionProperty = new \ReflectionProperty(JobInfo::class, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($jobInfo, 123);
        $jobInfo->setJobInfoType(JobInfo::JOB_INFO_TYPE_CEPT);

        $this->assertEquals(
            '/var/www/static/codeception_123456/logs/123/123.log',
            $codeceptionService->getLogFilePath($jobInfo)
        );
    }
}
