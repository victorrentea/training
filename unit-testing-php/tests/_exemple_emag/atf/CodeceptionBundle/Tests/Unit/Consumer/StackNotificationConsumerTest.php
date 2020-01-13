<?php
namespace Emag\Core\CodeceptionBundle\Tests\Consumer;

use Emag\Core\CodeceptionBundle\Consumer\StackNotificationConsumer;
use Emag\Core\CodeceptionBundle\Service\CoreNotificationService;
use Emag\Core\CodeceptionBundle\Service\ScheduleTriggerService;
use Emag\Core\CodeceptionBundle\Service\StackSyncService;
use OldSound\RabbitMqBundle\RabbitMq\ConsumerInterface;
use PhpAmqpLib\Message\AMQPMessage;
use Psr\Log\LoggerInterface;

class StackNotificationConsumerTest extends \PHPUnit_Framework_TestCase
{
    /**
     * @var StackSyncService|\PHPUnit_Framework_MockObject_MockObject
     */
    private $stackSyncServiceMock;

    /**
     * @var CoreNotificationService|\PHPUnit_Framework_MockObject_MockObject
     */
    private $notificationServiceMock;

    /**
     * @var ScheduleTriggerService|\PHPUnit_Framework_MockObject_MockObject
     */
    private $scheduleTriggerServiceMock;

    /**
     * @var LoggerInterface|\PHPUnit_Framework_MockObject_MockObject
     */
    private $loggerMock;

    /**
     * @var StackNotificationConsumer
     */
    private $stackNotificationConsumer;

    protected function setUp()
    {
        $this->stackSyncServiceMock = $this->getMockBuilder(StackSyncService::class)
                                           ->disableOriginalConstructor()
                                           ->getMock();

        $this->notificationServiceMock = $this->getMockBuilder(CoreNotificationService::class)
                                            ->disableOriginalConstructor()
                                            ->getMock();

        $this->scheduleTriggerServiceMock = $this->getMockBuilder(ScheduleTriggerService::class)
                                            ->disableOriginalConstructor()
                                            ->getMock();

        $this->loggerMock = $this->getMockBuilder(LoggerInterface::class)
                                ->disableOriginalConstructor()
                                ->getMock();

        $this->stackNotificationConsumer = new StackNotificationConsumer();
        $this->stackNotificationConsumer->setLogger($this->loggerMock);
        $this->stackNotificationConsumer->setStackSyncService($this->stackSyncServiceMock);
        $this->stackNotificationConsumer->setNotificationService($this->notificationServiceMock);
        $this->stackNotificationConsumer->setScheduleTriggerService($this->scheduleTriggerServiceMock);
    }

    /**
     * @param $undecodableJsonString
     * @dataProvider getUndecodableJsonStrings
     */
    public function testExecuteWillRejectTheMessageWhenTheBodyCantBeJsonDecoded($undecodableJsonString)
    {
        $this->notificationServiceMock
            ->expects($this->once())
            ->method('extractNotification')
            ->willReturn(null);

        $this->notificationServiceMock
            ->expects($this->once())
            ->method('validateNotification')
            ->willReturn(false);

        $res = $this->stackNotificationConsumer->execute(new AMQPMessage($undecodableJsonString));

        $this->assertEquals(
            ConsumerInterface::MSG_REJECT,
            $res
        );
    }

    public function getUndecodableJsonStrings()
    {
        return [
            ['string'],
        ];
    }

    /**
     * @param $undecodableJsonString
     * @param $extractResponse
     * @param $validateResponse
     * @dataProvider getInvalidMessageJsonStrings
     */
    public function testExecuteWillRejectTheMessageWhenTheDecodedNotificationIsInvalid($undecodableJsonString, $extractResponse, $validateResponse)
    {
        $this->notificationServiceMock
            ->expects($this->once())
            ->method('extractNotification')
            ->willReturn($extractResponse);

        $this->notificationServiceMock
            ->expects($this->once())
            ->method('validateNotification')
            ->willReturn($validateResponse);

        $res = $this->stackNotificationConsumer->execute(new AMQPMessage($undecodableJsonString));

        $this->assertEquals(
            ConsumerInterface::MSG_REJECT,
            $res
        );
    }

    public function getInvalidMessageJsonStrings()
    {
        return [
            [null, null, false],
            ['', null, false],
            ['{}', '{}', false],
            ['{"unu":"doi"}', '{"unu":"doi"}', false],
        ];
    }

    /**
     * @param $type
     * @param $code
     * @param $method
     * @param $service
     * @dataProvider getValidAndProcessableNotification
     */
    public function testExecuteWillProcessAValidNotificationAndAcknowledgeTheMessage($type, $code, $method, $service)
    {
        $message = new AMQPMessage(sprintf('{"type": "%s", "stack-code": "%s"}', $type, $code));

        $this->$service
            ->expects($this->once())
            ->method($method)
            ->with($code);

        $this->notificationServiceMock
            ->expects($this->once())
            ->method('extractNotification')
            ->willReturn((object)['type' => $type, 'stackCode' => $code]);

        $this->notificationServiceMock
            ->expects($this->once())
            ->method('validateNotification')
            ->willReturn(true);

        $res = $this->stackNotificationConsumer->execute($message);

        $this->assertEquals(
            ConsumerInterface::MSG_ACK,
            $res
        );
    }

    public function getValidAndProcessableNotification()
    {
        return [
            ['stack-allocated', '29', 'importStack', 'stackSyncServiceMock'],
            ['stack-deallocated', '29', 'removeStack', 'stackSyncServiceMock'],
            ['stack-deployed', '29', 'handleDeploy', 'scheduleTriggerServiceMock'],
        ];
    }

    /**
     * @param $type
     * @param $code
     * @dataProvider getUnimplementedNotification
     */
    public function testExecuteWillProcessAndAcknowledgeAnUnimplementedNotificationType($type, $code)
    {
        $message = new AMQPMessage(sprintf('{"type": "%s", "stack-code": "%s"}', $type, $code));

        $this->notificationServiceMock
            ->expects($this->once())
            ->method('extractNotification')
            ->willReturn((object)['type' => $type, 'stackCode' => $code]);

        $this->notificationServiceMock
            ->expects($this->once())
            ->method('validateNotification')
            ->willReturn(true);

        $this->loggerMock->expects($this->never())->method('error');
        $this->stackSyncServiceMock->expects($this->never())->method('importStack');
        $this->stackSyncServiceMock->expects($this->never())->method('removeStack');
        $this->scheduleTriggerServiceMock->expects($this->never())->method('handleDeploy');

        $res = $this->stackNotificationConsumer->execute($message);

        $this->assertEquals(
            ConsumerInterface::MSG_ACK,
            $res
        );
    }

    public function getUnimplementedNotification()
    {
        return [
            ['instance-deployed', '29'],
        ];
    }
}
