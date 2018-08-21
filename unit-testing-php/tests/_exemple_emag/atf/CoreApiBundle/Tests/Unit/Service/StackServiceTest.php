<?php
namespace Emag\Core\CoreApiBundle\Tests\Unit\Service;

use Emag\Core\CoreApiBundle\Entity\StackApplication;
use Emag\Core\CoreApiBundle\Entity\StackInfo;
use Emag\Core\CoreApiBundle\Service\StackService;
use GuzzleHttp\Client;
use GuzzleHttp\Psr7\Response;

class StackServiceTest extends \PHPUnit_Framework_TestCase
{
    public const API_URL = 'http://api.emag.local/';
    public const API_KEY = 'MY_SECRET_API_KEY';

    /**
     * @var StackService
     */
    private $stackService;

    /**
     * @var Client|\PHPUnit_Framework_MockObject_MockObject
     */
    private $clientMock;

    /**
     * @var \PHPUnit_Framework_MockObject_MockObject
     */
    private $loggerInterfaceMock;

    private $distributionsDatabases;

    protected function setUp()
    {
        $this->clientMock = $this->getMockBuilder(Client::class)
                ->disableOriginalConstructor()
                ->getMock();
        $this->stackService = new StackService($this->clientMock, static::API_URL, static::API_KEY);
        $this->loggerInterfaceMock = $this->getMockBuilder(\Psr\Log\LoggerInterface::class)->disableOriginalConstructor()->getMock();


        $this->distributionsDatabases = [
            "atf" => [
                "application" => "atf",
                "lang" => "",
                "dsqlKeyName" => "dsql:atf-sql1-prod",
                "host" => "dsql-mysql1234.emag.network",
                "port" => "1234",
                "reporting_status" => "",
            ],
            "eos" => [
                "application" => "eos",
                "lang" => "",
                "dsqlKeyName" => "",
                "host" => "",
                "port" => "",
                "reporting_status" => "",
            ],
            "mktp" => [
                "application" => "mktp",
                "lang" => "",
                "dsqlKeyName" => "",
                "host" => "",
                "port" => "",
                "reporting_status" => "",
            ]
        ];
    }

    public function testGetStacksWillCallProperApiEndpoint()
    {
        $this->setUpResponseForRelativeUrl('stacks', []);

        $this->stackService->getStacks();
    }

    public function testGetStackWillCallProperApiEndpointAndReturnNullOnEmptyResponse()
    {
        $this->setUpResponseForRelativeUrl('stacks/code', []);

        $ret = $this->stackService->getStack('code');

        $this->assertNull($ret);
    }

    public function testGetStackWillCallProperApiEndpointAndReturnAStackInfoOnCorrectResponse()
    {
        $info = [
            'code'              => 'code',
            'name'              => 'name',
            'deployment_status' => 'status',
            'type'              => 'type',
            'description'       => 'description',
            'owner'             => 'owner',
            'blocked'           => 'no',
            'environment'       => 'yes',
        ];
        $stackInfo = StackInfo::createFromArray($info);

        $this->setUpResponseForRelativeUrl('stacks/code', $info);

        $ret = $this->stackService->getStack('code');

        $this->assertEquals($stackInfo, $ret);
    }

    public function testGetStackApplicationsWillCallProperApiEndpointAndReturnAnApplicationsList()
    {
        $apps = ['app1', 'app2', 'app3'];
        $this->setUpResponseForRelativeUrl('stacks/code/applications', $apps);

        $ret = $this->stackService->getStackApplications('code');

        $this->assertEquals($apps, $ret);
    }

    public function testGetStackApplicationWillCallProperApiEndpoint()
    {
        $this->setUpResponseForRelativeUrl('stacks/code/applications/application', '');

        $ret = $this->stackService->getStackApplication('code', 'application');

        $this->assertEquals(new StackApplication(), $ret);
    }

    public function testGetStackReturnNullOnThrownClientException() {
        $clientExceptionMock = $this->getMockBuilder(\GuzzleHttp\Exception\ClientException::class)->disableOriginalConstructor()->getMock();

        $this->clientMock
            ->expects($this->once())
            ->method('request')
            ->will($this->throwException($clientExceptionMock));

        $this->stackService->setLogger($this->loggerInterfaceMock);
        $ret = $this->stackService->getStack('code');

        $this->assertNull($ret);
    }

    public function testGetStackReturnNullOnThrownServerException() {
        $serverExceptionMock = $this->getMockBuilder(\GuzzleHttp\Exception\ServerException::class)->disableOriginalConstructor()->getMock();

        $this->clientMock
            ->expects($this->once())
            ->method('request')
            ->will($this->throwException($serverExceptionMock));

        $this->stackService->setLogger($this->loggerInterfaceMock);
        $ret = $this->stackService->getStack('code');

        $this->assertNull($ret);
    }

    public function testGetDistributionsDatabases() {
        $allocationTokens = [
            "atf:host" => "atf.emag.network",
            "eos:host" => "atf.emag.network",
            "dsql:atf-sql1-prod:host" => "dsql-mysql1234.emag.network",
            "dsql:atf-sql1-prod:port" => "1234",
        ];
        $applications = ["atf", "eos", "mktp"];

        $stackServiceMock = $this->getMockBuilder(StackService::class)
                        ->setMethods(["getEdeployDistributionDatabases"])
                        ->disableOriginalConstructor()
                        ->getMock();

        $stackServiceMock->method("getEdeployDistributionDatabases")
                ->will($this->returnCallback(function($code, $distributionsDatabases) {
                    return $distributionsDatabases;
                }));

        $res = $stackServiceMock->getDistributionsDatabases("core-test", $applications, $allocationTokens);

        $this->assertEquals($res, $this->distributionsDatabases);
    }

    public function testGetEdeployDistributionDatabases() {
        $this->clientMock
            ->expects($this->any())
            ->method('request')
            ->will($this->returnCallback(function($type, $url) {
                preg_match("/\/(eos|mktp)\/master/", $url, $appName);
                if($appName[1] == "eos") {
                    return new Response(200, [], json_encode([
                        "common_data" => [
                            "{$appName[1]}.mysql_host" => "{$appName[1]}.local",
                            "{$appName[1]}.mysql_port" => "1234",
                        ]
                    ]));
                } else {
                    return new Response(200, [], json_encode([]));
                }
            }));


        $res = $this->stackService->getEdeployDistributionDatabases('core-test', $this->distributionsDatabases);

        $this->distributionsDatabases["eos"]["host"] = "eos.local";
        $this->distributionsDatabases["eos"]["port"] = "1234";
        $this->distributionsDatabases["eos"]["reporting_status"] = "SUCCESS";
        $this->distributionsDatabases["mktp"]["reporting_status"] = "ERROR => edeploy/test:core-test/mktp/master";

        $this->assertEquals($res, $this->distributionsDatabases);
    }

    private function setUpResponseForRelativeUrl($relativeUrl, $response)
    {
        static $at = 0;

        $this->clientMock
            ->expects($this->once())
            ->method('request')
            ->with('get', $this->getAbsoluteUrl($relativeUrl))
            ->willReturn(new Response(200, [], json_encode($response)));

        $at++;
    }

    /**
     * @param $relativeUrl
     * @return string
     */
    private function getAbsoluteUrl($relativeUrl)
    {
        return sprintf('%s%s?apikey=%s', static::API_URL, $relativeUrl, static::API_KEY);
    }

}
