<?php

namespace Emag\Core\UserBundle\Tests\Unit\EventListener;

use Doctrine\Common\Persistence\ObjectManager;
use Emag\Core\BaseBundle\Event\TrackableObjectEvent;
use Emag\Core\BaseBundle\Trackable\TrackableInterface;
use Emag\Core\UserBundle\Document\UserAction;
use Emag\Core\UserBundle\EventListener\UserActionsOnTrackablesListener;
use Symfony\Component\Security\Core\Authentication\Token\Storage\TokenStorage;
use Symfony\Component\Security\Core\Authentication\Token\TokenInterface;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;

class UserActionsOnTrackablesListenerTest extends \PHPUnit_Framework_TestCase
{
    public function testOnTrackableEvent()
    {
        $trackableMock = $this->getMockBuilder(TrackableInterface::class)->getMock();
        $trackableMock->expects($this->once())
            ->method('getId')
            ->willReturn('trackable_id');


        $tokenMock = $this->getMockBuilder(TokenInterface::class)->getMock();
        $tokenMock->expects($this->once())
            ->method('getUsername')
            ->willReturn('lastUserName');

        $tokenStorageMock = $this->getMockBuilder(TokenStorage::class)->disableOriginalConstructor()->getMock();

        $tokenStorageMock->expects($this->once())
            ->method('getToken')
            ->willReturn($tokenMock);

        $managerMock = $this->getMockBuilder(ObjectManager::class)->getMock();

        $managerMock->expects($this->once())
            ->method('persist')
            ->with(
                $this->callback(function (UserAction $userAction) use ($trackableMock) {
                    $trackable = $userAction->getActionable();
                    return
                        $userAction->getAction() === 'action' &&
                        $userAction->getUsername() === 'lastUserName' &&
                        $trackable->getId() === 'trackable_id' &&
                        $trackable->getExtra() === array('extra') &&
                        $trackable->getClass() === get_class($trackableMock);
                }));

        $userActionsOnTrackablesListener = new  UserActionsOnTrackablesListener($tokenStorageMock, $managerMock);


        $trackableEvent = new TrackableObjectEvent('action', $trackableMock, array('extra'));

        $userActionsOnTrackablesListener->onTrackableEvent($trackableEvent);
    }
}
