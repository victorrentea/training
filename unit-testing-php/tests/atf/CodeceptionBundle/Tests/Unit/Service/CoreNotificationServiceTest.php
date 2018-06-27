<?php

namespace Emag\Core\CodeceptionBundle\Service;

use Doctrine\Common\Persistence\ObjectManager;
use Doctrine\Common\Persistence\ObjectRepository;
use Emag\Core\BaseBundle\Exception\AtfException;
use Emag\Core\CodeceptionBundle\Entity\CoreNotification;
use Emag\Core\CodeceptionBundle\Repository\CoreNotificationRepository;
use Psr\Log\LoggerInterface;

class CoreNotificationServiceTest extends \PHPUnit_Framework_TestCase
{
    /**
     * @var LoggerInterface|\PHPUnit_Framework_MockObject_MockObject
     */
    private $loggerMock;

    /** @var CoreNotificationService|\PHPUnit_Framework_MockObject_MockObject */
    private $notificationService;

    protected function setUp()
    {
        parent::setUp();

        $this->loggerMock = $this->getMockBuilder(LoggerInterface::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->notificationService = new CoreNotificationService();
        $this->notificationService->setLogger($this->loggerMock);
    }

    public function testInvalidExtractNotification() {
        $this->loggerMock->expects($this->once())->method('error');

        $this->notificationService->extractNotification('invalid_json');
    }

    public function testValidExtractNotification() {
        $this->loggerMock->expects($this->never())->method('error');

        $response = $this->notificationService->extractNotification('{"type": "some_type", "stack-code": "some_code"}');

        $this->assertEquals($response, (object)['type' => 'some_type', 'stackCode' => 'some_code']);
    }

    /**
     * @param $jsonString
     * @param $validateResponse
     * @dataProvider getMessageJsonStrings
     */
    public function testValidateNotification($jsonString, $validateResponse) {
        $response = $this->notificationService->validateNotification($jsonString);

        $this->assertEquals($response, $validateResponse);
    }

    public function getMessageJsonStrings()
    {
        return [
            [null, false],
            ['', false],
            ['{}', false],
            [(object)['type' => 'some_type'], false],
            [(object)['type' => 'some_type', 'stackCode' => 'some_code'], true],
        ];
    }

    public function testSaveNotificationWithValidMessage() {
        $message = '{"type": "some_type", "stack-code": "some_code"}';

        $objectManager = $this->getMockBuilder(ObjectManager::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->notificationService->setManager($objectManager);

        $objectManager->expects($this->once())
            ->method('persist');

        $objectManager->expects($this->once())
            ->method('flush');

        $this->notificationService->saveNotification($message);
    }

    public function testSaveNotificationWithInvalidMessage() {
        $message = '{"type": "some_type" "invalid": "inv"}';

        $objectManager = $this->getMockBuilder(ObjectManager::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->notificationService->setManager($objectManager);

        $objectManager->expects($this->once())
            ->method('persist');

        $objectManager->expects($this->once())
            ->method('flush');

        $this->setExpectedException(AtfException::class, 'Invalid request');

        $this->notificationService->saveNotification($message);
    }

    public function testSetProcessed() {
        $notification = json_decode('{"type": "some_type", "stack-code": "some_code", "core_notification_id": 1}');

        $coreNotificationRepositoryMock = $this->getMockBuilder(CoreNotificationRepository::class)
            ->disableOriginalConstructor()
            ->getMock();

        $coreNotificationMock = $this->getMockBuilder(CoreNotification::class)
            ->disableOriginalConstructor()
            ->getMock();

        $objectManager = $this->getMockBuilder(ObjectManager::class)
            ->disableOriginalConstructor()
            ->getMock();

        $objectManager
            ->method("getRepository")
            ->willReturn($coreNotificationRepositoryMock);

        $coreNotificationRepositoryMock
            ->method("find")
            ->willReturn($coreNotificationMock);


        $this->notificationService->setManager($objectManager);

        $objectManager->expects($this->once())
            ->method('persist');

        $objectManager->expects($this->once())
            ->method('flush');

        $res = $this->notificationService->setProcessed($notification, 1);
    }
}