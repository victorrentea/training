<?php

namespace Emag\Core\CodeceptionBundle\Tests\Unit\Service;

use Doctrine\Common\Persistence\ObjectManager;
use Doctrine\ORM\EntityManager;
use Emag\Core\BaseBundle\Exception\AtfException;
use Emag\Core\CodeceptionBundle\Entity\Stack;
use Emag\Core\CodeceptionBundle\Repository\StackRepository;
use Emag\Core\CodeceptionBundle\Service\CodeRepositoryService;
use Emag\Core\CodeceptionBundle\Service\GeneratorService;
use GuzzleHttp\Client;
use GuzzleHttp\Exception\ClientException;
use GuzzleHttp\Psr7\Request;
use GuzzleHttp\Psr7\Response;

class CodeRepositoryServiceTest extends \PHPUnit_Framework_TestCase
{
    /** @var CodeRepositoryService */
    private $codeRepositoryService;

    /** @var ObjectManager|\PHPUnit_Framework_MockObject_MockObject $objectManager */
    private $objectManagerMock;

    /** @var GeneratorService|\PHPUnit_Framework_MockObject_MockObject $generatorServiceMock */
    private $generatorServiceMock;

    /** @var Client|\PHPUnit_Framework_MockObject_MockObject $generatorServiceMock */
    private $guzzleClientMock;

    public function setUp()
    {
        parent::setUp();

        /** @var ObjectManager|\PHPUnit_Framework_MockObject_MockObject $objectManager */
        $this->objectManagerMock = $this->getMockBuilder(EntityManager::class)
            ->disableOriginalConstructor()
            ->getMock();

        /** @var GeneratorService|\PHPUnit_Framework_MockObject_MockObject $generatorServiceMock */
        $this->generatorServiceMock = $this->getMockBuilder(GeneratorService::class)
            ->disableOriginalConstructor()
            ->getMock();

        /** @var Client|\PHPUnit_Framework_MockObject_MockObject guzzleClientMock */
        $this->guzzleClientMock = $this->getMockBuilder(Client::class)->setMethods(['get'])->disableArgumentCloning()->getMock();

        $this->codeRepositoryService = new CodeRepositoryService('stash_api_url', 'stash_username', 'stash_password');
        $this->codeRepositoryService->setManager($this->objectManagerMock);
        $this->codeRepositoryService->setGeneratorService($this->generatorServiceMock);
        $this->codeRepositoryService->setGuzzleClient($this->guzzleClientMock);
    }

    public function testGetAtfHelper()
    {
        /** @var StackRepository|\PHPUnit_Framework_MockObject_MockObject $stackRepoMock */
        $stackRepoMock = $this->getMockBuilder(StackRepository::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->objectManagerMock->expects($this->once())
            ->method('getRepository')
            ->with(Stack::class)
            ->willReturn($stackRepoMock);

        $stackRepoMock->expects($this->once())
            ->method('findBy')
            ->with([
                'status' => 1,
            ])
            ->willReturn('a');

        $this->generatorServiceMock->expects($this->once())
            ->method('generateHelperClass')
            ->with('a')
            ->willReturn('b');

        $atfHelper = $this->codeRepositoryService->getAtfHelper();
        $this->assertEquals('b', $atfHelper);
    }

    public function testGetApiHeadCommitHash()
    {
        $this->guzzleClientMock
            ->expects($this->once())
            ->method('get')
            ->willReturn(
                new Response(200,[], '{"id":"cc11e4ead1974ab729a16805c4a4fd049db43ebc","displayId":"cc11e4ead19","author":{"name":"alexandru.badaluta","emailAddress":"alexandru.badaluta@emag.ro","id":3399,"displayName":"Alexandru Badaluta | eMAG, Technology","active":true,"slug":"alexandru.badaluta","type":"NORMAL","link":{"url":"/users/alexandru.badaluta","rel":"self"},"links":{"self":[{"href":"http://stash.emag.network/users/alexandru.badaluta"}]}},"authorTimestamp":1491898012000,"message":"Merge pull request #2 in EMGCTF/atf-codecept_v2.2 from EMGCTF-3679 to master\n\n# By iulian.vechiu\n# Via iulian.vechiu\n* commit \'498fea67874d92e3aadf4083c0ab0c8807f693a9\':\n Deleted ATF Helper V1\n Download Helper V2 test\n dump sql update\n ATF Helper v2, CR updates\n deleted redundant test\n stack id modification\n URLs moved to Cests.\n Update Codeception Tests - Code Review\n Code review improvements\n Advanced Filter Tests\n Code Review Improvements\n Functions and variable refactoring, broken down functionalities, minor tweeks","parents":[{"id":"3ae70bf7c146c248cad801478e5e096cd4bbe8c1","displayId":"3ae70bf7c14","author":{"name":"iulian.vechiu","emailAddress":"iulian.vechiu@emag.ro"},"authorTimestamp":1491376432000,"message":"added new environment in Atf helper","parents":[{"id":"392237f55480189961d3f86e1a3c5d08d18f6c5f","displayId":"392237f5548"}]},{"id":"498fea67874d92e3aadf4083c0ab0c8807f693a9","displayId":"498fea67874","author":{"name":"iulian.vechiu","emailAddress":"iulian.vechiu@emag.ro"},"authorTimestamp":1491897921000,"message":"merge with master","parents":[{"id":"087b01ab186428382d322952d2f4ee6380d12107","displayId":"087b01ab186"},{"id":"3ae70bf7c146c248cad801478e5e096cd4bbe8c1","displayId":"3ae70bf7c14"}]}],"attributes":{"jira-key":["EMGCTF-3679"]},"properties":{"jira-key":["EMGCTF-3679"]}}')
            );

        $this->assertEquals('cc11e4ead1974ab729a16805c4a4fd049db43ebc', $this->codeRepositoryService->getApiHeadCommitHash('ssh://git@stash.emag.network/emgctf/atf-codecept_v2.2.git', 'master'));
    }

    public function testGetApiHeadCommitHashWithError()
    {
        $this->guzzleClientMock
            ->expects($this->once())
            ->method('get')
            ->willThrowException(
                new ClientException('Message', new Request('Message', 'url'))
            );

        $this->setExpectedException(AtfException::class);
        $this->codeRepositoryService->getApiHeadCommitHash('ssh://git@stash.emag.network/emgctf/atf-codecept_v2.2.git', 'bogus');
    }
}
