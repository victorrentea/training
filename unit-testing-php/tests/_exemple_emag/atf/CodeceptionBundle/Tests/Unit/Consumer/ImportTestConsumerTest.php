<?php
namespace Emag\Core\CodeceptionBundle\Tests\Unit\Consumer;

use Emag\Core\CodeceptionBundle\Consumer\ImportTestConsumer;
use Emag\Core\CodeceptionBundle\Service\ImportedTestService;
use Emag\SynchronizedBundle\Exception\CannotAquireLockException;
use OldSound\RabbitMqBundle\RabbitMq\ConsumerInterface;
use PhpAmqpLib\Message\AMQPMessage;
use Psr\Log\LoggerInterface;

class ImportTestConsumerTest extends \PHPUnit_Framework_TestCase
{
    public const ID = 42;

    /** @var ImportedTestService|\PHPUnit_Framework_MockObject_MockObject */
    private $importedTestServiceMock;

    /** @var ImportTestConsumer */
    private $importTestConsumer;

    /** @var LoggerInterface|\PHPUnit_Framework_MockObject_MockObject */
    private $loggerMock;

    protected function setUp()
    {
        $this->importedTestServiceMock = $this->getMockBuilder(ImportedTestService::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->loggerMock = $this->getMockBuilder(LoggerInterface::class)
            ->getMock();

        $this->importTestConsumer = new ImportTestConsumer($this->importedTestServiceMock);
        $this->importTestConsumer->setLogger($this->loggerMock);
    }

    /**
     * Test execute() with unserializable data will reject the message
     */
    public function testExecuteWithUnserializableDataWillThrowConsumerException()
    {
        // Given
        $message = new AMQPMessage('UNSERIALIZABLE_DATA');

        // When
        $flag = $this->importTestConsumer->execute($message);

        // Then
        $this->assertEquals(ConsumerInterface::MSG_REJECT, $flag);
    }

    /**
     * Test execute() will reject&requeue a valid import message
     * when it cannot acquire the import lock
     */
    public function testExecuteWillRejectAndRequeueWhenItCannotAcquireTheImportLock()
    {
        // Given
        $this->importedTestServiceMock
            ->expects($this->once())
            ->method('import')
            ->willThrowException(new CannotAquireLockException());

        // When
        $flag = $this->importTestConsumer->execute(
            new AMQPMessage(serialize($this->getValidImportData()))
        );

        // Then
        $this->assertEquals(ConsumerInterface::MSG_REJECT_REQUEUE, $flag);
    }

    /**
     * Test execute() will correctly try to import
     * and then acknowledge a valid import message
     */
    public function testExecuteWillCorrectlyImportAndAcknowledgeAValidImportMessage()
    {
        $this->importedTestServiceMock
            ->expects($this->once())
            ->method('import')
            ->with(static::ID);

        $flag = $this->importTestConsumer->execute(
            new AMQPMessage(serialize($this->getValidImportData()))
        );

        $this->assertEquals(ConsumerInterface::MSG_ACK, $flag);
    }

    /**
     * @return array
     */
    private function getValidImportData()
    {
        return [
            'id' => static::ID
        ];
    }
}
