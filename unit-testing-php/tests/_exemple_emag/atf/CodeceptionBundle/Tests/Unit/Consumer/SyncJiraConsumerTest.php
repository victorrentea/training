<?php
namespace Emag\Core\CodeceptionBundle\Tests\Unit\Consumer;

use Emag\Core\CodeceptionBundle\Consumer\SyncJiraConsumer;
use Emag\Core\CodeceptionBundle\Entity\TestingPlan;
use Emag\Core\CodeceptionBundle\Service\JiraService;
use OldSound\RabbitMqBundle\RabbitMq\ConsumerInterface;
use PhpAmqpLib\Message\AMQPMessage;

class SyncJiraConsumerTest extends \PHPUnit_Framework_TestCase
{
    public function test()
    {
        /** @var JiraService|\PHPUnit_Framework_MockObject_MockObject $jiraServiceMock */
        $jiraServiceMock = $this->getMockBuilder(JiraService::class)->disableOriginalConstructor()->getMock();
        $syncJiraConsumer = new SyncJiraConsumer($jiraServiceMock);

        $testingPlan = new TestingPlan();
        $addedTests = [];
        $removedTests = [];
        $jiraServiceMock->expects($this->once())->method('sync')->with($testingPlan, $addedTests, $removedTests);

        $ret = $syncJiraConsumer->execute(new AMQPMessage(base64_encode(serialize([$testingPlan, $addedTests, $removedTests]))));

        $this->assertEquals(ConsumerInterface::MSG_ACK, $ret);
    }
}
