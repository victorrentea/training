<?php
namespace Emag\Core\CodeceptionBundle\Tests\Unit\Service;

use Emag\Core\CodeceptionBundle\Service\CodeceptionConfig;
use Emag\Core\CodeceptionBundle\Service\CodeceptionConfigParser;
use Symfony\Component\Finder\Finder;
use Symfony\Component\Finder\SplFileInfo;
use Symfony\Component\Yaml\Parser;

class CodeceptionConfigParserTest extends \PHPUnit_Framework_TestCase
{
    /**
     * @var SplFileInfo|\PHPUnit_Framework_MockObject_MockObject
     */
    private $fileMock;

    /**
     * @var Finder|\PHPUnit_Framework_MockObject_MockObject
     */
    private $finderMock;

    /**
     * @var Parser|\PHPUnit_Framework_MockObject_MockObject
     */
    private $parserMock;

    protected function setUp()
    {
        $this->fileMock = $this->getMockBuilder(SplFileInfo::class)->setConstructorArgs(['', '', ''])->getMock();
        $this->finderMock = $this->getMockBuilder(Finder::class)->disableOriginalConstructor()->getMock();
        $this->parserMock = $this->getMockBuilder(Parser::class)->disableOriginalConstructor()->getMock();
    }

    public function testParse()
    {
        $parser = new CodeceptionConfigParser($this->finderMock, $this->parserMock);

        $this->fileMock->expects($this->once())->method('getContents')->willReturn('some: yml');

        $this->finderMock
            ->expects($this->once())
            ->method('in')
            ->with('/var/static/codeception')
            ->willReturn($this->finderMock);
        $this->finderMock
            ->expects($this->once())
            ->method('files')
            ->willReturn($this->finderMock);
        $this->finderMock
            ->expects($this->once())
            ->method('name')
            ->with('codeception.yml')
            ->willReturn($this->finderMock);
        $this->finderMock
            ->expects($this->once())
            ->method('getIterator')
            ->willReturn(new \ArrayIterator([$this->fileMock]));

        $this->parserMock->expects($this->once())->method('parse')->with('some: yml')->willReturn(['some' => 'yml']);

        $this->assertEquals(
            new CodeceptionConfig(['some' => 'yml']),
            $parser->parse('/var/static/codeception', 'codeception.yml')
        );
    }
}
