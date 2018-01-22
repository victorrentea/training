<?php

namespace MS\ExamplePHP\ObserverPattern\Service;

use MS\ExamplePHP\ObserverPattern\Event\OrderCreateEvent;
use MS\ExamplePHP\ObserverPattern\Model\Order;
use Symfony\Component\EventDispatcher\EventDispatcherInterface;

class OrderService
{
    /** @var EventDispatcherInterface  */
    protected $dispatcher;

    /**
     * @param EventDispatcherInterface $dispatcher
     */
    public function __construct(EventDispatcherInterface $dispatcher)
    {
        $this->dispatcher = $dispatcher;
    }

    /**
     * @param array $array
     */
    public function createOrder(array $array)
    {
        $order = new Order();
        $order->loadFromArray($array);

        $this->dispatcher->dispatch('order.create', new OrderCreateEvent($order));
    }
}
