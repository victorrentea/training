<?php
namespace Emag\Core\JiraApiBundle\Tests\Unit\Service;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Persistence\ObjectManager;
use Doctrine\ORM\EntityManager;
use Emag\Core\CodeceptionBundle\Entity\Distribution;
use Emag\Core\CodeceptionBundle\Entity\Project;
use Emag\Core\CodeceptionBundle\Entity\Stack;
use Emag\Core\CodeceptionBundle\Entity\Test;
use Emag\Core\CodeceptionBundle\Entity\TestingPlan;
use Emag\Core\JiraApiBundle\Entity\SupportTask;
use Emag\Core\JiraApiBundle\Repository\SupportTaskRepository;
use Emag\Core\JiraApiBundle\Service\IssueService;
use Emag\Core\JobBundle\Entity\Job;
use Guzzle\Http\Client;
use Guzzle\Http\Message\EntityEnclosingRequest;
use Guzzle\Http\Message\Response;
use PHPUnit_Framework_MockObject_MockObject as Mock;
use PHPUnit_Framework_MockObject_MockObject;
use Symfony\Component\Routing\Router;
use Symfony\Component\Validator\Constraints\DateTime;

/**
 * @group unit-tests
 */
class IssueServiceTest extends \PHPUnit_Framework_TestCase
{
    public const JIRA_API_URL = 'https://jira.emag.network/rest/api/2/';
    public const JIRA_USERNAME = 'atf.test';
    public const JIRA_PASSWORD = 'TestingUserATF2015';

    /**
     * @var Client|Mock
     */
    private $httpClientMock;

    /**
     * @var IssueService
     */
    private $issueService;

    /**
     * @var SupportTaskRepository|Mock
     */
    private $repositoryMock;

    /**
     * @var IssueService|Mock
     */
    private $issueServiceMock;

    /**
     * @var EntityManager|PHPUnit_Framework_MockObject_MockObject
     */
    private $entityManagerMock;

    protected function setUp()
    {
        $this->httpClientMock = $this->getMockBuilder(Client::class)->disableOriginalConstructor()
            ->setMethods(['post', 'get', 'delete'])->getMock();

        $this->issueService = new IssueService($this->httpClientMock);

        $this->entityManagerMock = $this->getMockBuilder(EntityManager::class)
            ->disableOriginalConstructor()
            ->setMethods(array('persist', 'flush', 'getRepository'))
            ->getMock();

        $this->repositoryMock = $this->getMockBuilder(SupportTaskRepository::class)
            ->disableOriginalConstructor()
            ->getMock();

        $routerMock = $this->getMockBuilder(Router::class)->disableOriginalConstructor()->getMock();
        $this->issueService->setRouter($routerMock);
        $this->issueService->setEntityManager($this->entityManagerMock);

        $this->issueServiceMock = $this->getMockBuilder(IssueService::class)
            ->disableOriginalConstructor()
            ->setMethods(['getTicketStatusByJiraKey', 'createJiraSupportTicket', 'addCommentToJiraTicket'])
            ->getMock();

        $this->issueServiceMock->setRouter($routerMock);
        $this->issueServiceMock->setEntityManager($this->entityManagerMock);

    }

    public function testIssueServiceGet()
    {
        /** @var EntityEnclosingRequest|\PHPUnit_Framework_MockObject_MockObject $request */
        $request = $this->getMockBuilder(EntityEnclosingRequest::class)->disableOriginalConstructor()->setMethods(['send'])->getMock();

        $this->httpClientMock
            ->expects($this->once())
            ->method('get')
            ->willReturn($request);

        $request->expects($this->once())
            ->method('send')
            ->willReturn(new Response(200, [], json_encode(['key' => 'AA-999', 'fields' => ['issuetype' => ['name' => 'Bug']]])));

        $result = $this->issueService->get('AA-999');

        $this->assertEquals('AA-999', $result['key']);
        $this->assertEquals('Bug', $result['fields']['issuetype']['name']);
    }

    public function testIssueServiceGetNoData()
    {
        /** @var EntityEnclosingRequest|\PHPUnit_Framework_MockObject_MockObject $request */
        $request = $this->getMockBuilder(EntityEnclosingRequest::class)->disableOriginalConstructor()->setMethods(['send'])->getMock();

        $this->httpClientMock
            ->expects($this->once())
            ->method('get')
            ->willReturn($request);

        $request->expects($this->once())
            ->method('send')
            ->willReturn(new Response(200, [], json_encode(array())));

        $result = $this->issueService->get('PROJECT', 'repository', 'branch');

        $this->assertEmpty($result);
    }

    public function testIssueServiceGetErrors()
    {
        /** @var EntityEnclosingRequest|\PHPUnit_Framework_MockObject_MockObject $request */
        $request = $this->getMockBuilder(EntityEnclosingRequest::class)->disableOriginalConstructor()->setMethods(['send'])->getMock();

        $this->httpClientMock
            ->expects($this->once())
            ->method('get')
            ->willReturn($request);

        $request->expects($this->once())
            ->method('send')
            ->willReturn(new Response(200, [], json_encode(['errors' => true])));

        $result = $this->issueService->get('PROJECT', 'repository', 'branch');

        $this->assertEquals(false, $result);
    }

    public function testCreateSupportIssueReturnKey()
    {
        $data =
            [
                'project' => ['id' => 12345],
                'issuetype' => ['id' => 54321],
                'summary' => 'Task test summary',
                'description' => 'Task test description',
                'assignee' => ['name' => '-1'],
                'jira_project_id' => '4161'
            ];

        /** @var EntityEnclosingRequest|\PHPUnit_Framework_MockObject_MockObject $request */
        $request = $this->getMockBuilder(EntityEnclosingRequest::class)->disableOriginalConstructor()->setMethods(['send', 'setBody'])->getMock();

        $this->httpClientMock
            ->expects($this->exactly(3))
            ->method('post')
            ->willReturn($request);

        $request->expects($this->exactly(3))
            ->method('send')
            ->willReturn(new Response(200, [], json_encode(['key' => 'TEST-11'])));

        $result = $this->issueService->createJiraSupportTicket($data);
        $this->assertEquals('TEST-11', $result);
    }

    public function testGetTicketStatusByJiraKey()
    {
        /** @var EntityEnclosingRequest|\PHPUnit_Framework_MockObject_MockObject $request */
        $request = $this->getMockBuilder(EntityEnclosingRequest::class)->disableOriginalConstructor()->setMethods(['send'])->getMock();

        $this->httpClientMock
            ->expects($this->once())
            ->method('get')
            ->willReturn($request);

        $request->expects($this->once())
            ->method('send')
            ->willReturn(new Response(200, [], json_encode(['fields' => ['status' => ['id' => 3]]])));

        $result = $this->issueService->getTicketStatusByJiraKey('TESTT-11');
        $this->assertEquals(3, $result);
    }

    public function testAddCommentToJiraTicket()
    {
        $data['body'] = 'Job failed: ATF Login Test (http://atf.emag.local/ro-en/job/job/view/41238)';

        /** @var EntityEnclosingRequest|\PHPUnit_Framework_MockObject_MockObject $request */
        $request = $this->getMockBuilder(EntityEnclosingRequest::class)->disableOriginalConstructor()->setMethods(['send', 'setBody'])->getMock();

        $this->httpClientMock
            ->expects($this->once())
            ->method('post')
            ->willReturn($request);

        $request->expects($this->once())
            ->method('send')
            ->willReturn(new Response(200, [], json_encode(['key' => 'TEST-11'])));

        $result = $this->issueService->addCommentToJiraTicket('TESTT-11', $data);
        $this->assertTrue($result);
    }

    public function testProcessJiraTicketWithJiraStatusClosed()
    {
        $testingPlan = new TestingPlan();
        $testingPlan->setName('Test Plan Name');
        $stack = new Stack();
        $stack->setCoreCode('core-test');
        $project = new Project();
        $project->setJiraId(4161);

        $job = new Job();
        $job->setTestingPlan($testingPlan);
        $job->setStack($stack);
        $job->setStartDate(new \DateTime('now'));

        $distribution = new Distribution();
        $distribution->setName('atf');
        $distribution->setProject($project);

        $supportTask = new SupportTask();
        $supportTask->setJiraStatus(5);

        $this->entityManagerMock->expects($this->once())
            ->method('getRepository')
            ->willReturn($this->repositoryMock);

        $this->repositoryMock->expects($this->once())
            ->method('findOneBy')
            ->willReturn($supportTask);

        $this->issueServiceMock->expects($this->once())
            ->method('getTicketStatusByJiraKey')
            ->willReturn(5);

        $supportTask->setJiraStatus(5);

        $this->issueServiceMock->expects($this->once())
            ->method('createJiraSupportTicket')
            ->willReturn('TESTT-30');

        $newSupportTask = new SupportTask();
        $newSupportTask->setJiraStatus(3);
        $newSupportTask->setDistribution($distribution);
        $newSupportTask->setTestingPlan($job->getTestingPlan());
        $newSupportTask->setJiraKey('TESTT-30');

        $this->entityManagerMock->expects($this->once())
            ->method('persist')
            ->with($newSupportTask);

        $this->entityManagerMock->expects($this->once())
            ->method('flush');

        $this->issueServiceMock->processJiraTicket($job, $distribution);
    }

    public function testProcessJiraTicketWithJiraStatusOpen()
    {
        $testingPlan = new TestingPlan();
        $testingPlan->setName('Test Plan Name');
        $stack = new Stack();
        $stack->setCoreCode('core-test');
        $project = new Project();
        $project->setJiraId(4161);

        $job = new Job();
        $job->setTestingPlan($testingPlan);
        $job->setStack($stack);
        $job->setStartDate(new \DateTime('now'));

        $distribution = new Distribution();
        $distribution->setName('atf');
        $distribution->setProject($project);

        $supportTask = new SupportTask();
        $supportTask->setJiraStatus(3);

        $this->entityManagerMock->expects($this->once())
            ->method('getRepository')
            ->willReturn($this->repositoryMock);

        $this->repositoryMock->expects($this->once())
            ->method('findOneBy')
            ->willReturn($supportTask);

        $this->issueServiceMock->expects($this->once())
            ->method('getTicketStatusByJiraKey')
            ->willReturn(1);

        $this->issueServiceMock->expects($this->once())
            ->method('addCommentToJiraTicket')
            ->willReturn(true);

        $this->issueServiceMock->processJiraTicket($job, $distribution);
    }

    public function testProcessJiraTicketWithNewSupportTask()
    {
        $testingPlan = new TestingPlan();
        $testingPlan->setName('Test Plan Name');
        $stack = new Stack();
        $stack->setCoreCode('core-test');
        $project = new Project();
        $project->setJiraId(4161);

        $job = new Job();
        $job->setTestingPlan($testingPlan);
        $job->setStack($stack);
        $job->setStartDate(new \DateTime('now'));

        $distribution = new Distribution();
        $distribution->setName('atf');
        $distribution->setProject($project);

        $supportTask = new SupportTask();
        $supportTask->setJiraStatus(3);

        $this->entityManagerMock->expects($this->once())
            ->method('getRepository')
            ->willReturn($this->repositoryMock);

        $this->repositoryMock->expects($this->once())
            ->method('findOneBy')
            ->willReturn(null);

        $this->issueServiceMock->expects($this->once())
            ->method('createJiraSupportTicket')
            ->willReturn('TESTT-30');

        $newSupportTask = new SupportTask();
        $newSupportTask->setJiraStatus(3);
        $newSupportTask->setDistribution($distribution);
        $newSupportTask->setTestingPlan($job->getTestingPlan());
        $newSupportTask->setJiraKey('TESTT-30');

        $this->entityManagerMock->expects($this->once())
            ->method('persist')
            ->with($newSupportTask);

        $this->entityManagerMock->expects($this->once())
            ->method('flush');

        $this->issueServiceMock->processJiraTicket($job, $distribution);
    }

    public function testSyncLinkedTestsWithEmptyJiraKey()
    {
        $testingPlan = new TestingPlan();
        $testingPlan->setJiraKey(null);

        $result = $this->issueService->syncLinkedTests($testingPlan, array());

        $this->assertFalse($result);

    }

    public function testSyncLinkedTestsWithJiraKeyAndTestingTask()
    {
        $testingPlan = new TestingPlan();
        $testingPlan->setJiraKey('EMGCTF-5000');

        /** @var EntityEnclosingRequest|\PHPUnit_Framework_MockObject_MockObject $request */
        $request = $this->getMockBuilder(EntityEnclosingRequest::class)->disableOriginalConstructor()->setMethods(['send'])->getMock();

        $this->httpClientMock
            ->expects($this->once())
            ->method('get')
            ->with('issue/' . $testingPlan->getJiraKey() . '?')
            ->willReturn($request);

        $request->expects($this->once())
            ->method('send')
            ->willReturn(new Response(200, [], json_encode(['fields' => ['issuetype' => ['name' => 'Technical Task']]])));

        $result = $this->issueService->syncLinkedTests($testingPlan, array());
        $this->assertFalse($result);
    }

    public function testSyncLinkedTestsWithJiraKeyAnd()
    {
        $testingPlan = new TestingPlan();
        $testingPlan->setJiraKey('EMGCTF-5000');

        $addedIssues = array();
        $removedIssues = array();

        /** @var EntityEnclosingRequest|\PHPUnit_Framework_MockObject_MockObject $request */
        $request = $this->getMockBuilder(EntityEnclosingRequest::class)->disableOriginalConstructor()->setMethods(['send'])->getMock();

        $this->httpClientMock
            ->expects($this->once())
            ->method('get')
            ->with('issue/' . $testingPlan->getJiraKey() . '?')
            ->willReturn($request);

        $jiraIssue = [
            'fields' =>
                ['issuetype' => ['name' => 'Testing task'],
                'project'   => ['id' => 1234]],
            'key' => 'EMGCTF-121234'
            ];

        $request->expects($this->once())
            ->method('send')
            ->willReturn(new Response(200, [], json_encode($jiraIssue)));

        $testCycles = new ArrayCollection();

        $test1 = new Test();
        $test1->setName('Test1');

        $test2 = new Test();
        $test2->setName('Test2');

        $test3 = new Test();
        $test3->setName('Test3');

        $testCycles->add($test1);
        $testCycles->add($test2);
        $testCycles->add($test3);

        foreach ($testCycles as $test) {

            $data = [
                'fields' => [
                    'project' => ['id' => $jiraIssue['fields']['project']['id']],
                    'issuetype' => ['id' => 19],
                    'summary' => $test->getName(),
                    'description' => $test->getName()
                ],
                'key' => 'EMGCTF-12' . $jiraIssue['fields']['project']['id']
            ];

            /** @var EntityEnclosingRequest|\PHPUnit_Framework_MockObject_MockObject $request */
            $request = $this->getMockBuilder(EntityEnclosingRequest::class)->disableOriginalConstructor()->setMethods(['send', 'setBody'])->getMock();

            $this->httpClientMock
                ->expects($this->any())
                ->method('post')
                ->willReturn($request);

            $request->expects($this->any())
                ->method('send')
                ->willReturn(new Response(200, [], json_encode($data)));

            $addedIssues[] = $data['key'];

            $data = [
                'type' => ['name' => 'Gantt Dependency'],
                'inwardIssue' => ['key' => $jiraIssue['key']],
                'outwardIssue' => ['key' => $jiraIssue['key']]
            ];

            /** @var EntityEnclosingRequest|\PHPUnit_Framework_MockObject_MockObject $request */
            $request = $this->getMockBuilder(EntityEnclosingRequest::class)->disableOriginalConstructor()->setMethods(['send', 'setBody'])->getMock();

            $this->httpClientMock
                ->expects($this->any())
                ->method('post')
                ->willReturn($request);

            $request->expects($this->any())
                ->method('send')
                ->willReturn(new Response(200, [], json_encode($data)));

        }

        $removedTestIssueKeys = ['EMGCTF-1234', 'EMGCTF-1233', 'EMGCTF-1232'];

        foreach ($removedTestIssueKeys as $testIssueKey) {

            /** @var EntityEnclosingRequest|\PHPUnit_Framework_MockObject_MockObject $request */
            $request = $this->getMockBuilder(EntityEnclosingRequest::class)->disableOriginalConstructor()->setMethods(['send'])->getMock();

            $this->httpClientMock
                ->expects($this->any())
                ->method('delete')
                ->willReturn($request);

            $request->expects($this->any())
                ->method('send')
                ->willReturn(new Response(204, [], '{"done": 1}'));

            $removedIssues[] = $testIssueKey;
        }


        $result = $this->issueService->syncLinkedTests($testingPlan, $testCycles, $removedTestIssueKeys);
        $this->assertEquals([$addedIssues, $removedIssues], $result);
    }
}
