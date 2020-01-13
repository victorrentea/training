<?php

namespace Emag\Core\GeneratorService\Service;

use Doctrine\Common\Persistence\ObjectManager;
use Emag\Core\GeneratorBundle\Service\OrderService;
use Emag\Core\JobBundle\Entity\JobInfo;
use Guzzle\Stream\Stream;
use GuzzleHttp\Client;
use GuzzleHttp\Exception\ClientException;
use GuzzleHttp\Psr7\Request;
use GuzzleHttp\Psr7\Response;

class OrderServiceTest extends \PHPUnit_Framework_TestCase
{
    /** @var OrderService */
    protected $orderService;

    /** @var Client|\PHPUnit_Framework_MockObject_MockObject */
    protected $clientMock;

    /** @var Response|\PHPUnit_Framework_MockObject_MockObject */
    protected $responseMock;

    /** @var ObjectManager|\PHPUnit_Framework_MockObject_MockObject */
    private $objectMangerMock;

    protected function setUp()
    {
        parent::setUp();

        $this->orderService = new OrderService();

        $this->clientMock = $this->getMockBuilder(Client::class)
            ->setMethods(['get'])
            ->disableOriginalConstructor()
            ->getMock();

        $this->responseMock = $this->getMockBuilder(Response::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->objectMangerMock = $this->getMockBuilder(ObjectManager::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->orderService->setClient($this->clientMock);
        $this->orderService->setManager($this->objectMangerMock);
    }

    public function testGetCities()
    {
        $this->clientMock
            ->expects($this->once())
            ->method('get')
            ->willReturn($this->responseMock);

        $data = $this->getMockBuilder(Stream::class)
            ->setMethods(['getContents'])
            ->disableOriginalConstructor()
            ->getMock();

        $this->responseMock
            ->expects($this->once())
            ->method('getBody')
            ->willReturn($data);

        $this->orderService->getCities(1);
    }

    public function testGetCitiesWithError()
    {
        $this->clientMock
            ->expects($this->once())
            ->method('get')
            ->willThrowException(
                new ClientException('Message', new Request('Message', 'url'))
            );

        $this->assertEquals(['success' => false, 'errCode' => 0], $this->orderService->getCities(9999999));
    }

    public function getShowrooms()
    {
        $this->clientMock
            ->expects($this->once())
            ->method('get')
            ->willReturn($this->responseMock);

        $data = $this->getMockBuilder(Stream::class)
            ->setMethods(['getContents'])
            ->disableOriginalConstructor()
            ->getMock();

        $this->responseMock
            ->expects($this->once())
            ->method('getBody')
            ->willReturn($data);

        $this->orderService->getShowrooms();
    }

    public function testGetShowroomsWithError()
    {
        $this->clientMock
            ->expects($this->once())
            ->method('get')
            ->willThrowException(
                new ClientException('Message', new Request('Message', 'url'))
            );

        $this->assertEquals(['success' => false, 'errCode' => 0], $this->orderService->getShowrooms());
    }

    public function testSetOrderIdNoData()
    {
        $this->assertFalse($this->orderService->setOrderId());
    }

    public function testSetOrderIdGoodData()
    {
        $jobInfo = new JobInfo();

        $reflectionProperty = new \ReflectionProperty(JobInfo::class, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($jobInfo, 2);

        $this->objectMangerMock
            ->expects($this->once())
            ->method('persist');

        $this->objectMangerMock
            ->expects($this->once())
            ->method('flush');

        $this->orderService->setOrderId($jobInfo, 'abc');
    }
}
