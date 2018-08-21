<?php
namespace Emag\Core\CodeceptionBundle\Tests\Service;

use Doctrine\Common\Persistence\ObjectManager;
use Emag\Core\CodeceptionBundle\Entity\CodeBranch;
use Emag\Core\CodeceptionBundle\Entity\CodeceptionImport;
use Emag\Core\CodeceptionBundle\Entity\CodeceptionInstance;
use Emag\Core\CodeceptionBundle\Entity\Test;
use Emag\Core\CodeceptionBundle\Entity\TestingPlan;
use Emag\Core\CodeceptionBundle\Entity\TestStatus;
use Emag\Core\CodeceptionBundle\Repository\TestingPlanRepository;
use Emag\Core\CodeceptionBundle\Service\CodeceptionImportService;
use Emag\Core\CodeceptionBundle\Service\CodeService;
use Emag\Core\CodeceptionBundle\Service\TestingPlanService;
use Emag\Core\CodeceptionBundle\Service\TestService;
use OldSound\RabbitMqBundle\RabbitMq\Producer;

/**
 * @group unit-tests
 */
class TestingPlanServiceTest extends \PHPUnit_Framework_TestCase
{
    /**
     * @var TestingPlanService
     */
    private $service;

    /**
     * @var ObjectManager|\PHPUnit_Framework_MockObject_MockObject
     */
    private $objectManagerMock;

    /**
     * @var TestingPlanRepository|\PHPUnit_Framework_MockObject_MockObject
     */
    private $testingPlanRepositoryMock;

    /**
     * @var TestService|\PHPUnit_Framework_MockObject_MockObject
     */
    private $testServiceMock;

    /**
     * @var Producer|\PHPUnit_Framework_MockObject_MockObject
     */
    private $syncJiraProducerMock;

    /**
     * @var array
     */
    private $testingPlanData;


    protected function setUp()
    {
        $this->service = new TestingPlanService();

        $this->objectManagerMock = $this->getMockBuilder(ObjectManager::class)
            ->disableOriginalConstructor()
            ->getMock();
        $this->service->setManager($this->objectManagerMock);

        $this->testingPlanRepositoryMock = $this->getMockBuilder(TestingPlanRepository::class)
            ->disableOriginalConstructor()
            ->getMock();
        $this->objectManagerMock
            ->expects($this->any())
            ->method('getRepository')
            ->with('EmagCoreCodeceptionBundle:TestingPlan')
            ->willReturn($this->testingPlanRepositoryMock);

        $this->testServiceMock = $this->getMockBuilder(TestService::class)
            ->disableOriginalConstructor()
            ->getMock();
        $this->service->setTestService($this->testServiceMock);

        $this->syncJiraProducerMock = $this->getMockBuilder(Producer::class)
            ->disableOriginalConstructor()
            ->getMock();
        $this->service->setSyncJiraProducer($this->syncJiraProducerMock);

        /** @var CodeService|\PHPUnit_Framework_MockObject_MockObject $codeServiceMock */
        $codeServiceMock = $this->getMockBuilder(CodeService::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->service->setCodeService($codeServiceMock);

        $this->testingPlanData = [
            'id' => 1234,
            'name' => 'Existing testing plan',
            'testDescription' => 'Existing testing plan',
            'author' => null,
            'jiraKey' => '',
            'testCycles' => '123:|456:456|321:',
            'testStatus' => new TestStatus(),
        ];
    }


    public function testSaveTestingPlan_AddedAndRemovedTestsAreCalculatedCorrectly_WhenAnExistingPlanIsEdited()
    {
        $data = $this->testingPlanData;

        $tests = $this->makeTests(array('123', '456', '321', '213'));

        $existingTestingPlan = new TestingPlan();
        $existingTestingPlan->getTests()->add($tests['123']);
        $existingTestingPlan->getTests()->add($tests['456']);
        $existingTestingPlan->getTests()->add($tests['213']);

        $addedTests = array($tests['321']);
        $removedTests = array($tests['213']);

        $this->testingPlanRepositoryMock
            ->expects($this->once())
            ->method('find')
            ->with($data['id'])
            ->willReturn($existingTestingPlan);

        $i = 0;
        unset($tests['213']);
        foreach ($tests as $id => $test) {
            $this->testServiceMock
                ->expects($this->at($i++))
                ->method('findOneBy')
                ->with(['id' => $id])
                ->willReturn($test);
        }

        $this->service->saveTestingPlan($data);
    }


    public function testSaveTestingPlan_AddedAndRemovedTestsAreCalculatedCorrectly_WhenAnExistingPlanIsEditedWithJiraKey()
    {
        $data = $this->testingPlanData;
        $data['jiraKey'] = 'EMGCTF-0001';

        $tests = $this->makeTests(array('123', '456', '321', '213'));

        $existingTestingPlan = new TestingPlan();
        $existingTestingPlan->getTests()->add($tests['123']);
        $existingTestingPlan->getTests()->add($tests['456']);
        $existingTestingPlan->getTests()->add($tests['213']);

        $this->testingPlanRepositoryMock
            ->expects($this->once())
            ->method('find')
            ->with($data['id'])
            ->willReturn($existingTestingPlan);

        $i = 0;
        unset($tests['213']);
        foreach ($tests as $id => $test) {
            $this->testServiceMock
                ->expects($this->at($i++))
                ->method('findOneBy')
                ->with(['id' => $id])
                ->willReturn($test);
        }

        $this->syncJiraProducerMock
            ->expects($this->once())
            ->method('publish');

        $this->service->saveTestingPlan($data);
    }

    public function testApiTestsParams()
    {
        $testPlan = new TestingPlan();

        $test1 = new Test();
        $test1->setType('imported');
        $test1->setCodeceptionInstance(new CodeceptionInstance());
        $test1->setBranch(new CodeBranch());
        $test1->setStatus(true);

        $test2 = new Test();

        $test3 = new Test();
        $test3->setType('imported');
        $test3->setCodeceptionInstance(new CodeceptionInstance());
        $test3->setBranch(new CodeBranch());
        $test3->setStatus(true);

        $testPlan->addTest($test1);
        $testPlan->addTest($test2);
        $testPlan->addTest($test3);

        /** @var CodeceptionImportService|\PHPUnit_Framework_MockObject_MockObject $codeceptionImportServiceMock */
        $codeceptionImportServiceMock = $this->getMockBuilder(CodeceptionImportService::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->service->setCodeceptionImportService($codeceptionImportServiceMock);

        $codeceptionImportServiceMock
            ->expects($this->any())
            ->method('findLatestImportByBranch')
            ->willReturn(new CodeceptionImport());

        $codeceptionImportServiceMock
            ->expects($this->any())
            ->method('getConfigParams');

        $this->service->apiTestsParams($testPlan);
    }

    /**
     * @param $id
     *
     * @return Test
     */
    private function makeTest($id)
    {
        $test = new Test();

        $reflectionProperty = new \ReflectionProperty(Test::class, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($test, $id);

        return $test;
    }

    /**
     * @param $ids
     *
     * @return array
     */
    private function makeTests($ids)
    {
        $tests = array();

        foreach ($ids as $id) {
            $tests[$id] = $this->makeTest($id);
        }

        return $tests;
    }
}
