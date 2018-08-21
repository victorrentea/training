<?php

namespace Emag\Core\JobBundle\Tests\Unit\Service;


use Doctrine\Common\Persistence\ObjectManager;
use Doctrine\ORM\QueryBuilder;
use Emag\Core\BaseBundle\Exception\AtfException;
use Emag\Core\CodeceptionBundle\Entity\CodeceptionImport;
use Emag\Core\CodeceptionBundle\Entity\Stack;
use Emag\Core\CodeceptionBundle\Entity\Test;
use Emag\Core\CodeceptionBundle\Service\CodeceptionImportService;
use Emag\Core\CodeceptionBundle\Service\CodeService;
use Emag\Core\JobBundle\Entity\Job;
use Emag\Core\JobBundle\Entity\JobInfo;
use Emag\Core\JobBundle\Repository\JobInfoRepository;
use Emag\Core\JobBundle\Service\JobInfoService;
use EmagUI\ThemeBundle\Service\JqGridService;

class JobInfoServiceTest extends \PHPUnit_Framework_TestCase
{
    /** @var JobInfoService */
    private $jobInfoService;

    /** @var ObjectManager|\PHPUnit_Framework_MockObject_MockObject */
    private $objectMangerMock;

    /** @var JqGridService|\PHPUnit_Framework_MockObject_MockObject */
    private $jqGridServiceMock;

    /** @var CodeceptionImportService|\PHPUnit_Framework_MockObject_MockObject */
    private $codeceptionImportServiceMock;

    /** @var CodeService|\PHPUnit_Framework_MockObject_MockObject */
    private $codeServiceMock;

    public function setUp()
    {
        $this->jobInfoService = new JobInfoService();

        $this->objectMangerMock = $this->getMockBuilder(ObjectManager::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->jqGridServiceMock = $this->getMockBuilder(JqGridService::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->codeceptionImportServiceMock = $this->getMockBuilder(CodeceptionImportService::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->codeServiceMock = $this->getMockBuilder(CodeService::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->jobInfoService->setManager($this->objectMangerMock);
        $this->jobInfoService->setJqGridService($this->jqGridServiceMock);
        $this->jobInfoService->setCodeceptionPath(__DIR__ . "/Mocks");
        $this->jobInfoService->setCodeceptionImportService($this->codeceptionImportServiceMock);
        $this->jobInfoService->setCodeService($this->codeServiceMock);
    }

    public function testGetOrdersListQueryBuilder()
    {
        $jobInfoRepositoryMock = $this->getMockBuilder(JobInfoRepository::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->objectMangerMock
            ->expects($this->any())
            ->method('getRepository')
            ->willReturn($jobInfoRepositoryMock);

        $qbMock = $this->getMockBuilder(QueryBuilder::class)
            ->disableOriginalConstructor()
            ->getMock();

        $jobInfoRepositoryMock
            ->expects($this->once())
            ->method('getOrdersQueryBuilder')
            ->willReturn($qbMock);

        $this->jqGridServiceMock
            ->expects($this->once())
            ->method('getData')
            ->willReturn([
                'total' => 1,
                'records' => 1,
                'rows' => [
                    0 => [
                        'id' => 2,
                        'cell' => [
                            'id' => 2,
                            'runParams' => '{"login":{"username":"atf.test1@emagbf16.ro","password":"123456!"},"delivery":"courier","payment":"cash"}',
                            'test_id' => "21",
                            'author' => "Victor Dumitru",
                            'start' => new \DateTime("2017-11-01 01:53:31"),
                            'finish' => new \DateTime("2017-11-01 01:55:31"),
                            'duration' => "10",
                            'reportPath' => "/var/www/vd-static/imported_tests/eos-ro_dev-generator_b57036b/tests/_output/2/report.json",
                            'reportName' => "report.html",
                            'orderId' => "fdsfsdfds344",
                            'jobInfoStatus' => "PASS",
                            'jobInfoStatusMessage' => "The job passed",
                            'createdAt' => new \DateTime("2017-10-31 01:55:31"),
                            'screenshotsFolder' => "",
                            'rerun_parent_id' => "",
                            'serialExecution' => "",
                        ]
                    ]
                ]
            ]);

        $jobInfo = new JobInfo();
        $test = new Test();
        $job = new Job();
        $stack = new Stack();


        $jobInfo->setTest($test);
        $jobInfo->setJob($job);
        $stack->setName('Stack 1');
        $job->setStack($stack);

        $jobInfoRepositoryMock
            ->expects($this->once())
            ->method('find')
            ->willReturn($jobInfo);

        $this->jobInfoService->getOrdersListQueryBuilder();
    }

    /**
     * @return \Generator
     * @throws \ReflectionException
     */
    public function _jobInfoProvider()
    {
        $jobInfo1 = new JobInfo();
        $reflectionProperty = new \ReflectionProperty($jobInfo1, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($jobInfo1, 333);
        $jobInfo1->setJobInfoType(JobInfo::JOB_INFO_TYPE_CEPT);

        $jobInfo2 = new JobInfo();
        $reflectionProperty = new \ReflectionProperty($jobInfo2, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($jobInfo2, 444);
        $jobInfo2->setJobInfoType(JobInfo::JOB_INFO_TYPE_CEST);

        $jobInfo3 = new JobInfo();
        $reflectionProperty = new \ReflectionProperty($jobInfo3, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($jobInfo3, 4);
        $jobInfo3->setJobInfoType(JobInfo::JOB_INFO_TYPE_CEST);

        $jobInfo4 = new JobInfo();
        $reflectionProperty = new \ReflectionProperty($jobInfo4, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($jobInfo4, 4);



        yield [$jobInfo1];
        yield [$jobInfo2];
        yield [$jobInfo3];
        yield [$jobInfo4];
    }

    /**
     * @dataProvider _jobInfoProvider
     * @param JobInfo $jobInfo
     */
    public function testGetRunConfigFromStatic(JobInfo $jobInfo)
    {
        if ($jobInfo->getJobInfoType() === JobInfo::JOB_INFO_TYPE_CEST) {
            $codeceptionImport = new CodeceptionImport();

            $this->codeceptionImportServiceMock
                ->expects($this->once())
                ->method('getCodeceptionImport')
                ->with($jobInfo)
                ->willReturn($codeceptionImport);

            $this->codeServiceMock
                ->expects($this->once())
                ->method('getImportPath')
                ->with($codeceptionImport)
                ->willReturn(__DIR__ . "/Mocks");

            $filePath = __DIR__ . '/Mocks/config/' . $jobInfo->getId() . '/codeception.yml';

            if (!empty($filePath) && \file_exists($filePath)) {
                $this->assertEquals(file_get_contents($filePath), $this->jobInfoService->getRunConfigFromStatic($jobInfo));
            } else {
                $this->assertFalse($this->jobInfoService->getRunConfigFromStatic($jobInfo));
            }
        } else {
            $this->assertFalse($this->jobInfoService->getRunConfigFromStatic($jobInfo));
        }
    }
}