<?php

use Doctrine\Common\Persistence\ObjectManager;
use Doctrine\ORM\QueryBuilder;
use Emag\Core\CodeceptionBundle\Service\ReportService;
use Emag\Core\JobBundle\Repository\JobInfoRepository;
use EmagUI\ThemeBundle\Service\JqGridService;
use PHPUnit_Framework_MockObject_MockObject as Mock;

class ReportServiceTest extends \PHPUnit_Framework_TestCase
{

    /**
     * @var JobInfoRepository|Mock
     */
    private $jobInfoRepositoryMock;

    /**
     * @var ReportService
     */
    private $reportService;

    /**
     * @var ObjectManager|Mock
     */
    private $managerMock;

    /**
     * @var JqGridService|Mock
     */
    private $jqGridServiceMock;

    /**
     * @var array
     */
    private $jqGridInput = [];

    /**
     * @var QueryBuilder|Mock
     */
    private $queryBuilderMock;

    /**
     * @var array
     */
    private $jqGridOutput = [];

    protected function setUp()
    {
        $this->reportService = new ReportService();


        /** @var JobInfoRepository|\PHPUnit_Framework_MockObject_MockObject repositoryMock */
        $this->jobInfoRepositoryMock = $this->getMockBuilder(JobInfoRepository::class)->disableOriginalConstructor()->getMock();
        $this->managerMock = $this->getMockBuilder(ObjectManager::class)->disableOriginalConstructor()->getMock();

        $this->reportService->setManager($this->managerMock);

        $this->jqGridServiceMock = $this->getMockBuilder(JqGridService::class)->disableOriginalConstructor()->getMock();
        $this->reportService->setJqGridService($this->jqGridServiceMock);
        $this->queryBuilderMock = $this->getMockBuilder(QueryBuilder::class)->disableOriginalConstructor()->getMock();

        $this->jqGridInput =
            [
                'queryBuilder' => null,
                'rows' => JqGridService::NUMBER_OF_ROWS,
                'filters' => '',
                'filterConditions' => '',
                'useRowsIndexes' => true,
                'currentPage' => '1'

            ];

    }

    public function testGetDeployValidationReport()
    {
        $distributionRepository = $this->getMockBuilder(\Emag\Core\CodeceptionBundle\Repository\DistributionRepository::class)
            ->disableOriginalConstructor()->getMock();

        $this->managerMock->expects($this->once())
            ->method('getRepository')
            ->willReturn($distributionRepository);

        $distributionRepository
            ->expects($this->once())
            ->method('getReportDeployValidationQueryBuilder');

        $this->jqGridServiceMock->expects($this->once())
            ->method('getData')
            ->with($this->jqGridInput)
            ->willReturn(['rows'=>[]]);

        $this->reportService->getDeployValidationReport([]);
    }
    
    public function testGetRunsLastMonth()
    {
        $this->managerMock->expects($this->once())
            ->method('getRepository')
            ->willReturn($this->jobInfoRepositoryMock);

        $this->jobInfoRepositoryMock
            ->expects($this->once())
            ->method('buildMonthlyRuns');

        $this->reportService->getRunsLastMonth("2017-09-20", "2017-09-25");
    }

}