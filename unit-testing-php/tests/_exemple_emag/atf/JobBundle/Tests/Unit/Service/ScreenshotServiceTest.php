<?php
namespace Emag\Core\JobBundle\Tests\Unit\Service;

use Doctrine\Common\Collections\ArrayCollection;
use Emag\Core\BaseBundle\Builder\FinderBuilder;
use Emag\Core\CodeceptionBundle\Entity\Test;
use Emag\Core\CodeceptionBundle\Service\CodeceptionService;
use Emag\Core\JobBundle\Service\JobService;
use Emag\Core\JobBundle\Service\ScreenshotService;
use Emag\Core\JobBundle\Tests\Util\JobInfoBuilder;
use PHPUnit_Framework_MockObject_MockObject as Mock;
use Symfony\Component\Filesystem\Filesystem;
use Symfony\Component\Finder\Finder;
use Symfony\Component\Finder\SplFileInfo;

class ScreenshotServiceTest extends \PHPUnit_Framework_TestCase
{
    /**
     * @var ScreenshotService
     */
    private $screenshotService;

    /**
     * @var CodeceptionService|Mock
     */
    private $codeceptionServiceMock;

    /**
     * @var FinderBuilder|Mock
     */
    private $finderBuilderMock;

    /**
     * @var Finder|Mock
     */
    private $finderMock;

    /**
     * @var Filesystem|Mock
     */
    private $filesystemMock;

    public function testSetterInjection()
    {
        $staticUrl = new \ReflectionProperty(ScreenshotService::class, 'staticUrl');
        $staticUrl->setAccessible(true);
        $codeceptionUrl = new \ReflectionProperty(ScreenshotService::class, 'codeceptionUrl');
        $codeceptionUrl->setAccessible(true);
        $codeceptionService = new \ReflectionProperty(ScreenshotService::class, 'codeceptionService');
        $codeceptionService->setAccessible(true);
        $finderBuilder = new \ReflectionProperty(ScreenshotService::class, 'finderBuilder');
        $finderBuilder->setAccessible(true);
        $filesystem = new \ReflectionProperty(ScreenshotService::class, 'filesystem');
        $filesystem->setAccessible(true);

        $this->assertEquals('http://static.url', $staticUrl->getValue($this->screenshotService));
        $this->assertEquals('http://static.url/codeception', $codeceptionUrl->getValue($this->screenshotService));
        $this->assertEquals($this->codeceptionServiceMock, $codeceptionService->getValue($this->screenshotService));
        $this->assertEquals($this->finderBuilderMock, $finderBuilder->getValue($this->screenshotService));
        $this->assertEquals($this->filesystemMock, $filesystem->getValue($this->screenshotService));
    }

    public function testFindScreenshotsWillFindTheDefaultFailPngWhenTheRecorderExtensionIsDisabledAndThereIsNoScreenshotHashInTheOutput()
    {
        $jobInfo = $this->getJobInfoBuilder()
            ->withId(42)
            ->withTestType(Test::TYPE_CYCLE)
            ->getJobInfo();

        $this->codeceptionServiceMock
            ->expects($this->once())->method('getScreenshotsHash')
            ->with($jobInfo)->willReturn(null);
        $this->codeceptionServiceMock
            ->expects($this->once())->method('getCodeceptionPath')
            ->with($jobInfo)->willReturn($codeceptionPath = '/var/www/codeception');

        $this->finderMock->expects($this->once())->method('name')
            ->with(ScreenshotService::FAIL_PNG_PATTERN)->willReturn($this->finderMock);
        $this->finderMock->expects($this->once())->method('in')
            ->with($codeceptionPath . sprintf(JobService::TESTS_OUTPUT_FORMAT, $jobInfo->getId()))
            ->willReturn($this->finderMock);
        $this->finderMock->expects($this->once())->method('files')
            ->willReturn($this->finderMock);

        $this->filesystemMock->expects($this->once())
            ->method('exists')
            ->willReturn(true);

        $file = new SplFileInfo(
            '/var/www/static.atf1-dev.emag.local/codeception_main/tests/_output/12345/GeneratedTestCest.ceptTest.fail.png', '', '');
        $this->finderMock->expects($this->once())->method('getIterator')
            ->willReturn(new ArrayCollection([$file]));


        $screenshots = $this->screenshotService->findScreenshots($jobInfo);


        $this->assertEquals(1, $screenshots->count());
        $this->assertEquals(
            '/var/www/static.atf1-dev.emag.local/codeception_main/tests/_output/12345/GeneratedTestCest.ceptTest.fail.png',
            $screenshots[0]->getPath()
        );
        $this->assertEquals(
            'http://static.url/codeception_main/tests/_output/12345/GeneratedTestCest.ceptTest.fail.png',
            $screenshots[0]->getUrl()
        );
    }

    public function testFindScreenshotsWillFindScreenshotsFoldersByHashAndExistingIndexHtmlForImportedTests()
    {
        $jobInfo = $this->getJobInfoBuilder()
            ->withId(42)
            ->withTestType(Test::TYPE_IMPORTED)
            ->getJobInfo();

        $this->codeceptionServiceMock
            ->expects($this->once())->method('getScreenshotsHash')
            ->with($jobInfo)->willReturn($screenshotsHash = substr(md5(microtime()), 0, 13));
        $this->codeceptionServiceMock
            ->expects($this->once())->method('getCodeceptionPath')
            ->with($jobInfo)->willReturn($codeceptionPath = '/var/www/codeception/imported');

        $this->finderMock->expects($this->once())->method('name')
            ->with(sprintf(ScreenshotService::SCREENSHOT_FOLDER_PATTERN, $screenshotsHash))
            ->willReturn($this->finderMock);
        $this->finderMock->expects($this->once())->method('in')
            ->with($codeceptionPath . sprintf(JobService::TESTS_OUTPUT_FORMAT, $jobInfo->getId()))
            ->willReturn($this->finderMock);
        $this->finderMock->expects($this->once())->method('directories')
            ->willReturn($this->finderMock);

        $this->filesystemMock->expects($this->at(0))->method('exists')->willReturn(true);

        $dirname = '/var/www/static.atf1-dev.emag.local/imported/tests/_output/%d/record_%s_TestCest.%s';
        $dirname1 = sprintf($dirname, $jobInfo->getId(), $screenshotsHash, 'someTest');
        $dirname2 = sprintf($dirname, $jobInfo->getId(), $screenshotsHash, 'someOtherTest');
        $dirname3 = sprintf($dirname, $jobInfo->getId(), $screenshotsHash, 'oneMoreTest');
        $dir1 = new SplFileInfo($dirname1, '', '');
        $this->filesystemMock->expects($this->at(1))->method('exists')
            ->with($dirname1 . '/index.html')->willReturn(true);

        $dir2 = new SplFileInfo($dirname2, '', '');
        $this->filesystemMock->expects($this->at(2))->method('exists')
            ->with($dirname2 . '/index.html')->willReturn(false);

        $dir3 = new SplFileInfo($dirname3, '', '');
        $this->filesystemMock->expects($this->at(3))->method('exists')
            ->with($dirname3 . '/index.html')->willReturn(true);

        $this->finderMock->expects($this->once())->method('getIterator')
            ->willReturn(new ArrayCollection([$dir1, $dir2, $dir3]));


        $screenshots = $this->screenshotService->findScreenshots($jobInfo);


        $this->assertEquals(2, $screenshots->count());
        $this->assertEquals($dirname1, $screenshots[0]->getPath());
        $this->assertEquals($dirname3, $screenshots[1]->getPath());
        $this->assertEquals(str_replace('/var/www/static.atf1-dev.emag.local', 'http://static.url', $dirname1), $screenshots[0]->getUrl());
        $this->assertEquals(str_replace('/var/www/static.atf1-dev.emag.local', 'http://static.url', $dirname3), $screenshots[1]->getUrl());
    }

    protected function setUp()
    {
        $this->codeceptionServiceMock = $this->getMockBuilder(CodeceptionService::class)
            ->disableOriginalConstructor()->getMock();
        $this->finderBuilderMock = $this->getMockBuilder(FinderBuilder::class)
            ->disableOriginalConstructor()->getMock();
        $this->finderMock = $this->getMockBuilder(Finder::class)
            ->disableOriginalConstructor()->getMock();
        $this->finderBuilderMock
            ->expects($this->any())
            ->method('makeFinder')
            ->willReturn($this->finderMock);
        $this->filesystemMock = $this->getMockBuilder(Filesystem::class)
            ->disableOriginalConstructor()->getMock();

        $this->screenshotService = new ScreenshotService();
        $this->screenshotService->setStaticUrl('http://static.url');
        $this->screenshotService->setCodeceptionUrl('http://static.url/codeception');
        $this->screenshotService->setCodeceptionService($this->codeceptionServiceMock);
        $this->screenshotService->setFinderBuilder($this->finderBuilderMock);
        $this->screenshotService->setFilesystem($this->filesystemMock);
    }

    private function getJobInfoBuilder()
    {
        return new JobInfoBuilder();
    }
}

function file_exists($filename, $exists = null)
{
    static $cache;

    $key = md5($filename);
    if (is_null($exists)) {
        return array_key_exists($key, $cache) && $cache[$key];
    }

    $cache[$key] = $exists;

    return true;
}
