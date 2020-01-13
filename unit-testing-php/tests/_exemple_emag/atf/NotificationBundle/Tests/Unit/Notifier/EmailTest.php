<?php

namespace Emag\Core\NotificationBundle\Tests\Unit\Notifier;

use Emag\Core\NotificationBundle\Notifier\Mailer;

class EmailTest extends \PHPUnit_Framework_TestCase
{
    public function testSend()
    {
        $swiftMailerMock = $this->getMockBuilder(\Swift_Mailer::class)->disableOriginalConstructor()->getMock();

        $swiftMailerMock->expects($this->once())
            ->method('send')
            ->with($this->callback(function (\Swift_Message $message) {
                return
                    $message->getTo() === ['email@domain.extension' => 'name'] &&
                    $message->getSubject() === '[ATF]subject' &&
                    $message->getBody() === 'body';
            }));
        $email = new Mailer($swiftMailerMock);

        $email->send(['email@domain.extension' => 'name'], 'subject', 'body');
    }
}
