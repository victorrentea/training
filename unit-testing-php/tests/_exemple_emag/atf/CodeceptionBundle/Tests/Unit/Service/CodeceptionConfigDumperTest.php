<?php
namespace Emag\Core\CodeceptionBundle\Tests\Unit\Service;

use Emag\Core\CodeceptionBundle\Service\CodeceptionConfig;
use Emag\Core\CodeceptionBundle\Service\CodeceptionConfigDumper;
use Symfony\Component\Filesystem\Filesystem;
use Symfony\Component\Yaml\Dumper;

class CodeceptionConfigDumperTest extends \PHPUnit_Framework_TestCase
{
    /**
     * @var Filesystem|\PHPUnit_Framework_MockObject_MockObject
     */
    private $filesystemMock;

    /**
     * @var Dumper|\PHPUnit_Framework_MockObject_MockObject
     */
    private $dumperMock;

    protected function setUp()
    {
        $this->filesystemMock = $this->getMockBuilder(Filesystem::class)->disableOriginalConstructor()->getMock();
        $this->dumperMock = $this->getMockBuilder(Dumper::class)->disableOriginalConstructor()->getMock();
    }

    public function testDump()
    {
        $dumper = new CodeceptionConfigDumper($this->filesystemMock, $this->dumperMock);
        $this->filesystemMock
            ->expects($this->once())
            ->method('mkdir')
            ->with(
                [
                    '/var',
                    '/var/static',
                    '/var/static/codeception',
                    '/var/static/codeception/config',
                    '/var/static/codeception/config/123'
                ]
            );
        $this->filesystemMock
            ->expects($this->once())
            ->method('dumpFile')
            ->with('/var/static/codeception/config/123/codeception.yml', "some: yaml");
        $this->dumperMock
            ->expects($this->once())
            ->method('dump')
            ->with(['some' => 'yaml', 'extensions' => ['enabled' => [], 'config' => []], 'modules' => ['config' => []]], 5)
            ->willReturn("some: yaml");

        $this->assertEquals(
            '/var/static/codeception/config/123/codeception.yml',
            $dumper->dump('/var/static/codeception/config/123', 'codeception.yml', new CodeceptionConfig(['some' => 'yaml']))
        );
    }
}
