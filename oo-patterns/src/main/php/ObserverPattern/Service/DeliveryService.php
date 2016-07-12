<?php

namespace MS\ExamplePHP\ObserverPattern\Service;

use MS\ExamplePHP\ObserverPattern\Event\OrderCreateEvent;
use MS\ExamplePHP\ObserverPattern\Model\Delivery;
use MS\ExamplePHP\ObserverPattern\Model\Order;
use Symfony\Component\EventDispatcher\EventDispatcherInterface;

class DeliveryService
{
    /** @var EventDispatcherInterface  */
    protected $dispatcher;

    /**
     * @param EventDispatcherInterface $dispatcher
     */
    public function __construct(EventDispatcherInterface $dispatcher)
    {
        $this->dispatcher = $dispatcher;

        $this->dispatcher->addListener('order.create', [$this, 'onOrderCreate']);
    }

    /**
     * @param OrderCreateEvent $event
     */
    public function onOrderCreate(OrderCreateEvent $event)
    {
        $order = $event->getOrder();
        $this->createDelivery($order);
    }

    /**
     * @param Order $order
     */
    public function createDelivery(Order $order)
    {
        $delivery = new Delivery();
        $delivery->loadFromOrder($order);

        /* @TODO validate and persist data */
    }
}
