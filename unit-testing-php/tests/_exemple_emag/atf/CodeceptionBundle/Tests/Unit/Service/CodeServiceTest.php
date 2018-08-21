<?php
namespace Emag\Core\CodeceptionBundle\Tests\Unit\Service;

use Codeception\Lib\Di;
use Emag\Core\CodeceptionBundle\Entity\CodeBranch;
use Emag\Core\CodeceptionBundle\Entity\CodeceptionImport;
use Emag\Core\CodeceptionBundle\Entity\CodeceptionInstance;
use Emag\Core\CodeceptionBundle\Entity\CodeRepository;
use Emag\Core\CodeceptionBundle\Entity\Distribution;
use Emag\Core\CodeceptionBundle\Service\CodeRepositoryService;
use Emag\Core\CodeceptionBundle\Service\CodeService;
use Symfony\Component\Filesystem\Filesystem;

class CodeServiceTest extends \PHPUnit_Framework_TestCase
{
    public const STATIC_PATH = '/var/static';
    public const STATIC_URL = 'http://static.emag.local';

    /**
     * @var CodeService
     */
    private $codeService;

    /**
     * @var CodeRepositoryService|\PHPUnit_Framework_MockObject_MockObject
     */
    private $codeRepositoryServiceMock;

    /**
     * @var Filesystem|\PHPUnit_Framework_MockObject_MockObject
     */
    private $filesystemMock;

    protected function setUp()
    {
        $this->codeRepositoryServiceMock = $this->getMockBuilder(CodeRepositoryService::class)->disableOriginalConstructor()->getMock();
        $this->filesystemMock = $this->getMockBuilder(Filesystem::class)->disableOriginalConstructor()->getMock();
        $this->codeService = new CodeService(static::STATIC_PATH, static::STATIC_URL, $this->codeRepositoryServiceMock, $this->filesystemMock);
    }

    public function testGetRepositoriesPath()
    {
        $this->assertEquals('/var/static/imported_repositories', $this->codeService->getRepositoriesPath());
    }

    public function testGetImportsPath()
    {
        $this->assertEquals('/var/static/imported_tests', $this->codeService->getImportsPath());
    }

    public function testGetRepositoryPath()
    {
        $id = 123;
        $alias = 'some-repo';

        $repository = $this->makeRepository($id, $alias);

        $this->assertEquals('/var/static/imported_repositories/123_some_repo', $this->codeService->getRepositoryPath($repository));
    }

    public function testGetImportFolder()
    {
        $codeceptionImport = new CodeceptionImport();

        $codeceptionInstance = new CodeceptionInstance();
        $distribution = new Distribution();
        $distribution->setCode('atf');
        $codeceptionInstance->setDistribution($distribution);
        $codeceptionImport->setInstance($codeceptionInstance);

        $branch = new CodeBranch();
        $branch->setBranchName('master');
        $codeceptionImport->setBranch($branch);

        $codeceptionImport->setHash(sha1('#hash'));

        $this->assertEquals('atf_master_c6e1f36', $this->codeService->getImportFolder($codeceptionImport));
    }

    public function testGetImportPath()
    {
        $codeceptionImport = new CodeceptionImport();

        $codeceptionInstance = new CodeceptionInstance();
        $distribution = new Distribution();
        $distribution->setCode('atf');
        $codeceptionInstance->setDistribution($distribution);
        $codeceptionImport->setInstance($codeceptionInstance);

        $branch = new CodeBranch();
        $branch->setBranchName('master');
        $codeceptionImport->setBranch($branch);

        $codeceptionImport->setHash(sha1('#hash'));

        $this->assertEquals('/var/static/imported_tests/atf_master_c6e1f36', $this->codeService->getImportPath($codeceptionImport));
    }

    public function testGetImportUrl()
    {
        $codeceptionImport = new CodeceptionImport();

        $codeceptionInstance = new CodeceptionInstance();
        $distribution = new Distribution();
        $distribution->setCode('atf');
        $codeceptionInstance->setDistribution($distribution);
        $codeceptionImport->setInstance($codeceptionInstance);

        $branch = new CodeBranch();
        $branch->setBranchName('master');
        $codeceptionImport->setBranch($branch);

        $codeceptionImport->setHash(sha1('#hash'));

        $this->assertEquals('http://static.emag.local/imported_tests/atf_master_c6e1f36', $this->codeService->getImportUrl($codeceptionImport));
    }

    /**
     * @param $id
     * @param $alias
     * @return CodeRepository
     */
    private function makeRepository($id, $alias)
    {
        $repository = new CodeRepository();

        $reflectionProperty = new \ReflectionProperty(CodeRepository::class, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($repository, $id);

        $repository->setAlias($alias);

        return $repository;
    }
}
