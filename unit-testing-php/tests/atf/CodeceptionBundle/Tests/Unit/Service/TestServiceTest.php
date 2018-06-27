<?php
namespace Emag\Core\CodeceptionBundle\Tests\Service;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Persistence\ObjectManager;
use Doctrine\ORM\EntityManager;
use Emag\Core\CodeceptionBundle\Entity\{
    Distribution, Tag, Test, TestingPlan, TestInstance
};
use Emag\Core\CodeceptionBundle\Repository\TestRepository;
use Emag\Core\CodeceptionBundle\Service\{
    TagService, TestInstanceService, TestService
};

/**
 * @group unit-tests
 */
class TestServiceTest extends \PHPUnit_Framework_TestCase
{
    public const TEST_DISTRIBUTION = 7;
    public const TEST_COUNTRY = 1;
    public const TEST_RANDOM_ID = 9;

    /** @var TestService $testService */
    protected $testService;

    /** @var TestInstanceService|\PHPUnit_Framework_MockObject_MockObject $testInstanceMock */
    protected $testInstanceMock;

    /** @var TagService|\PHPUnit_Framework_MockObject_MockObject $testInstanceMock */
    protected $tagServiceMock;

    /** @var ObjectManager|\PHPUnit_Framework_MockObject_MockObject $objectManager */
    protected $objectManagerMock;

    /** @var TestRepository|\PHPUnit_Framework_MockObject_MockObject $objectManager */
    protected $testRepositoryMock;

    public function setUp()
    {
        /** @var TestService testService */
        $this->testService = new TestService();

        /** @var TestInstanceService|\PHPUnit_Framework_MockObject_MockObject testInstanceMock */
        $this->testInstanceMock = $this->getMockBuilder(TestInstanceService::class)
            ->disableOriginalConstructor()
            ->getMock();

        /** @var TagService|\PHPUnit_Framework_MockObject_MockObject testInstanceMock */
        $this->tagServiceMock = $this->getMockBuilder(TagService::class)
            ->disableOriginalConstructor()
            ->getMock();

        /** @var ObjectManager|\PHPUnit_Framework_MockObject_MockObject testInstanceMock */
        $this->objectManagerMock = $this->getMockBuilder(ObjectManager::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->testRepositoryMock = $this->getMockBuilder(TestRepository::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->objectManagerMock
            ->expects($this->any())
            ->method('getRepository')
            ->willReturn($this->testRepositoryMock);

        $this->testService->setManager($this->objectManagerMock);
        $this->testService->setTestInstanceService($this->testInstanceMock);
        $this->testService->setTagService($this->tagServiceMock);
    }

    public function testFindTestStepList()
    {
        $testEntityMock = $this->getMock(Test::class, array('getId'));
        $testEntityMock->expects($this->once())
            ->method('getId')
            ->willReturn(static::TEST_RANDOM_ID);

        $repoMock = $this->getMockBuilder(TestRepository::class)->disableOriginalConstructor()->getMock();
        $repoMock->expects($this->once())
            ->method('findTestStepList')
            ->willReturn(array($testEntityMock));

        $managerMockBuilder = $this->getMockBuilder(EntityManager::class)->disableOriginalConstructor();
        $managerMockBuilder->setMethods(array('getRepository'));
        /** @var ObjectManager|\PHPUnit_Framework_MockObject_MockObject $managerMock */
        $managerMock = $managerMockBuilder->getMock();
        $managerMock->expects($this->once())
            ->method('getRepository')
            ->willReturn($repoMock);

        $testService = new TestService();
        $testService->setManager($managerMock);

        /** @var Test[] $result */
        $result = $testService->findTestStepList(
            static::TEST_DISTRIBUTION,
            static::TEST_COUNTRY,
            Test::SUITE_ACCEPTANCE
        );

        $this->assertInternalType('array', $result);
        $this->assertEquals(static::TEST_RANDOM_ID, $result[0]->getId());
    }

    public function testGetDistributionListForTests() {
        $test = new Test();
        $distribution1 = new Distribution();
        $distribution2 = new Distribution();
        $distribution3 = new Distribution();

        $childTest1 = new Test();
        $childTest1->setType('step');
        $childTest1->setDistribution($distribution1);
        $childTest2 = new Test();
        $childTest2->setType('step');
        $childTest2->setDistribution($distribution1);
        $childTest3 = new Test();
        $childTest3->setType('step');
        $childTest3->setDistribution($distribution2);

        $childTest4 = new Test();
        $childTest4->setType('group');
        $childTest4->setDistribution($distribution2);


        $childTest4 = new Test();
        $childTest4->setType('step');
        $childTest4->setDistribution($distribution1);
        $childTest5 = new Test();
        $childTest5->setType('step');
        $childTest5->setDistribution($distribution3);

        $testInstance1 = new TestInstance();
        $testInstance1->setStatus(true);
        $testInstance1->setChild($childTest1);
        $testInstance2 = new TestInstance();
        $testInstance2->setStatus(true);
        $testInstance2->setChild($childTest2);
        $testInstance3 = new TestInstance();
        $testInstance3->setStatus(true);
        $testInstance3->setChild($childTest3);

        $testInstance4 = new TestInstance();
        $testInstance4->setStatus(true);
        $testInstance4->setChild($childTest4);
        $testInstance4->setChild($childTest5);

        $test->addChild($testInstance1);
        $test->addChild($testInstance2);
        $test->addChild($testInstance3);
        $test->addChild($testInstance4);

        $testService = new TestService();

        /** @var ArrayCollection $result */
        $result = $testService->getDistributionListForTest($test);
        $this->assertEquals(new ArrayCollection([$distribution1, $distribution2, $distribution3]), $result);
    }

    public function testGetDistributionListForTestingPlan() {
        $test = new Test();
        $howManyDistributions = 3;
        $howManyTestSteps = 5;
        $howManyTestInstances = 5;

        $distribution = [];
        for ($i = 0; $i < $howManyDistributions; $i++) {
            $distribution[$i] = new Distribution();
            $reflectionProperty = new \ReflectionProperty($distribution[$i], 'id');
            $reflectionProperty->setAccessible(true);
            $reflectionProperty->setValue($distribution[$i], $i + 1);
        }

        $childTest = [];
        for ($i = 0; $i < $howManyTestSteps; $i++) {
            $childTest[$i] = new Test();
            $childTest[$i]->setType('step');
            $childTest[$i]->setDistribution($distribution[$i % $howManyDistributions]);
        }

        $testInstance = [];
        for ($i = 0; $i < $howManyTestInstances; $i++) {
            $testInstance[$i] = new TestInstance();
            $testInstance[$i]->setStatus(true);
            $testInstance[$i]->setChild($childTest[$i]);
        }

        $testGroup = new Test();
        $testGroup->setType('group');
        $testGroup->addChild($testInstance[2]);
        $testGroup->addChild($testInstance[3]);

        $testInstance[5] = new TestInstance();
        $testInstance[5]->setStatus(true);
        $testInstance[5]->setParent($testGroup);
        $testInstance[5]->setChild($childTest[2]);
        $testInstance[5]->setChild($childTest[3]);

        $test->addChild($testInstance[0]);
        $test->addChild($testInstance[1]);
        $test->addChild($testInstance[5]);

        $testingPlan = new TestingPlan();
        $testingPlan->addTest($test);

        $testService = new TestService();

        /** @var ArrayCollection $result */
        $result = $testService->getDistributionListForTestingPlan($testingPlan);

        $this->assertEquals(new ArrayCollection([
            $distribution[1],
            $distribution[0]
        ]), $result);
    }

    public function testGetGroupedChildren()
    {
        $test = new Test();

        $child1 = new Test();
        $child1->setType('step');
        $child1->setStatus(true);

        $child2 = new Test();
        $child2->setType('step');
        $child1->setStatus(true);

        $child3 = new Test();
        $child3->setType('step');
        $child1->setStatus(true);

        $childInstance1 = new TestInstance();
        $childInstance1->setChild($child1);
        $childInstance2 = new TestInstance();
        $childInstance2->setChild($child2);
        $childInstance3 = new TestInstance();
        $childInstance3->setChild($child3);

        $test->setChildren(new ArrayCollection([ $childInstance1, $childInstance2, $childInstance3 ]));

        $this->testService->getGroupedChildren($test);
    }

    /**
     * @return \Generator
     */
    public function _childrenProvider()
    {
        yield [ [ '582:7514', '584:' ], new Test() ];
    }

    /**
     * @dataProvider _childrenProvider
     *
     * @param $children
     * @param $test
     */
    public function testEditChildren($children, $test)
    {
        foreach ($children as $position => $testStepId)
        {
            $ids = explode(":", $testStepId);

            $this->testRepositoryMock
                ->expects($this->any())
                ->method('find')
                ->willReturn(new Test());

            if ((int) $ids[1]) {
                $this->testInstanceMock
                    ->expects($this->any())
                    ->method('findById')
                    ->willReturn(new TestInstance());
            }

            $this->objectManagerMock
                ->expects($this->any())
                ->method('persist');
        }

        $this->testService->editChildren($children, $test);
    }

    /**
     * @return \Generator
     */
    public function _tagsProvider()
    {
        $test = new Test();
        $tag1 = new Tag();
        $tag2 = new Tag();

        $test->addTag($tag1);
        $test->addTag($tag2);

        yield [ [], $test ];
    }

    /**
     * @dataProvider _tagsProvider
     * @param $tags
     * @param Test $test
     */
    public function testEditTags($tags, Test $test)
    {
        if (!empty($tags)) {
            $this->tagServiceMock
                ->expects($this->once())
                ->method('addTags');
        }

        $this->objectManagerMock
            ->expects($this->once())
            ->method('persist');

        $this->objectManagerMock
            ->expects($this->once())
            ->method('flush');

        $this->testService->editTags($test, $tags);
    }

}
