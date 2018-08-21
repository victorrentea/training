<?php
namespace Emag\Core\JobBundle\Tests\Unit\Service;

use Doctrine\Common\Persistence\ObjectManager;
use Doctrine\ORM\QueryBuilder;
use Emag\Core\BaseBundle\Exception\AtfException;
use Emag\Core\CodeceptionBundle\Entity\Stack;
use Emag\Core\CodeceptionBundle\Entity\Team;
use Emag\Core\CodeceptionBundle\Entity\TestingPlan;
use Emag\Core\JobBundle\Entity\JobSchedule;
use Emag\Core\JobBundle\Repository\JobScheduleRepository;
use Emag\Core\JobBundle\Service\JobScheduleService;
use Emag\Core\UserBundle\Entity\User;
use EmagUI\ThemeBundle\Service\JqGridService;
use GuzzleHttp\Client;
use GuzzleHttp\Exception\ClientException;
use GuzzleHttp\Psr7\Response;
use PHPUnit_Framework_MockObject_MockObject as Mock;
use Psr\Http\Message\ResponseInterface;
use Symfony\Bundle\FrameworkBundle\Routing\Router;

class JobScheduleServiceTest extends \PHPUnit_Framework_TestCase
{
    public const ATF_BASE_URL = 'http://atf.emag.network';
    public const JENKINS_BASE_URL = 'http://jenkins2.emag.network:8080';
    public const JENKINS_USERNAME = 'jenkins';
    public const JENKINS_PASSWORD = 'j3nk1n5';
    public const JENKINS_CREDENTIALS_ID = '9a99a9aa-9999-9999-9999-5rcret9999a*';
    public const ATF_RAW_REPO = 'ssh://git@stash.emag.network/emgctf/atf-raw.git';
    public const ATF_RAW_RUN_SERVER = 'atf1-test';

    /**
     * @var JobScheduleService
     */
    private $jobScheduleService;

    /**
     * @var JobScheduleService|Mock
     */
    private $jobScheduleServiceMock;

    /**
     * @var JobScheduleRepository|Mock
     */
    private $repositoryMock;

    /**
     * @var ObjectManager|Mock
     */
    private $managerMock;

    /**
     * @var QueryBuilder|Mock
     */
    private $queryBuilderMock;

    /**
     * @var JqGridService|Mock
     */
    private $jqGridServiceMock;

    /**
     * @var Client|Mock
     */
    private $httpClientMock;

    /**
     * @var array
     */
    private $jqGridInput = [];

    /**
     * @var array
     */
    private $jqGridOutput = [];

    /**
     * @var array
     */
    private $findAllOutput = [];

    /**
     * @var array
     */
    private $viewActionOutput = [];

    /**
     * @var JobSchedule
     */
    private $jobSchedule;

    protected function setUp()
    {
        $this->jobScheduleService = new JobScheduleService();

        $this->managerMock = $this->getMockBuilder(ObjectManager::class)->disableOriginalConstructor()->getMock();
        $this->jobScheduleService->setManager($this->managerMock);

        $this->jobScheduleServiceMock = $this->getMockBuilder(JobScheduleService::class)->disableOriginalConstructor()->getMock();

        $this->repositoryMock = $this->getMockBuilder(JobScheduleRepository::class)->disableOriginalConstructor()->getMock();
        $this->managerMock->expects($this->any())
                          ->method('getRepository')
                          ->willReturn($this->repositoryMock);

        $this->queryBuilderMock = $this->getMockBuilder(QueryBuilder::class)->disableOriginalConstructor()->getMock();

        $this->jqGridServiceMock = $this->getMockBuilder(JqGridService::class)->disableOriginalConstructor()->getMock();
        $this->jobScheduleService->setJqGridService($this->jqGridServiceMock);

        $this->httpClientMock = $this->getMockBuilder(Client::class)->disableOriginalConstructor()
                                     ->setMethods(['post', 'get'])->getMock();
        $this->jobScheduleService->setHttpClient($this->httpClientMock);

        $routerMock = $this->getMockBuilder(\Symfony\Component\Routing\Router::class)->disableOriginalConstructor()->getMock();
        $this->jobScheduleService->setRouter($routerMock);

        $this->jobScheduleService->setAtfBaseUrl(static::ATF_BASE_URL);
        $this->jobScheduleService->setJenkinsBaseUrl(static::JENKINS_BASE_URL);
        $this->jobScheduleService->setJenkinsUsername(static::JENKINS_USERNAME);
        $this->jobScheduleService->setJenkinsPassword(static::JENKINS_PASSWORD);
        $this->jobScheduleService->setJenkinsCredentialsId(static::JENKINS_CREDENTIALS_ID);
        $this->jobScheduleService->setAtfRawRepo(static::ATF_RAW_REPO);
        $this->jobScheduleService->setAtfRawRunServer(static::ATF_RAW_RUN_SERVER);

        $this->jqGridInput =
            [
                'queryBuilder'     => $this->queryBuilderMock,
                'sortBy'           => JqGridService::SORT_COLUMN,
                'sortOrder'        => JqGridService::SORT_DIRECTION,
                'rows'             => JqGridService::NUMBER_OF_ROWS,
                'filters'          => '',
                'filterConditions' => [
                    1 => ['like', 'and'],
                    2 => ['like', 'and'],
                    3 => ['like', 'and'],
                    4 => ['like', 'and'],
                ],
                'currentPage'      => '1',
            ];

        $lastJob = null;

        $this->jqGridOutput = [
            'total'   => 1,
            'page'    => '1',
            'records' => 2,
            'rows'    => [
                [
                    'id'   => 1,
                    'cell' =>
                        [
                            'id'                => 1,
                            'name'              => 'test schedule',
                            'schedule'          => '0 0 * * *',
                            'createdAt'         =>
                                [
                                    'date'          => '2016-09-15 10:13:57.000000',
                                    'timezone_type' => 3,
                                    'timezone'      => 'Europe/Bucharest',
                                ],
                            'modifiedAt'        =>
                                [
                                    'date'          => '2016-09-15 10:13:59.000000',
                                    'timezone_type' => 3,
                                    'timezone'      => 'Europe/Bucharest',
                                ],
                            'user_id'           => 94,
                            'user_name'         => 'Ionut Marinescu',
                            'testing_plan_id'   => 37,
                            'testing_plan_name' => 'TestATF',
                            'stack_id'          => 16,
                            'stack_name'        => 'Atf-Test',
                            'team_id'           => 2,
                            'team_name'         => 'ATF',
                            'lastJob'           => $lastJob,
                            'testsCount'        => 0,
                        ],
                ],
                [
                    'id'   => 2,
                    'cell' =>
                        [
                            'id'                => 2,
                            'name'              => 'Test ATF Name1',
                            'schedule'          => 'H * * * *',
                            'createdAt'         =>
                                [
                                    'date'          => '2016-09-22 15:32:23.000000',
                                    'timezone_type' => 3,
                                    'timezone'      => 'Europe/Bucharest',
                                ],
                            'modifiedAt'        =>
                                [
                                    'date'          => '2016-09-22 15:32:23.000000',
                                    'timezone_type' => 3,
                                    'timezone'      => 'Europe/Bucharest',
                                ],
                            'user_id'           => 10,
                            'user_name'         => 'Victor Dumitru',
                            'testing_plan_id'   => 31,
                            'testing_plan_name' => 'Marvel',
                            'stack_id'          => 16,
                            'stack_name'        => 'Atf-Test',
                            'team_id'           => 2,
                            'team_name'         => 'ATF',
                            'lastJob'           => $lastJob,
                            'testsCount'        => 0,
                        ],
                ],
            ],
        ];

        $this->findAllOutput = [
            'total'   => 1,
            'page'    => '1',
            'records' => 2,
            'rows'    => [
                [
                    'id'   => 1,
                    'cell' =>
                        [
                            'id'          => 1,
                            'name'        => 'test schedule',
                            'schedule'    => '0 0 * * *',
                            'createdAt'   =>
                                [
                                    'date'          => '2016-09-15 10:13:57.000000',
                                    'timezone_type' => 3,
                                    'timezone'      => 'Europe/Bucharest',
                                ],
                            'modifiedAt'  =>
                                [
                                    'date'          => '2016-09-15 10:13:59.000000',
                                    'timezone_type' => 3,
                                    'timezone'      => 'Europe/Bucharest',
                                ],
                            'user'        =>
                                [
                                    'id'   => 94,
                                    'name' => 'Ionut Marinescu',
                                ],
                            'testingPlan' =>
                                [
                                    'id'   => 37,
                                    'name' => 'TestATF',
                                    'url'  => null
                                ],
                            'stack'       =>
                                [
                                    'id'   => 16,
                                    'name' => 'Atf-Test',
                                ],
                            'team'        =>
                                [
                                    'id'   => 2,
                                    'name' => 'ATF',
                                ],
                            'lastJob'     => $lastJob,
                            'testsCount'        => 0,
                        ],
                ],
                [
                    'id'   => 2,
                    'cell' =>
                        [
                            'id'          => 2,
                            'name'        => 'Test ATF Name1',
                            'schedule'    => 'H * * * *',
                            'createdAt'   =>
                                [
                                    'date'          => '2016-09-22 15:32:23.000000',
                                    'timezone_type' => 3,
                                    'timezone'      => 'Europe/Bucharest',
                                ],
                            'modifiedAt'  =>
                                [
                                    'date'          => '2016-09-22 15:32:23.000000',
                                    'timezone_type' => 3,
                                    'timezone'      => 'Europe/Bucharest',
                                ],
                            'user'        =>
                                [
                                    'id'   => 10,
                                    'name' => 'Victor Dumitru',
                                ],
                            'testingPlan' =>
                                [
                                    'id'   => 31,
                                    'name' => 'Marvel',
                                    'url'  => null
                                ],
                            'stack'       =>
                                [
                                    'id'   => 16,
                                    'name' => 'Atf-Test',
                                ],
                            'team'        =>
                                [
                                    'id'   => 2,
                                    'name' => 'ATF',
                                ],
                            'lastJob'     => $lastJob,
                            'testsCount'        => 0,
                        ],
                ],
            ],
        ];

        $this->viewActionOutput = [
            'id'          => 17,
            'name'        => 'Test ATF API 8',
            'schedule'    => '* */8 * * *',
            'browser'     => 'chrome',
            'user'        => [
                'id'   => 10,
                'name' => 'Victor Dumitru',
            ],
            'testingPlan' => [
                'id'   => 31,
                'name' => 'Marvel'
            ],
            'stack'       => [
                'id'   => 16,
                'name' => 'Atf-Test',
            ],
            'team'        => [
                'id'   => 2,
                'name' => 'ATF',
            ],
            'createdAt'   => [
                'date'          => '2016-09-28 10:55:11.000000',
                'timezone_type' => 3,
                'timezone'      => 'Europe/Bucharest',
            ],
            'modifiedAt'  => [
                'date'          => '2016-09-28 11:47:15.000000',
                'timezone_type' => 3,
                'timezone'      => 'Europe/Bucharest',
            ],
        ];

        $testingPlan = new TestingPlan();
        $testingPlan->setName('Marvel');
        $reflectionProperty = new \ReflectionProperty($testingPlan, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($testingPlan, 31);
        $this->jobSchedule = new JobSchedule();
        $this->jobSchedule->setName('Test ATF API 8');
        $this->jobSchedule->setSchedule('* */8 * * *');
        $this->jobSchedule->setBrowser('chrome');
        $this->jobSchedule->setTestingPlans($testingPlan);
        $reflectionProperty = new \ReflectionProperty($this->jobSchedule, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($this->jobSchedule, 17);
    }

    public function testFindAllPagedWithEmptyParamsWillUseDefaultForFilteringSortingAndPaging()
    {
        $this->repositoryMock->expects($this->once())
                             ->method('getListQueryBuilder')
                             ->with(array())
                             ->willReturn($this->queryBuilderMock);

        $this->jqGridServiceMock->expects($this->once())
                                ->method('getData')
                                ->with($this->jqGridInput)
                                ->willReturn($this->jqGridOutput);

        $i = 1;
        foreach ($this->findAllOutput['rows'] as $schedule) {
            $this->repositoryMock
                ->expects($this->at($i++))
                ->method('find')
                ->with($schedule['id'])
                ->willReturn($this->jobSchedule);
        }

        $allPaged = $this->jobScheduleService->findAllPaged($params = array());

        $this->assertEquals($this->findAllOutput, $allPaged);
    }

    /**
     * @param $sortColumn
     * @param $sortOrder
     * @param $maxRows
     * @param $page
     * @dataProvider provideParamsWithSortingAndPaging
     */
    public function testFindAllPagedWithSortingAndPagingParamsWillCallTheGridServiceWithTheCorrectSortingAndPagingValues(
        $sortColumn,
        $sortOrder,
        $maxRows,
        $page
    ) {
        $this->repositoryMock->expects($this->once())
                             ->method('getListQueryBuilder')
                             ->with(array())
                             ->willReturn($this->queryBuilderMock);

        $this->jqGridInput['sortBy'] = $sortColumn;
        $this->jqGridInput['sortOrder'] = $sortOrder;
        $this->jqGridInput['rows'] = $maxRows;
        $this->jqGridInput['currentPage'] = $page;

        $this->jqGridServiceMock->expects($this->once())
                                ->method('getData')
                                ->with($this->jqGridInput)
                                ->willReturn($this->jqGridOutput);

        $params = [
            'sidx' => $sortColumn,
            'sord' => $sortOrder,
            'rows' => $maxRows,
            'page' => $page,
        ];

        $i = 1;
        foreach ($this->findAllOutput['rows'] as $schedule) {
            $this->repositoryMock
                ->expects($this->at($i++))
                ->method('find')
                ->with($schedule['id'])
                ->willReturn($this->jobSchedule);
        }

        $allPaged = $this->jobScheduleService->findAllPaged($params);

        $this->assertEquals($this->findAllOutput, $allPaged);
    }

    public function provideParamsWithSortingAndPaging()
    {
        return [
            ['id', 'ASC', random_int(3, 7), random_int(1, 3)],
            ['name', 'DESC', random_int(3, 7), random_int(1, 3)],
        ];
    }

    public function testFindAllPagedWithFilterParamsWillCallTheGridServiceWithTheCorrectFilterValues()
    {
        $formName = 'some_form';
        $filter = [
            $formName => [
                'one'  => 'fish',
                'blue' => 'fish',
                'two'  => 'three',
            ],
        ];
        $params = [
            'formName' => $formName,
            'filter'   => http_build_query($filter),
        ];

        $this->repositoryMock->expects($this->once())
                             ->method('getListQueryBuilder')
                             ->with($filter[$formName])
                             ->willReturn($this->queryBuilderMock);

        $this->jqGridInput['filters'] = $filter[$formName];
        $this->jqGridServiceMock->expects($this->once())
                                ->method('getData')
                                ->with($this->jqGridInput)
                                ->willReturn($this->jqGridOutput);

        $i = 1;
        foreach ($this->findAllOutput['rows'] as $schedule) {
            $this->repositoryMock
                ->expects($this->at($i++))
                ->method('find')
                ->with($schedule['id'])
                ->willReturn($this->jobSchedule);
        }

        $allPaged = $this->jobScheduleService->findAllPaged($params);

        $this->assertEquals($this->findAllOutput, $allPaged);
    }

    public function testCreateJobScheduleWillReturnNewInstanceWhenNoIdParam()
    {
        $data = [
            'schedule' => '* */10 * * *',
            'browser'  => 'firefox',
            'name'     => 'Test Unit Schedule',
            'trigger'  => 'time',
            'notification_emails'  => 'atf.test@emag.ro',
            'executionLimit' => 10,
        ];

        $testingPlan = new TestingPlan();
        $user = new User();
        $stack = new Stack();
        $executionLimit = 10;

        $jobSchedule = $this->jobScheduleService->createJobSchedule($data, $testingPlan, $user, $stack, $executionLimit);

        $this->assertEquals($data['name'], $jobSchedule->getName());
        $this->assertEquals($data['browser'], $jobSchedule->getBrowser());
        $this->assertEquals($data['schedule'], $jobSchedule->getSchedule());
        $this->assertEquals($data['notification_emails'], $jobSchedule->getNotificationEmails());
        $this->assertEquals($data['executionLimit'], $jobSchedule->getExecutionLimit());
        $this->assertEquals($testingPlan, $jobSchedule->getTestingPlan());
        $this->assertEquals($user, $jobSchedule->getAuthor());
        $this->assertEquals($stack, $jobSchedule->getStack());
        $this->assertEquals(1, $jobSchedule->getStatus());
    }

    public function testCreateJobScheduleWillReturnNewInstanceWhenIdParam()
    {
        $data = [
            'id'       => '23',
            'schedule' => '* */10 * * *',
            'browser'  => 'firefox',
            'name'     => 'Test Unit Schedule',
            'trigger'  => 'time',
            'notification_emails'  => 'atf.test@emag.ro',
            'executionLimit' => 10
        ];

        $testingPlan = new TestingPlan();
        $user = new User();
        $stack = new Stack();
        $initialJobSchedule = new JobSchedule();
        $initialJobSchedule->setStatus(false);
        $initialJobSchedule->setAuthor($user);
        $executionLimit = 10;

        $this->repositoryMock->expects($this->once())
                             ->method('find')
                             ->with((int)$data['id'])
                             ->willReturn($initialJobSchedule);

        $jobSchedule = $this->jobScheduleService->createJobSchedule($data, $testingPlan, $user, $stack);

        $this->assertEquals($initialJobSchedule, $jobSchedule);
        $this->assertEquals($data['browser'], $jobSchedule->getBrowser());
        $this->assertEquals($data['schedule'], $jobSchedule->getSchedule());
        $this->assertEquals($data['notification_emails'], $jobSchedule->getNotificationEmails());
        $this->assertEquals($testingPlan, $jobSchedule->getTestingPlan());
        $this->assertEquals($user, $jobSchedule->getAuthor());
        $this->assertEquals($stack, $jobSchedule->getStack());
        $this->assertEquals(1, $jobSchedule->getStatus());
    }

    public function testSaveJobSchedule()
    {
        $jobSchedule = new JobSchedule();
        /** @var ObjectManager|Mock $managerMock */
        $managerMock = $this->jobScheduleService->getManager();
        $managerMock->expects($this->once())
            ->method('persist')
            ->with($jobSchedule);

        $managerMock->expects($this->once())
            ->method('flush');

        $this->jobScheduleService->saveJobSchedule($jobSchedule);
    }

    public function testEnableJobSchedule()
    {
        $jobSchedule = new JobSchedule();

        $reflectionProperty = new \ReflectionProperty($jobSchedule, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($jobSchedule, 10);

        /** @var ObjectManager|Mock $managerMock */
        $managerMock = $this->jobScheduleService->getManager();
        $managerMock->expects($this->once())
            ->method('persist')
            ->with($jobSchedule);

        $managerMock->expects($this->once())
            ->method('flush');

        $enableJenkinsJob = $this->jobScheduleService->enableJobSchedule($jobSchedule);

        $this->assertTrue($enableJenkinsJob);
    }

    public function testEnableJobScheduleWillReturnFalseWhenIdIsNotSet()
    {
        $jobSchedule = new JobSchedule();
        $enableJenkinsJob = $this->jobScheduleService->enableJobSchedule($jobSchedule);
        $this->assertFalse($enableJenkinsJob);
    }

    public function testDisableJobSchedule()
    {
        $jobSchedule = new JobSchedule();
        $user = new User();

        $reflectionProperty = new \ReflectionProperty($jobSchedule, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($jobSchedule, 10);

        /** @var ObjectManager|Mock $managerMock */
        $managerMock = $this->jobScheduleService->getManager();
        $managerMock->expects($this->once())
            ->method('persist')
            ->with($jobSchedule);

        $managerMock->expects($this->once())
            ->method('flush');

        $enableJenkinsJob = $this->jobScheduleService->disableJobSchedule($jobSchedule, $user);

        $this->assertTrue($enableJenkinsJob);
    }

    public function testDisableJobScheduleWillReturnFalseWhenIdIsNotSet()
    {
        $jobSchedule = new JobSchedule();
        $user = new User();
        $enableJenkinsJob = $this->jobScheduleService->disableJobSchedule($jobSchedule, $user);
        $this->assertFalse($enableJenkinsJob);
    }

    public function testDeleteJobSchedule()
    {
        $jobSchedule = new JobSchedule();
        /** @var ObjectManager|Mock $managerMock */
        $managerMock = $this->jobScheduleService->getManager();
        $managerMock->expects($this->once())
                    ->method('remove')
                    ->with($jobSchedule);

        $managerMock->expects($this->once())
                    ->method('flush');

        $this->jobScheduleService->deleteJobSchedule($jobSchedule);
    }

    public function testAddJenkinsJobWillReturnTrueWhenPostSuccess()
    {
        $jobSchedule = new JobSchedule();
        $testingPlan = new TestingPlan();
        $stack = new Stack();
        $user = new User();

        $jobSchedule->setTestingPlans($testingPlan);
        $jobSchedule->setStack($stack);
        $jobSchedule->setAuthor($user);
        $jobSchedule->setBrowser('chrome');
        $jobSchedule->setSchedule('* * * * *');
        $jobSchedule->setName('Some schedule');
        $jobSchedule->setExecutionLimit(10);

        $jobScheduleConfig = $this->makeJobScheduleConfig($jobSchedule);

        $this->httpClientMock->expects($this->at(0))
            ->method('post')
            ->with(
                static::JENKINS_BASE_URL . '/createItem',
                [
                    'auth' => [static::JENKINS_USERNAME, static::JENKINS_PASSWORD],
                    'headers' => [
                        'Accept' => 'application/xml',
                        'Content-Type' => 'text/xml; charset=UTF8',
                    ],
                    'body' => $jobScheduleConfig->asXML(),
                    'query' => ['name' => $jobSchedule->getName()],
                ]
            )
            ->willReturn(new Response(200));

        $this->httpClientMock->expects($this->at(1))
            ->method('post')
            ->with(
                static::JENKINS_BASE_URL . '/view/ATF%20Schedules/addJobToView',
                [
                    'auth' => [static::JENKINS_USERNAME, static::JENKINS_PASSWORD],
                    'query' => ['name' => $jobSchedule->getName()],
                ]
            )
            ->willReturn(new Response(200));

        $addJenkinsJob = $this->jobScheduleService->addJenkinsJob($jobSchedule);

        $this->assertTrue($addJenkinsJob);
    }

    public function testAddJenkinsJobWillReturnFalseWhenPostThrowsClientException()
    {
        $jobSchedule = new JobSchedule();
        $testingPlan = new TestingPlan();
        $stack = new Stack();
        $user = new User();

        $jobSchedule->setTestingPlans($testingPlan);
        $jobSchedule->setStack($stack);
        $jobSchedule->setAuthor($user);
        $jobSchedule->setBrowser('firefox');
        $jobSchedule->setSchedule('* * * * *');
        $jobSchedule->setName('Some schedule');
        $jobSchedule->setExecutionLimit(PHP_INT_MAX);

        $jobScheduleConfig = $this->makeJobScheduleConfig($jobSchedule);

        /** @var ClientException|Mock $clientException */
        $clientException = $this->getMockBuilder(ClientException::class)->disableOriginalConstructor()->getMock();
        $this->httpClientMock->expects($this->once())
                             ->method('post')
                             ->with(
                                 static::JENKINS_BASE_URL . '/createItem',
                                 [
                                     'auth'    => [static::JENKINS_USERNAME, static::JENKINS_PASSWORD],
                                     'headers' => [
                                         'Accept'       => 'application/xml',
                                         'Content-Type' => 'text/xml; charset=UTF8',
                                     ],
                                     'body'    => $jobScheduleConfig->asXML(),
                                     'query'   => ['name' => $jobSchedule->getName()],
                                 ]
                             )
                             ->willThrowException($clientException);

        $addJenkinsJob = $this->jobScheduleService->addJenkinsJob($jobSchedule);

        $this->assertFalse($addJenkinsJob);
    }

    public function testAddJenkinsJobWillReturnFalseWhenPostThrowsClientExceptionWhenAssignJobToView()
    {
        $jobSchedule = new JobSchedule();
        $testingPlan = new TestingPlan();
        $stack = new Stack();
        $user = new User();

        $jobSchedule->setTestingPlans($testingPlan);
        $jobSchedule->setStack($stack);
        $jobSchedule->setAuthor($user);
        $jobSchedule->setBrowser('firefox');
        $jobSchedule->setSchedule('* * * * *');
        $jobSchedule->setName('Some schedule');
        $jobSchedule->setExecutionLimit(5);

        $jobScheduleConfig = $this->makeJobScheduleConfig($jobSchedule);

        /** @var ClientException|Mock $clientException */
        $clientException = $this->getMockBuilder(ClientException::class)->disableOriginalConstructor()->getMock();
        $this->httpClientMock->expects($this->at(0))
            ->method('post')
            ->with(
                static::JENKINS_BASE_URL . '/createItem',
                [
                    'auth' => [static::JENKINS_USERNAME, static::JENKINS_PASSWORD],
                    'headers' => [
                        'Accept' => 'application/xml',
                        'Content-Type' => 'text/xml; charset=UTF8',
                    ],
                    'body' => $jobScheduleConfig->asXML(),
                    'query' => ['name' => $jobSchedule->getName()],
                ]
            )
            ->willReturn(new Response(200));

        $this->httpClientMock->expects($this->at(1))
            ->method('post')
            ->with(
                static::JENKINS_BASE_URL . '/view/ATF%20Schedules/addJobToView',
                [
                    'auth' => [static::JENKINS_USERNAME, static::JENKINS_PASSWORD],
                    'query' => ['name' => $jobSchedule->getName()],
                ]
            )
            ->willThrowException($clientException);

        $addJenkinsJob = $this->jobScheduleService->addJenkinsJob($jobSchedule);

        $this->assertFalse($addJenkinsJob);
    }

    /**
     * @dataProvider provideUpdateAndEnableResponses
     * @param ResponseInterface $updateResponse
     * @param ResponseInterface $enableResponse
     * @param bool $expectedResult
     */
    public function testEditJenkinsJobWillReturnTrueOrFalseWhenPostSucceedsBasedOnStatusCodes(
        $updateResponse,
        $enableResponse,
        $expectedResult
    ) {
        $jobSchedule = new JobSchedule();
        $testingPlan = new TestingPlan();
        $stack = new Stack();
        $user = new User();

        $jobSchedule->setTestingPlans($testingPlan);
        $jobSchedule->setStack($stack);
        $jobSchedule->setAuthor($user);
        $jobSchedule->setBrowser('chrome');
        $jobSchedule->setSchedule('* * * * *');
        $jobSchedule->setName('Some schedule');
        $jobSchedule->setExecutionLimit(6);

        $jobScheduleConfig = $this->makeJobScheduleConfig($jobSchedule);

        $this->httpClientMock->expects($this->at(0))
                             ->method('post')
                             ->with(
                                 static::JENKINS_BASE_URL . '/view/ATF%20Schedules/job/Some%20schedule/config.xml',
                                 [
                                     'auth'    => [static::JENKINS_USERNAME, static::JENKINS_PASSWORD],
                                     'headers' => [
                                         'Accept'       => 'application/xml',
                                         'Content-Type' => 'text/xml; charset=UTF8',
                                     ],
                                     'body'    => $jobScheduleConfig->asXML(),
                                 ]
                             )
                             ->willReturn($updateResponse);

        if (!is_null($enableResponse)) {
            $this->httpClientMock->expects($this->at(1))
                                 ->method('post')
                                 ->with(static::JENKINS_BASE_URL . '/view/ATF%20Schedules/job/Some%20schedule/enable')
                                 ->willReturn($enableResponse);
        }

        $result = $this->jobScheduleService->editJenkinsJob($jobSchedule);

        $this->assertEquals($expectedResult, $result);
    }

    public function provideUpdateAndEnableResponses()
    {
        return [
            [new Response(200), new Response(200), true],
            [new Response(200), new Response(500), false],
            [new Response(500), null, false],
        ];
    }

    public function testEditJenkinsJobWillReturnFalseWhenPostThrowsClientExceptionOnEdit()
    {
        $jobSchedule = new JobSchedule();
        $testingPlan = new TestingPlan();
        $stack = new Stack();
        $user = new User();

        $jobSchedule->setTestingPlans($testingPlan);
        $jobSchedule->setStack($stack);
        $jobSchedule->setAuthor($user);
        $jobSchedule->setBrowser('firefox');
        $jobSchedule->setSchedule('* * * * *');
        $jobSchedule->setName('Some schedule');
        $jobSchedule->setExecutionLimit(6);

        $jobScheduleConfig = $this->makeJobScheduleConfig($jobSchedule);

        $this->httpClientMock->expects($this->at(0))
                             ->method('post')
                             ->with(
                                 static::JENKINS_BASE_URL . '/view/ATF%20Schedules/job/Some%20schedule/config.xml',
                                 [
                                     'auth'    => [static::JENKINS_USERNAME, static::JENKINS_PASSWORD],
                                     'headers' => [
                                         'Accept'       => 'application/xml',
                                         'Content-Type' => 'text/xml; charset=UTF8',
                                     ],
                                     'body'    => $jobScheduleConfig->asXML(),
                                 ]
                             )
                             ->willReturn(new Response(200));

        /** @var ClientException|Mock $clientException */
        $clientException = $this->getMockBuilder(ClientException::class)->disableOriginalConstructor()->getMock();
        $this->httpClientMock->expects($this->at(1))
                             ->method('post')
                             ->with(static::JENKINS_BASE_URL . '/view/ATF%20Schedules/job/Some%20schedule/enable')
                             ->willThrowException($clientException);

        $addJenkinsJob = $this->jobScheduleService->editJenkinsJob($jobSchedule);

        $this->assertFalse($addJenkinsJob);
    }

    public function testEditJenkinsJobWillReturnFalseWhenPostThrowsClientExceptionOnEnable()
    {
        $jobSchedule = new JobSchedule();
        $testingPlan = new TestingPlan();
        $stack = new Stack();
        $user = new User();

        $jobSchedule->setTestingPlans($testingPlan);
        $jobSchedule->setStack($stack);
        $jobSchedule->setAuthor($user);
        $jobSchedule->setBrowser('firefox');
        $jobSchedule->setSchedule('* * * * *');
        $jobSchedule->setName('Some schedule');
        $jobSchedule->setExecutionLimit(3);

        $jobScheduleConfig = $this->makeJobScheduleConfig($jobSchedule);

        /** @var ClientException|Mock $clientException */
        $clientException = $this->getMockBuilder(ClientException::class)->disableOriginalConstructor()->getMock();
        $this->httpClientMock->expects($this->once())
                             ->method('post')
                             ->with(
                                 static::JENKINS_BASE_URL . '/view/ATF%20Schedules/job/Some%20schedule/config.xml',
                                 [
                                     'auth'    => [static::JENKINS_USERNAME, static::JENKINS_PASSWORD],
                                     'headers' => [
                                         'Accept'       => 'application/xml',
                                         'Content-Type' => 'text/xml; charset=UTF8',
                                     ],
                                     'body'    => $jobScheduleConfig->asXML(),
                                 ]
                             )
                             ->willThrowException($clientException);

        $addJenkinsJob = $this->jobScheduleService->editJenkinsJob($jobSchedule);

        $this->assertFalse($addJenkinsJob);
    }

    public function testDisableJobWillCallTheCorrectUrlAndReturnTheResponseBasedOnlyOnTheJobScheduleName()
    {
        $jobSchedule = new JobSchedule();
        $jobSchedule->setName('Some schedule');

        $expectedDisableResponse = new Response(200);
        $this->httpClientMock->expects($this->once())
            ->method('post')
            ->with(static::JENKINS_BASE_URL . '/view/ATF%20Schedules/job/Some%20schedule/disable')
            ->willReturn($expectedDisableResponse);

        $actualDisableResponse = $this->jobScheduleService->disableJenkinsJob($jobSchedule);

        $this->assertEquals($expectedDisableResponse, $actualDisableResponse);
    }

    public function testDeleteJobWillCallTheCorrectUrlAndReturnTheResponseBasedOnlyOnTheJobScheduleName()
    {
        $jobSchedule = new JobSchedule();
        $jobSchedule->setName('Some schedule');

        $expectedDeleteResponse = new Response(200);
        $this->httpClientMock->expects($this->once())
            ->method('post')
            ->with(static::JENKINS_BASE_URL . '/view/ATF%20Schedules/job/Some%20schedule/doDelete')
            ->willReturn($expectedDeleteResponse);

        $actualDeleteResponse = $this->jobScheduleService->deleteJenkinsJob($jobSchedule);

        $this->assertEquals($expectedDeleteResponse, $actualDeleteResponse);
    }

    public function testCheckJenkinsJobExistsIfExists()
    {
        $jobSchedule = new JobSchedule();
        $jobSchedule->setName('Some schedule');

        $expectedDisableResponse = new Response(200);
        $this->httpClientMock->expects($this->once())
                             ->method('get')
                             ->with(static::JENKINS_BASE_URL . '/view/ATF%20Schedules/job/Some%20schedule/config.xml')
                             ->willReturn($expectedDisableResponse);

        $actualDisableResponse = $this->jobScheduleService->checkJenkinsJobExists($jobSchedule);

        $this->assertTrue($actualDisableResponse);
    }

    public function testCheckJenkinsJobExistsIfNotExists()
    {
        $jobSchedule = new JobSchedule();
        $jobSchedule->setName('Some schedule');

        $expectedDisableResponse = new Response(404);
        $this->httpClientMock->expects($this->once())
                             ->method('get')
                             ->with(static::JENKINS_BASE_URL . '/view/ATF%20Schedules/job/Some%20schedule/config.xml')
                             ->willReturn($expectedDisableResponse);

        $actualDisableResponse = $this->jobScheduleService->checkJenkinsJobExists($jobSchedule);

        $this->assertTrue($actualDisableResponse);
    }

    public function testViewScheduleByIdSuccess()
    {
        $stack = new Stack();
        $stack->setName('Atf-Test');

        $reflectionProperty = new \ReflectionProperty($stack, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($stack, 16);

        $testingPlan = new TestingPlan();
        $testingPlan->setName('Marvel');

        $reflectionProperty = new \ReflectionProperty($testingPlan, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($testingPlan, 31);

        $team = new Team();
        $team->setName('ATF');

        $reflectionProperty = new \ReflectionProperty($team, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($team, 2);

        $user = new User();
        $user->setTeam($team);
        $user->setName('Victor Dumitru');

        $reflectionProperty = new \ReflectionProperty($user, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($user, 10);

        $createdAt = [
            'date'          => '2016-09-28 10:55:11.000000',
            'timezone_type' => 3,
            'timezone'      => 'Europe/Bucharest',
        ];

        $modifiedAt = [
            'date'          => '2016-09-28 11:47:15.000000',
            'timezone_type' => 3,
            'timezone'      => 'Europe/Bucharest',
        ];

        $jobSchedule = new JobSchedule();
        $jobSchedule->setName('Test ATF API 8');
        $jobSchedule->setSchedule('* */8 * * *');
        $jobSchedule->setBrowser('chrome');
        $jobSchedule->setTestingPlans($testingPlan);
        $jobSchedule->setStack($stack);
        $jobSchedule->setAuthor($user);

        $reflectionProperty = new \ReflectionProperty($jobSchedule, 'createdAt');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($jobSchedule, $createdAt);

        $reflectionProperty = new \ReflectionProperty($jobSchedule, 'modifiedAt');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($jobSchedule, $modifiedAt);

        $reflectionProperty = new \ReflectionProperty($jobSchedule, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($jobSchedule, 17);

        $this->repositoryMock->expects($this->once())
                             ->method('find')
                             ->with(17)
                             ->willReturn($jobSchedule);

        $viewScheduleById = $this->jobScheduleService->getJobScheduleById($jobSchedule->getId());

        $this->assertEquals($this->viewActionOutput, $viewScheduleById);
    }

    /**
     * @param JobSchedule $jobSchedule
     * @return \SimpleXMLElement
     */
    private function makeJobScheduleConfig(JobSchedule $jobSchedule)
    {
        $reflectionProperty = new \ReflectionProperty($this->jobScheduleService, 'configTemplate');
        $reflectionProperty->setAccessible(true);

        $config = simplexml_load_string($reflectionProperty->getValue($this->jobScheduleService));
        $config->triggers->{'hudson.triggers.TimerTrigger'}->spec = $jobSchedule->getSchedule();
        $config->scm->userRemoteConfigs->{'hudson.plugins.git.UserRemoteConfig'}->url = static::ATF_RAW_REPO;
        $config->scm->userRemoteConfigs->{'hudson.plugins.git.UserRemoteConfig'}->credentialsId = static::JENKINS_CREDENTIALS_ID;
        $config->assignedNode = static::ATF_RAW_RUN_SERVER;
        $config->scm->branches->{'hudson.plugins.git.BranchSpec'}->name = '*/master';
        $config->displayName = 'Schedule / ' . $jobSchedule->getName();
        $config->builders->{'hudson.tasks.Shell'}->command = sprintf(
            "php composer.phar install --no-dev\nphp bin/raw.php -t %d -s %d -b %s -j %d -h %s -l %d",
            $jobSchedule->getTestingPlan()->getId(),
            $jobSchedule->getStack()->getId(),
            $jobSchedule->getBrowser(),
            $jobSchedule->getId(),
            static::ATF_BASE_URL,
            $jobSchedule->getExecutionLimit()
        );

        return $config;
    }
}
