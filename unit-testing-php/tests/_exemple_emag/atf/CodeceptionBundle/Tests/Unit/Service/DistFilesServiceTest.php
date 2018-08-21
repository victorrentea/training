<?php
namespace Emag\Core\CodeceptionBundle\Service;

use Emag\Core\BaseBundle\Builder\FinderBuilder;
use Symfony\Component\Filesystem\Filesystem;
use Symfony\Component\Finder\Finder;
use Symfony\Component\Finder\SplFileInfo;

class DistFilesServiceTest extends \PHPUnit_Framework_TestCase
{
    /**
     * @var DistFilesService
     */
    private $distFilesService;

    /**
     * @var Finder|\PHPUnit_Framework_MockObject_MockObject
     */
    private $finderMock;

    /**
     * @var Filesystem|\PHPUnit_Framework_MockObject_MockObject
     */
    private $filesystemMock;

    protected function setUp()
    {
        /** @var Finder|\PHPUnit_Framework_MockObject_MockObject $finderMock */
        $finderMock = $this->getMockBuilder(Finder::class)->disableOriginalConstructor()->getMock();
        /** @var FinderBuilder|\PHPUnit_Framework_MockObject_MockObject $finderBuilderMock */
        $finderBuilderMock = $this->getMockBuilder(FinderBuilder::class)->getMock();
        $finderBuilderMock->expects($this->any())->method('makeFinder')->willReturn($finderMock);

        /** @var Filesystem|\PHPUnit_Framework_MockObject_MockObject $filesystemMock */
        $filesystemMock = $this->getMockBuilder(Filesystem::class)->disableOriginalConstructor()->getMock();

        $distFilesService = new DistFilesService();
        $distFilesService->setFinderBuilder($finderBuilderMock);
        $distFilesService->setFilesystem($filesystemMock);

        $this->finderMock = $finderMock;
        $this->filesystemMock = $filesystemMock;
        $this->distFilesService = $distFilesService;
    }

    public function testCopyDistFilesWillCopyNothingWhenItFindsNothing()
    {
        $this->finderMock->expects($this->once())->method('in')->with('/var/www');
        $this->finderMock->expects($this->once())->method('getIterator')->willReturn(new \ArrayObject());
        $this->filesystemMock->expects($this->never())->method('copy');

        $this->distFilesService->copyDistFiles('/var/www');
    }

    public function testCopyDistFilesWillCopyFilesWithoutTheDistExtensionWhenItFindsAny()
    {
        $this->finderMock->expects($this->once())->method('in')->with('/var/www/project');

        $files = new \ArrayObject([
            new SplFileInfo('/var/www/project/codeception.yml.dist', '', 'codeception.yml.dist'),
        ]);
        $this->finderMock->expects($this->once())->method('getIterator')->willReturn($files);
        $this->filesystemMock->expects($this->once())->method('copy')
            ->with('/var/www/project/codeception.yml.dist', '/var/www/project/codeception.yml', false);

        $this->distFilesService->copyDistFiles('/var/www/project');
    }
}
