<?php

namespace Emag\Core\BaseBundle\Tests\Unit\EventListener;

use Emag\Core\BaseBundle\EventListener\ExceptionLogger;
use Emag\Core\BaseBundle\Exception\AtfException;
use Emag\Core\CodeceptionBundle\Exception\TestException;
use PhpAmqpLib\Exception\AMQPRuntimeException;
use PhpAmqpLib\Exception\AMQPTimeoutException;
use Psr\Log\LoggerInterface;
use Symfony\Component\Console\Command\Command;
use Symfony\Component\Console\Event\ConsoleExceptionEvent;

class ExceptionLoggerTest extends \PHPUnit_Framework_TestCase
{
    public function testOnConsoleExceptionEventWithoutLogger()
    {
        $consoleExceptionEvent = $this->getMockBuilder(ConsoleExceptionEvent::class)->disableOriginalConstructor()->getMock();
        $consoleExceptionEvent->expects($this->never())->method('getException');
        $consoleExceptionEvent->expects($this->never())->method('getCommand');

        $exceptionLogger = new ExceptionLogger();
        $exceptionLogger->onConsoleExceptionEvent($consoleExceptionEvent);
    }

    public function testOnStandardException()
    {
        $this->onConsoleExceptionEvent(new \Exception('message'), 'critical');
    }

    public function testOnAtfException()
    {
        $this->onConsoleExceptionEvent(new AtfException('atf message'), 'error');
    }

    public function testOnTestException()
    {
        $this->onConsoleExceptionEvent(new TestException('test exception message'), 'notice');
    }

    public function testOnAMQPRuntimeException()
    {
        $this->onConsoleExceptionEvent(new AMQPRuntimeException('amqp runtime exception'), 'notice');
    }

    public function testOnAMQPTimeoutException()
    {
        $this->onConsoleExceptionEvent(new AMQPTimeoutException('amqp timeout exception'), 'notice');
    }

    private function onConsoleExceptionEvent(\Exception $exception, $methodName)
    {
        $loggerMock = $this->getMockBuilder(LoggerInterface::class)->getMock();

        $loggerMock->expects($this->once())
            ->method($methodName)
            ->with(
                $exception->getMessage(),
                $this->callback(function ($context) use ($exception) {
                    return $context === [
                        'command' => 'command_name',
                        'class' => 'Mock_Command',
                        'exception' => $exception
                    ];
                })
            );


        $commandMock = $this->getMockBuilder(Command::class)->disableOriginalConstructor()->setMockClassName('Mock_Command')->getMock();
        $commandMock->expects($this->atLeast(1))
            ->method('getName')
            ->willReturn('command_name');

        $consoleExceptionEvent = $this->getMockBuilder(ConsoleExceptionEvent::class)->disableOriginalConstructor()->getMock();
        $consoleExceptionEvent->expects($this->atLeast(1))
            ->method('getException')
            ->willReturn($exception);
        $consoleExceptionEvent->expects($this->atLeast(1))
            ->method('getCommand')
            ->willReturn($commandMock);

        $exceptionLogger = new ExceptionLogger();
        $exceptionLogger->setLogger($loggerMock);
        $exceptionLogger->onConsoleExceptionEvent($consoleExceptionEvent);
    }
}
