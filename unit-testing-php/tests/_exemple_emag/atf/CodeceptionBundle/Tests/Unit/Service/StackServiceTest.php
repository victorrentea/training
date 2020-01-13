<?php
namespace Emag\Core\JobBundle\Tests\Unit\Service;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Persistence\ObjectManager;
use Emag\Core\BaseBundle\Exception\AtfException;
use Emag\Core\CodeceptionBundle\Entity\CodeBranch;
use Emag\Core\CodeceptionBundle\Entity\Distribution;
use Emag\Core\CodeceptionBundle\Entity\Environment;
use Emag\Core\CodeceptionBundle\Entity\Stack;
use Emag\Core\CodeceptionBundle\Repository\StackRepository;
use Emag\Core\CodeceptionBundle\Service\DistributionService;
use Emag\Core\CodeceptionBundle\Service\StackService;
use PHPUnit_Framework_MockObject_MockObject as Mock;

class StackServiceTest extends \PHPUnit_Framework_TestCase
{

    /**
     * @var StackService
     */
    private $stackService;

    /**
     * @var StackRepository|Mock
     */
    private $repositoryMock;

    /**
     * @var ObjectManager|Mock
     */
    private $managerMock;

    /**
     * @var array
     */
    private $environmentsListOutput = [];

    protected function setUp()
    {
        $this->stackService = new StackService();

        $this->managerMock = $this->getMockBuilder(ObjectManager::class)->disableOriginalConstructor()->getMock();

        /** @var StackRepository|\PHPUnit_Framework_MockObject_MockObject repositoryMock */
        $this->repositoryMock = $this->getMockBuilder(StackRepository::class)->disableOriginalConstructor()->getMock();
        $this->managerMock->expects($this->any())
            ->method('getRepository')
            ->willReturn($this->repositoryMock);

        $this->stackService->setManager($this->managerMock);

        $this->environmentsListOutput = [
            '3' => [
                'doc' => [
                    'db_host' => 'fdghdfgh',
                    'db_name' => 'dfghdfgh',
                    'host' => 'http://fghfgh',
                    'db_port' => 1234
                ]
            ]
        ];
    }

    public function testAddStackWithNoDetails()
    {
        $stack = $this->stackService->addStack([]);

        $this->assertNull($stack);
    }

    public function testAddStackWithNoEnvironments()
    {
        $stack = $this->stackService->addStack([
            'id' => 0,
            'name' => 'Test Stack'
        ]);

        $this->assertNull($stack);
    }

    public function testAddStackWithEmptyEnvironments()
    {
        $stack = $this->stackService->addStack([
            'id' => 0,
            'name' => 'Test Stack',
            'environments' => []
        ]);

        $this->assertNull($stack);
    }

    public function testAddStackWithStackID()
    {
        $newStack = new Stack();
        $this->repositoryMock
            ->expects($this->once())
            ->method('find')
            ->willReturn($newStack);

        $response = $this->stackService->addStack([
            'id' => 1,
            'name' => 'Test Stack',
            'environments' => []
        ]);

        $this->assertNull($response);
    }

    public function testAddStackExistingStack()
    {
        $existingStack = new Stack();
        $this->repositoryMock
            ->expects($this->once())
            ->method('findOneBy')
            ->willReturn($existingStack);

        $response = $this->stackService->addStack([
            'id' => 0,
            'name' => 'Test Stack',
            'environments' => []
        ]);

        $this->assertNull($response);
    }

    public function testAddStackWithEnvironmentsAndNoDistribution()
    {
        $existingStack = new Stack();
        $this->repositoryMock
            ->expects($this->once())
            ->method('findOneBy')
            ->willReturn($existingStack);

        /** @var DistributionService|\PHPUnit_Framework_MockObject_MockObject $distributionServiceMock */
        $distributionServiceMock = $this->getMockBuilder(DistributionService::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->stackService->setDistributionService($distributionServiceMock);
        $distributionServiceMock->expects($this->once())
            ->method('findOneBy')
            ->with(['code' => 'atf'])
            ->willReturn(null);

        $response = $this->stackService->addStack([
            'id' => 0,
            'name' => 'Test Stack',
            'environments' => [
                'atf' => [
                    'host' => "alexandru-badaluta-dev.atf-all9309-all-dev.atf.c.emag.network",
                    'db' => [
                        'name' => '',
                        'host' => '',
                        'port' => null,
                    ]
                ]
            ]
        ]);

        $this->assertNull($response);
    }

    public function testAddStackWithEnvironmentsWithDistribution()
    {
        $existingStack = new Stack();
        $this->repositoryMock
            ->expects($this->once())
            ->method('findOneBy')
            ->willReturn($existingStack);

        $distributionServiceMock = $this->getMockBuilder(DistributionService::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->stackService->setDistributionService($distributionServiceMock);
        $distributionServiceMock->expects($this->once())
            ->method('findOneBy')
            ->with(['code' => 'atf'])
            ->willReturn(new Distribution());

        $response = $this->stackService->addStack([
            'id' => 0,
            'name' => 'Test Stack',
            'environments' => [
                'atf' => [
                    'host' => "alexandru-badaluta-dev.atf-all9309-all-dev.atf.c.emag.network",
                    'db' => [
                        'name' => '',
                        'host' => '',
                        'port' => null
                    ]
                ]
            ]
        ]);

        $this->assertInstanceOf(Stack::class, $response);
    }

    public function testGetDistributionListForStackAsArray()
    {
        /** @var Stack $stack */
        $stack = new Stack();
        $stack->setName('Stack 18');

        /** @var Environment $environment */
        $environment = new Environment();
        $environment->setHost('http://fghfgh');
        $environment->setDbName('dfghdfgh');
        $environment->setDbHost('fdghdfgh');
        $environment->setDbPort(1234);
        $environment->setStatus(true);

        /** @var Distribution $distribution */
        $distribution = new Distribution();
        $distribution->setName('doc');
        $distribution->setName('doc');
        $distribution->setStatus(true);

        $environment->setDistribution($distribution);

        $stack->addEnvironment($environment);

        $reflectionProperty = new \ReflectionProperty($stack, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($stack, 3);

        $distributionListForStack = $this->stackService->getDistributionListForStackAsArray($stack);

        $this->assertEquals($this->environmentsListOutput, $distributionListForStack);
    }

    public function testGetDistributionUrlByStackAndCodeWhereStackHasNoEnvironments() {
        $code = 'atf';
        $stackId = 16;

        $stack = new Stack();
        $stack->setEnvironments(new ArrayCollection([]));

        $this->repositoryMock
            ->expects($this->once())
            ->method('find')
            ->with($stackId)
            ->willReturn($stack);

        $response = $this->stackService->getDistributionUrlByStackAndCode($code, $stackId);

        $this->assertEquals($response, null);
    }

    public function testGetDistributionUrlByStackAndCodeHasNoStack() {
        $code = 'atf';
        $stackId = 169999;

        $this->repositoryMock
            ->expects($this->once())
            ->method('find')
            ->with($stackId)
            ->willReturn(null);

        $response = $this->stackService->getDistributionUrlByStackAndCode($code, $stackId);

        $this->assertEquals($response, null);
    }

    public function testGetDistributionUrlByStackAndCodeWithEnvironments() {
        $code = 'atf';
        $stackId = 1;
        $envHost = 'atf.emag.local';

        $stack = new Stack();
        $stack->setId($stackId);

        $distribution = new Distribution();
        $distribution->setName($code);

        $environemnt = new Environment();
        $environemnt->setDistribution($distribution);
        $environemnt->setStatus(true);
        $environemnt->setHost($envHost);
        $environemnt->setDbHost('db-host');
        $environemnt->setDbName('db-name');

        $stack->setEnvironments(new ArrayCollection([$environemnt]));

        $this->repositoryMock
            ->expects($this->once())
            ->method('find')
            ->with($stackId)
            ->willReturn($stack);

        $response = $this->stackService->getDistributionUrlByStackAndCode($code, $stackId);

        $this->assertEquals($response, $envHost);
    }
}
