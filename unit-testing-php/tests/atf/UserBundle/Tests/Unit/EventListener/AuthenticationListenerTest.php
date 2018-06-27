<?php

namespace Emag\Core\UserBundle\Tests\Unit\EventListener;

use Emag\Core\UserBundle\EventListener\AuthenticationListener;
use Psr\Log\LoggerInterface;
use Symfony\Component\Security\Core\Authentication\Token\TokenInterface;
use Symfony\Component\Security\Core\Event\AuthenticationEvent;
use Symfony\Component\Security\Core\Event\AuthenticationFailureEvent;
use Symfony\Component\Security\Core\Exception\AuthenticationException;

class AuthenticationListenerTest extends \PHPUnit_Framework_TestCase
{
    public function testOnFailure()
    {
        $mockAuthenticationException = $this->getMockBuilder(AuthenticationException::class)->disableOriginalConstructor()->getMock();
        $mockAuthenticationException->expects($this->once())
            ->method('serialize')
            ->willReturn('serialized exception');

        $mockFailureEvent = $this->getMockEvent(AuthenticationFailureEvent::class, $this->getMockAuthenticationToken());

        $mockFailureEvent->expects($this->once())
            ->method('getAuthenticationException')
            ->willReturn($mockAuthenticationException);

        $mockLogger = $this->getMockBuilder(LoggerInterface::class)->getMock();
        $context = array(
            'username' => 'username',
            'exception' => 'serialized exception'
        );
        $mockLogger->expects($this->once())
            ->method('notice')
            ->with(
                'event_name',
                $context
            );
        $authenticationListener = new AuthenticationListener();
        $authenticationListener->setLogger($mockLogger);

        $authenticationListener->onFailure($mockFailureEvent, 'event_name');
    }

    public function testOnSuccess()
    {

        $mockEvent = $this->getMockEvent(AuthenticationEvent::class, $this->getMockAuthenticationToken());

        $mockLogger = $this->getMockBuilder(LoggerInterface::class)->getMock();
        $context = array(
            'username' => 'username',
        );
        $mockLogger->expects($this->once())
            ->method('info')
            ->with(
                'event_name',
                $context
            );
        $authenticationListener = new AuthenticationListener();
        $authenticationListener->setLogger($mockLogger);

        $authenticationListener->onSuccess($mockEvent, 'event_name');
    }

    private function getMockAuthenticationToken()
    {
        $mockAuthenticationToken = $this->getMockBuilder(TokenInterface::class)->getMock();
        $mockAuthenticationToken->expects($this->once())
            ->method('getUsername')
            ->willReturn('username');

        return $mockAuthenticationToken;
    }

    private function getMockEvent($class, $authenticationToken)
    {
        $mockEvent = $this->getMockBuilder($class)->disableOriginalConstructor()->getMock();
        $mockEvent->expects($this->once())
            ->method('getAuthenticationToken')
            ->willReturn($authenticationToken);

        return $mockEvent;
    }
}
