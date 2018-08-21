<?php

namespace Emag\Core\CodeceptionBundle\Tests\Unit\Service;

use Emag\Core\BaseBundle\Builder\FinderBuilder;
use Emag\Core\CodeceptionBundle\Entity\CodeceptionImport;
use Emag\Core\CodeceptionBundle\Service\CodeBranchService;
use Emag\Core\CodeceptionBundle\Service\CodeceptionImportService;
use Emag\Core\CodeceptionBundle\Service\CodeService;
use Symfony\Component\Filesystem\Filesystem;
use Doctrine\Common\Persistence\ObjectManager;
use Emag\Core\CodeceptionBundle\Entity\CodeBranch;
use Emag\Core\CodeceptionBundle\Entity\CodeceptionInstance;
use Emag\Core\CodeceptionBundle\Entity\CodeRepository;
use Emag\Core\CodeceptionBundle\Entity\Test;
use Emag\Core\CodeceptionBundle\Repository\CodeceptionImportRepository;
use Emag\Core\CodeceptionBundle\Repository\TestingPlanInstanceRepository;
use Emag\Core\CodeceptionBundle\Service\TestService;
use Symfony\Component\Finder\Finder;
use Symfony\Component\Finder\SplFileInfo;
use Symfony\Component\Yaml\Parser;

require_once __DIR__ . '/Overrides.php';

class CodeceptionImportServiceTest extends \PHPUnit_Framework_TestCase
{
    public static function file_get_contents($filename)
    {
        $returns = [
            '/var/www/static.atf1-dev.emag.local/imported_tests/atf_smoke_ad5ffc3/tests/smoke.suite.yml' => 'class_name: SmokeTester
modules:
    enabled:
        - PhpBrowser:
            url: http://google.ro
        - \INTEGRATION\Helper\Smoke
        - \INTEGRATION\Helper\Atf',
            '/var/www/static.atf1-dev.emag.local/imported_tests/something/tests/acceptance.suite.yml' => 'class_name: SmokeTester
modules:
    enabled:
        - WebDriver:
            url: http://google.ro',

            '/tests-diff.yml' => 'added:
    - acceptance/ListingTestStepCest.php
    - acceptance/LoginCest.php
removed:
    - acceptance/SomethingCest.php'
        ];

        return $returns[$filename] ?? '';
    }

    /** @var ObjectManager|\PHPUnit_Framework_MockObject_MockObject $codeBranchServiceMock */
    protected $objManagerMock;

    /** @var CodeceptionImportRepository|\PHPUnit_Framework_MockObject_MockObject $codeBranchServiceMock */
    protected $codeceptionImportMock;

    /** @var CodeBranchService|\PHPUnit_Framework_MockObject_MockObject $codeBranchServiceMock */
    protected $codeBranchServiceMock;

    /** @var CodeService|\PHPUnit_Framework_MockObject_MockObject $codeBranchServiceMock */
    protected $codeServiceMock;

    /** @var Filesystem|\PHPUnit_Framework_MockObject_MockObject $filesystemMock */
    protected $filesystemMock;

    /**
     * @var Finder|\PHPUnit_Framework_MockObject_MockObject
     */
    private $finderMock;

    /**
     * @var FinderBuilder|\PHPUnit_Framework_MockObject_MockObject
     */
    private $finderBuilderMock;

    /** @var TestService|\PHPUnit_Framework_MockObject_MockObject $filesystemMock */
    protected $testServiceMock;

    /** @var CodeceptionImportService */
    protected $codeceptionImportService;

    public function setUp()
    {
        $GLOBALS['className'] = static::class;

        $this->codeBranchServiceMock = $this
            ->getMockBuilder(CodeBranchService::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->codeServiceMock = $this
            ->getMockBuilder(CodeService::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->filesystemMock = $this
            ->getMockBuilder(Filesystem::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->testServiceMock = $this
            ->getMockBuilder(TestService::class)
            ->disableOriginalConstructor()
            ->getMock();

        /** @var ObjectManager|\PHPUnit_Framework_MockObject_MockObject $objManagerMock */
        $this->objManagerMock = $this
            ->getMockBuilder(ObjectManager::class)
            ->disableOriginalConstructor()
            ->getMock();

        /** @var CodeceptionImportRepository|\PHPUnit_Framework_MockObject_MockObject $codeceptionImportMock */
        $this->codeceptionImportMock = $this->getMockBuilder(CodeceptionImportRepository::class)->disableOriginalConstructor()->getMock();

        $this->objManagerMock
            ->expects($this->any())
            ->method('getRepository')
            ->willReturn($this->codeceptionImportMock);

        /** @var Finder|\PHPUnit_Framework_MockObject_MockObject $finderMock */
        $finderMock = $this->getMockBuilder(Finder::class)->disableOriginalConstructor()->getMock();
        /** @var FinderBuilder|\PHPUnit_Framework_MockObject_MockObject $finderBuilderMock */
        $finderBuilderMock = $this->getMockBuilder(FinderBuilder::class)->getMock();
        $finderBuilderMock->expects($this->any())->method('makeFinder')->willReturn($finderMock);

        $this->finderMock = $finderMock;
        $this->finderBuilderMock = $finderBuilderMock;

        $this->codeceptionImportService = new CodeceptionImportService(
            $this->codeBranchServiceMock,
            $this->codeServiceMock,
            $this->filesystemMock
        );

        $this->codeceptionImportService->setFinderBuilder($this->finderBuilderMock);
        $this->codeceptionImportService->setTestService($this->testServiceMock);
        $this->codeceptionImportService->setManager($this->objManagerMock);
    }

    public function testIsWebDriverSuiteWithNoPathProvided()
    {
        $codeceptionImportService = new CodeceptionImportService($this->codeBranchServiceMock, $this->codeServiceMock, $this->filesystemMock);

        $this->assertEquals(false, $codeceptionImportService->isWebDriverSuite(new Test));
    }

    public function testIsWebDriverSuiteWherePathDoesNotExist()
    {
        $codeceptionImportService = new CodeceptionImportService($this->codeBranchServiceMock, $this->codeServiceMock, $this->filesystemMock);

        $this->filesystemMock
            ->expects($this->any())
            ->method('exists')
            ->willReturn(false);

        $this->assertEquals(false, $codeceptionImportService->isWebDriverSuite(new Test));
    }

    public function testIsWebDriverSuiteWhereNoWebDriverFound()
    {
        $codeceptionImportService = new CodeceptionImportService($this->codeBranchServiceMock, $this->codeServiceMock, $this->filesystemMock);

        $this->filesystemMock
            ->expects($this->any())
            ->method('exists')
            ->willReturn(true);

        $test = new Test;
        $test->setCodePath('smoke/IproSmokeCest.php');

        $this->assertEquals(false, $codeceptionImportService->isWebDriverSuite($test));
    }

    public function testIsWebDriverSuiteWhereWebDriverFound()
    {
        $codeceptionImportService = new CodeceptionImportService($this->codeBranchServiceMock, $this->codeServiceMock, $this->filesystemMock);

        $this->filesystemMock
            ->expects($this->any())
            ->method('exists')
            ->willReturn(true);

        $test = new Test;
        $test->setCodePath('acceptance/LoginCestWhatever.php');

        $this->assertEquals(true, $codeceptionImportService->isWebDriverSuite($test));
    }

    public function testGetCodeceptByImportPath()
    {
        $importPath = '/var/www/static.atf1-dev.emag.local/imported_tests/something';
        $files = new \ArrayObject([
            new SplFileInfo($importPath .'/vendor/codeception/codeception/codecept', '', 'codecept'),
        ]);

        $this->finderMock->expects($this->any())->method('in')->willReturn($this->finderMock);
        $this->finderMock->expects($this->any())->method('files')->willReturn($this->finderMock);
        $this->finderMock->expects($this->any())->method('name')->willReturn($this->finderMock);
        $this->finderMock->expects($this->any())->method('depth')->willReturn($files);

        $this->finderMock->expects($this->once())->method('in')->with($importPath);
        $this->finderMock->expects($this->once())->method('files');
        $this->finderMock->expects($this->once())->method('name')->with('/^codecept(\.phar)?$/');
        $this->finderMock->expects($this->once())->method('depth')->with('< 3');

        $this->codeceptionImportService->getCodeceptByImportPath($importPath);
    }

    public function testDeleteTests()
    {
        $codeceptionInstance = new CodeceptionInstance();
        $codeRepository = new CodeRepository();
        $codeceptionInstance->setRepository($codeRepository);
        $codeceptionImportService = new CodeceptionImportService(
            $this->codeBranchServiceMock,
            $this->codeServiceMock,
            $this->filesystemMock
        );

        $codeceptionImportService->setTestService($this->testServiceMock);

        /** @var ObjectManager|\PHPUnit_Framework_MockObject_MockObject $objManagerMock */
        $objManagerMock = $this
            ->getMockBuilder(ObjectManager::class)
            ->disableOriginalConstructor()
            ->getMock();

        $codeceptionImportService->setManager($objManagerMock);

        /** @var CodeceptionImportRepository|\PHPUnit_Framework_MockObject_MockObject $codeceptionImportMock */
        $codeceptionImportMock = $this->getMockBuilder(CodeceptionImportRepository::class)->disableOriginalConstructor()->getMock();
        $objManagerMock
            ->expects($this->any())
            ->method('getRepository')
            ->willReturn($codeceptionImportMock);

        $codeceptionImport1 = new CodeceptionImport();
        $codeceptionImport1->setBranch(new CodeBranch());
        $codeceptionImport2 = new CodeceptionImport();
        $codeceptionImport2->setBranch(new CodeBranch());
        $codeceptionImportMock
            ->expects($this->any())
            ->method('findBy')
            ->willReturn([$codeceptionImport1, $codeceptionImport2]);

        $this->codeServiceMock
            ->expects($this->at(0))
            ->method('getRepositoryPath')
            ->willReturn('a_path');

        $this->filesystemMock
            ->expects($this->at(0))
            ->method('exists')
            ->willReturn(true);

        $this->filesystemMock
            ->expects($this->once())
            ->method('remove');

        $this->codeServiceMock
            ->expects($this->at(1))
            ->method('getImportPath')
            ->willReturn('random_path');

        $this->filesystemMock
            ->expects($this->at(1))
            ->method('exists')
            ->willReturn(true);

        $t1 = new Test();
        $t2 = new Test();
        $this->testServiceMock
            ->expects($this->any())
            ->method('findBy')
            ->willReturn([$t1, $t2]);

        /** @var TestingPlanInstanceRepository|\PHPUnit_Framework_MockObject_MockObject $testingPlanInstanceRepositoryMock */
        $testingPlanInstanceRepositoryMock = $this
            ->getMockBuilder(TestingPlanInstanceRepository::class)
            ->disableOriginalConstructor()

            ->getMock();
        $objManagerMock
            ->expects($this->any())
            ->method('getRepository')
            ->willReturn($testingPlanInstanceRepositoryMock);

        $testingPlanInstanceRepositoryMock
            ->expects($this->any())
            ->method('findBy')
            ->willReturn([]);

        $objManagerMock->expects($this->once())->method('flush');

        $this->assertEquals([
            'success' => true,
            'removed' => 4,
        ], $codeceptionImportService->deleteTests($codeceptionInstance));
    }

    public function testGetDiffFile() {
        $codeceptionImportService = new CodeceptionImportService(
            $this->codeBranchServiceMock,
            $this->codeServiceMock,
            $this->filesystemMock
        );

        /** @var ObjectManager|\PHPUnit_Framework_MockObject_MockObject $objManagerMock */
        $objManagerMock = $this
            ->getMockBuilder(ObjectManager::class)
            ->disableOriginalConstructor()
            ->getMock();

        $codeceptionImportService->setManager($objManagerMock);

        $codeceptionInstance = new CodeceptionInstance();
        $codeBranch = new CodeBranch();

        $codeceptionImportRepository = $this->getMockBuilder(CodeceptionImportRepository::class)
            ->disableOriginalConstructor()
            ->getMock();
        $objManagerMock->expects($this->once())
            ->method('getRepository')
            ->willReturn($codeceptionImportRepository);

        $codeceptionImportRepository->expects($this->once())
            ->method('findOneBy')
            ->willReturn(new CodeceptionImport());

        $this->assertEquals([
            'added' => [
                'acceptance/ListingTestStepCest.php',
                'acceptance/LoginCest.php'
            ],
            'removed' => [
                'acceptance/SomethingCest.php'
            ]
        ], $codeceptionImportService->getDiffFile($codeceptionInstance, $codeBranch));
    }

    public function testGetConfigParams()
    {
        $codeceptionImport = new CodeceptionImport();

        $path = __DIR__ . '/Mocks';

        $this->codeServiceMock
            ->expects($this->once())
            ->method('getImportPath')
            ->willReturn($path);

        $config = (new Parser())->parse(
            file_get_contents('src/Emag/Core/CodeceptionBundle/Tests/Unit/Service/Mocks/codeception.yml')
        );

        $this->assertEquals(
            $config['modules']['config']['Params'],
            $this->codeceptionImportService->getConfigParams($codeceptionImport)
        );
    }

    public function testGetFormattedConfigParams()
    {
        $codeceptionImport = new CodeceptionImport();

        $path = __DIR__ . '/Mocks';

        $this->codeServiceMock
            ->expects($this->once())
            ->method('getImportPath')
            ->willReturn($path);

        $this->codeceptionImportService->getFormattedConfigParams($codeceptionImport);
    }
}
