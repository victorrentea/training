<?php

namespace Emag\Core\CodeceptionBundle\Tests\Unit\Service;
use Doctrine\Common\Persistence\ObjectManager;
use Emag\Core\CodeceptionBundle\Entity\Test;
use Emag\Core\CodeceptionBundle\Repository\TagRepository;
use Emag\Core\CodeceptionBundle\Service\TagService;

/**
 * Class TagServiceTest
 * @package Emag\Core\CodeceptionBundle\Tests\Unit\Service
 */
class TagServiceTest extends \PHPUnit_Framework_TestCase
{
    /** @var TagService $tagService */
    protected $tagService;

    /** @var ObjectManager|\PHPUnit_Framework_MockObject_MockObject */
    protected $objectManagerMock;

    /** @var TagRepository|\PHPUnit_Framework_MockObject_MockObject */
    protected $tagRepositoryMock;

    public function setUp()
    {
        /** @var ObjectManager|\PHPUnit_Framework_MockObject_MockObject testInstanceMock */
        $this->objectManagerMock = $this->getMockBuilder(ObjectManager::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->tagRepositoryMock = $this->getMockBuilder(TagRepository::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->objectManagerMock
            ->expects($this->any())
            ->method('getRepository')
            ->willReturn($this->tagRepositoryMock);

        $this->tagService = new TagService();
        $this->tagService->setManager($this->objectManagerMock);
    }

    public function testAddTagsWithNoTags()
    {
        $this->assertFalse($this->tagService->addTags(new Test(), []));
    }

    public function testAddTagsWithTags()
    {
        $this->objectManagerMock
            ->expects($this->once())
            ->method('persist');

        $this->objectManagerMock
            ->expects($this->once())
            ->method('flush');

        $this->assertTrue($this->tagService->addTags(new Test(), [ 1, 2, 3 ]));
    }
}