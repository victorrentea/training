<?php

namespace Emag\Core\BaseBundle\Tests\Unit\EventListener;


use Doctrine\ORM\EntityManagerInterface;
use Doctrine\ORM\Event\OnFlushEventArgs;
use Doctrine\ORM\Event\PostFlushEventArgs;
use Doctrine\ORM\UnitOfWork;
use Emag\Core\BaseBundle\Event\TrackableObjectEvent;
use Emag\Core\BaseBundle\EventListener\TrackableEntitiesLifecycleListener;
use Emag\Core\BaseBundle\Trackable\TrackableInterface;
use Symfony\Component\EventDispatcher\EventDispatcherInterface;

class TrackableEntitiesLifecycleListenerTest extends \PHPUnit_Framework_TestCase
{

    /**
     * @param $scheduledForInsertions
     * @param $scheduledForUpdates
     * @param $scheduledForDeletion
     * @dataProvider getScheduledEntitiesCombinations
     */
    public function testOnFlush($scheduledForInsertions, $scheduledForUpdates, $scheduledForDeletion)
    {
        $this->onFlush($scheduledForInsertions, $scheduledForUpdates, $scheduledForDeletion);
    }

    public function getScheduledEntitiesCombinations()
    {
        return [
            [
                [], [], []
            ],
            [
                [], ['update' => $this->getMockBuilder(TrackableInterface::class)->getMock()], [] //One scheduled for update
            ],
            [
                [], [], ['delete' => $this->getMockBuilder(TrackableInterface::class)->getMock()] //One scheduled for delete
            ],
            [
                ['insert' => $this->getMockBuilder(TrackableInterface::class)->getMock()], [], [] //One scheduled for insert
            ],
            [
                ['insert' => $this->getMockBuilder(TrackableInterface::class)->getMock()], ['update' => $this->getMockBuilder(TrackableInterface::class)->getMock()], [] //One insert, one update
            ],
            [
                ['insert' => $this->getMockBuilder(TrackableInterface::class)->getMock()], ['update' => $this->getMockBuilder(TrackableInterface::class)->getMock()], ['delete' => $this->getMockBuilder(TrackableInterface::class)->getMock()] //One insert, one update, one delete
            ],
        ];
    }

    private function onFlush($scheduledEntitiesInsertions = array(), $scheduledEntitiesUpdates = array(), $scheduledEntitiesDeletions = array())
    {
        $eventDispatcherMock = $this->getMockBuilder(EventDispatcherInterface::class)->getMock();

        $callIndex = -1;

        $this->addExpectationsToEventDispatcher($eventDispatcherMock, $scheduledEntitiesInsertions, TrackableObjectEvent::ACTION_ADDED, $callIndex);
        $this->addExpectationsToEventDispatcher($eventDispatcherMock, $scheduledEntitiesUpdates, TrackableObjectEvent::ACTION_UPDATED, $callIndex);
        $this->addExpectationsToEventDispatcher($eventDispatcherMock, $scheduledEntitiesDeletions, TrackableObjectEvent::ACTION_DELETED, $callIndex);

        $trackableEntitiesLifecycleListener = new TrackableEntitiesLifecycleListener();
        $trackableEntitiesLifecycleListener->setEventDispatcher($eventDispatcherMock);

        $unitOfWorkMock = $this->getUnitOfWorkMock($scheduledEntitiesInsertions, $scheduledEntitiesUpdates, $scheduledEntitiesDeletions);
        $entityManagerMock = $this->getEntityManagerMock($unitOfWorkMock);
        $onFlushEventArgs = $this->getOnFlushEventArgs($entityManagerMock);
        $postFlushEventArgs = $this->getPostFlushEventArgs($entityManagerMock);

        $trackableEntitiesLifecycleListener->onFlush($onFlushEventArgs);
        $trackableEntitiesLifecycleListener->postFlush($postFlushEventArgs);
    }

    private function addExpectationsToEventDispatcher(\PHPUnit_Framework_MockObject_MockObject $eventDispatcherMock, array $entities, $action, &$callIndex)
    {
        foreach ($entities as $entity) {
            $eventDispatcherMock->expects($this->at(++$callIndex))
                ->method('dispatch')
                ->with(
                    'trackable.flushed',
                    $this->callback(function (TrackableObjectEvent $event) use ($entity, $action) {
                        return
                            $event->getAction() === $action
                            &&
                            $event->getTrackable() === $entity;
                    }));
        }
    }


    private function getUnitOfWorkMock($scheduledEntitiesInsertions = array(), $scheduledEntitiesUpdates = array(), $scheduledEntitiesDeletions = array())
    {
        $unitOfWorkMock = $this->getMockBuilder(UnitOfWork::class)->disableOriginalConstructor()->getMock();

        $unitOfWorkMock->expects($this->any())
            ->method('getScheduledEntityInsertions')
            ->willReturn($scheduledEntitiesInsertions);
        $unitOfWorkMock->expects($this->any())
            ->method('getScheduledEntityUpdates')
            ->willReturn($scheduledEntitiesUpdates);
        $unitOfWorkMock->expects($this->any())
            ->method('getScheduledEntityDeletions')
            ->willReturn($scheduledEntitiesDeletions);
        $unitOfWorkMock->expects($this->any())
            ->method('getEntityChangeSet')
            ->willReturn(array());
        $unitOfWorkMock->expects($this->any())
            ->method('getScheduledCollectionUpdates')
            ->willReturn(array());
        $unitOfWorkMock->expects($this->any())
            ->method('getScheduledCollectionDeletions')
            ->willReturn(array());

        return $unitOfWorkMock;
    }

    private function getEntityManagerMock($unitOfWork)
    {
        $entityManagerMock = $this->getMockBuilder(EntityManagerInterface::class)->getMock();
        $entityManagerMock->expects($this->any())
            ->method('getUnitOfWork')
            ->willReturn($unitOfWork);

        return $entityManagerMock;
    }

    private function getOnFlushEventArgs($entityManager)
    {
        $onFlushEventArgs = $this->getMockBuilder(OnFlushEventArgs::class)->disableOriginalConstructor()->getMock();
        $onFlushEventArgs->expects($this->once())
            ->method('getEntityManager')
            ->willReturn($entityManager);

        return $onFlushEventArgs;
    }

    private function getPostFlushEventArgs($entityManager)
    {
        $postFlushEventArgs = $this->getMockBuilder(PostFlushEventArgs::class)->disableOriginalConstructor()->getMock();
        $postFlushEventArgs->expects($this->once())
            ->method('getEntityManager')
            ->willReturn($entityManager);

        return $postFlushEventArgs;
    }
}
