<?php
namespace Emag\Core\CodeceptionBundle\Tests\Unit\Service;

use Doctrine\Common\Persistence\ObjectManager;
use Emag\Core\BaseBundle\Exception\AtfException;
use Emag\Core\CodeceptionBundle\Entity\BranchImport;
use Emag\Core\CodeceptionBundle\Entity\CodeBranch;
use Emag\Core\CodeceptionBundle\Entity\CodeceptionImport;
use Emag\Core\CodeceptionBundle\Entity\CodeceptionInstance;
use Emag\Core\CodeceptionBundle\Entity\CodeRepository;
use Emag\Core\CodeceptionBundle\Entity\Test;
use Emag\Core\CodeceptionBundle\Repository\CodeceptionInstanceRepository;
use Emag\Core\CodeceptionBundle\Service\BranchImportService;
use Emag\Core\CodeceptionBundle\Service\CodeBranchService;
use Emag\Core\CodeceptionBundle\Service\CodeceptionImportService;
use Emag\Core\CodeceptionBundle\Service\CodeceptionService;
use Emag\Core\CodeceptionBundle\Service\CodeService;
use Emag\Core\CodeceptionBundle\Service\ComposerService;
use Emag\Core\CodeceptionBundle\Service\DistFilesService;
use Emag\Core\CodeceptionBundle\Service\DistributionService;
use Emag\Core\CodeceptionBundle\Service\ImportedTestService;
use Emag\Core\CodeceptionBundle\Service\TeamService;
use Emag\Core\CodeceptionBundle\Service\TestService;
use Emag\Core\CodeceptionBundle\Service\UserService;
use Psr\Log\LoggerInterface;

class ImportedTestServiceTest extends \PHPUnit_Framework_TestCase
{
    /**
     * @var ImportedTestService|\PHPUnit_Framework_MockObject_MockObject
     */
    private $importedTestService;

    /**
     * @var TestService|\PHPUnit_Framework_MockObject_MockObject
     */
    private $testServiceMock;

    /**
     * @var TeamService|\PHPUnit_Framework_MockObject_MockObject
     */
    private $teamServiceMock;

    /**
     * @var DistributionService|\PHPUnit_Framework_MockObject_MockObject
     */
    private $distributionServiceMock;

    /**
     * @var CodeceptionImportService|\PHPUnit_Framework_MockObject_MockObject
     */
    private $codeceptionImportServiceMock;

    /**
     * @var CodeService|\PHPUnit_Framework_MockObject_MockObject
     */
    private $codeServiceMock;

    /**
     * @var UserService|\PHPUnit_Framework_MockObject_MockObject
     */
    private $userServiceMock;

    /**
     * @var ObjectManager|\PHPUnit_Framework_MockObject_MockObject
     */
    private $managerMock;

    /**
     * @var CodeceptionInstanceRepository|\PHPUnit_Framework_MockObject_MockObject
     */
    private $codeceptionInstanceRepositoryMock;

    /**
     * @var CodeBranchService|\PHPUnit_Framework_MockObject_MockObject
     */
    private $codeBranchServiceMock;

    /**
     * @var ComposerService|\PHPUnit_Framework_MockObject_MockObject
     */
    private $composerServiceMock;

    /**
     * @var BranchImportService|\PHPUnit_Framework_MockObject_MockObject
     */
    private $branchImportServiceMock;

    /**
     * @var LoggerInterface|\PHPUnit_Framework_MockObject_MockObject
     */
    private $loggerMock;

    /**
     * @var CodeceptionService|\PHPUnit_Framework_MockObject_MockObject
     */
    private $codeceptionServiceMock;

    /**
     * @var DistFilesService|\PHPUnit_Framework_MockObject_MockObject
     */
    private $distFilesServiceMock;

    public function setUp()
    {
        $this->testServiceMock = $this->makeTestServiceMock();
        $this->teamServiceMock = $this->makeTeamServiceMock();
        $this->distributionServiceMock = $this->makeDistributionServiceMock();
        $this->codeceptionImportServiceMock = $this->makeCodeceptionImportServiceMock();
        $this->codeServiceMock = $this->makeCodeServiceMock();
        $this->branchImportServiceMock = $this->makeBranchImportServiceMock();

        $this->importedTestService = $this->getMockBuilder(ImportedTestService::class)
            ->setConstructorArgs([
                $this->testServiceMock,
                $this->teamServiceMock,
                $this->distributionServiceMock,
                $this->codeceptionImportServiceMock,
                $this->codeServiceMock
            ])
            ->setMethods(['importTests'])
            ->getMock();

        /** @var UserService|\PHPUnit_Framework_MockObject_MockObject userServiceMock */
        $this->userServiceMock = $this->getMockBuilder(UserService::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->importedTestService->setUserService($this->userServiceMock);

        /** @var ObjectManager|\PHPUnit_Framework_MockObject_MockObject $managerMock */
        $this->managerMock = $this->getMockBuilder(ObjectManager::class)
            ->disableOriginalConstructor()
            ->getMock();

        /** @var CodeceptionInstanceRepository|\PHPUnit_Framework_MockObject_MockObject $codeceptionInstanceRepositoryMock */
        $this->codeceptionInstanceRepositoryMock = $this->getMockBuilder(CodeceptionInstanceRepository::class)
            ->disableOriginalConstructor()
            ->getMock();

        /** @var CodeBranchService|\PHPUnit_Framework_MockObject_MockObject $codeBranchServiceMock */
        $this->codeBranchServiceMock = $this->getMockBuilder(CodeBranchService::class)
            ->disableOriginalConstructor()
            ->getMock();

        /** @var ComposerService|\PHPUnit_Framework_MockObject_MockObject composerServiceMock */
        $this->composerServiceMock = $this->getMockBuilder(ComposerService::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->codeceptionServiceMock = $this->getMockBuilder(CodeceptionService::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->loggerMock = $this->getMockBuilder(LoggerInterface::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->distFilesServiceMock = $this->getMockBuilder(DistFilesService::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->importedTestService->setCodeBranchService($this->codeBranchServiceMock);
        $this->importedTestService->setComposerService($this->composerServiceMock);

        $this->importedTestService->setBranchImportService($this->branchImportServiceMock);

        $this->importedTestService->setCodeceptionService($this->codeceptionServiceMock);
        $this->importedTestService->setLogger($this->loggerMock);
        $this->importedTestService->setManager($this->managerMock);
        $this->importedTestService->setDistFilesService($this->distFilesServiceMock);
    }

    public function testImport()
    {
        $id = 1;

        $repository = new CodeRepository();

        $branch = new CodeBranch();
        $branch->setBranchName('master');
        $branch->setRepository($repository);

        $codeceptionInstance = new CodeceptionInstance();
        $codeceptionInstance->setRepository($repository);

        $branchImport = new BranchImport();
        $branchImport->setBranch($branch);
        $branchImport->setBranchName($branch->getBranchName());
        $branchImport->setCodeceptionInstance($codeceptionInstance);

        $this->branchImportServiceMock
            ->expects($this->once())
            ->method('findById')
            ->with($id)
            ->willReturn($branchImport);

        $latestCodeceptionImport = new CodeceptionImport();

        $this->codeServiceMock->expects($this->once())
            ->method('fetchLatestCommit')
            ->with($codeceptionInstance->getRepository());

        $this->codeceptionImportServiceMock->expects($this->once())
            ->method('findLatestImportByBranch')
            ->with($codeceptionInstance, $branch)
            ->willReturn($latestCodeceptionImport);

        $this->codeServiceMock->expects($this->any())
            ->method('getImportPath')
            ->with($latestCodeceptionImport)
            ->willReturn('/var/www/imports/abcdef');

        $this->composerServiceMock->expects($this->once())
            ->method('install')
            ->with('/var/www/imports/abcdef');

        $this->distFilesServiceMock->expects($this->once())
            ->method('copyDistFiles')
            ->with('/var/www/imports/abcdef');

        $this->importedTestService->import($id);
    }

    public function testImportWithAfException()
    {
        $id = 42;

        $this->branchImportServiceMock
            ->expects($this->once())
            ->method('findById')
            ->with($id)
            ->willReturn(null);

        $this->setExpectedException(AtfException::class, 'No branch import found for id 42');

        $this->importedTestService->import($id);
    }

    public function testGetFullImportPath() {
        $test = new Test();
        $codeceptionImport = new CodeceptionImport();
        $codeceptionInstance = new CodeceptionInstance();
        $test->setCodeceptionInstance($codeceptionInstance);
        $codeBranch = new CodeBranch();
        $test->setBranch($codeBranch);

        $this->codeceptionImportServiceMock
            ->expects($this->once())
            ->method('findLatestImportByBranch')
            ->willReturn($codeceptionImport);

        $this->codeServiceMock
            ->expects($this->once())
            ->method('getImportPath')
            ->with($codeceptionImport);

        $this->importedTestService->getFullImportPath($test);
    }

    /**
     * @return TestService|\PHPUnit_Framework_MockObject_MockObject
     */
    private function makeTestServiceMock()
    {
        return $this->getMockBuilder(TestService::class)->disableOriginalConstructor()->getMock();
    }

    /**
     * @return TeamService|\PHPUnit_Framework_MockObject_MockObject
     */
    private function makeTeamServiceMock()
    {
        return $this->getMockBuilder(TeamService::class)->disableOriginalConstructor()->getMock();
    }

    /**
     * @return DistributionService|\PHPUnit_Framework_MockObject_MockObject
     */
    private function makeDistributionServiceMock()
    {
        return $this->getMockBuilder(DistributionService::class)->disableOriginalConstructor()->getMock();
    }

    /**
     * @return CodeceptionImportService|\PHPUnit_Framework_MockObject_MockObject
     */
    private function makeCodeceptionImportServiceMock()
    {
        return $this->getMockBuilder(CodeceptionImportService::class)->disableOriginalConstructor()->getMock();
    }

    /**
     * @return CodeService|\PHPUnit_Framework_MockObject_MockObject
     */
    private function makeCodeServiceMock()
    {
        return $this->getMockBuilder(CodeService::class)->disableOriginalConstructor()->getMock();
    }

    /**
     * @return BranchImportService|\PHPUnit_Framework_MockObject_MockObject
     */
    private function makeBranchImportServiceMock()
    {
        return $this->getMockBuilder(BranchImportService::class)->disableOriginalConstructor()->getMock();
    }

    /**
     * @return \Generator
     */
    public function _apiParams()
    {
        // Good JSON
        yield [ '{"1":{"ATF\\\Page\\\Login":{"username":"atf.test","password":"TestingUserATF2015"},"ATF\\\Smoke":{"testingPlanId":1,"testCycleId":7,"testGroupId":4,"testStepId":1}}}' ];

        // Bad JSON
        yield [ '{"1":{"ATF\\\Page\\\Login"xxx:{"username":"atf.test","password":"TestingUserATF2015"},"ATF\\\Smoke":{"testingPlanId":1,"testCycleId":7,"testGroupId":4,"testStepId":1}}}' ];
    }

    /**
     * @dataProvider _apiParams
     *
     * @param string $json
     * @throws AtfException
     */
    public function testGetApiRunParams(string $json)
    {
        $params = json_decode($json, true);

        if (json_last_error() == JSON_ERROR_NONE) {
            $this->assertEquals($params, $this->importedTestService->getApiRunParams($json));
        } else {
            $this->setExpectedException(AtfException::class);

            $this->importedTestService->getApiRunParams($json);
        }
    }
}
