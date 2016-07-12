<?php

namespace MS\ExamplePHP\ObserverPattern\Event;

use MS\ExamplePHP\ObserverPattern\Model\Order;
use Symfony\Component\EventDispatcher\Event;

class OrderCreateEvent extends Event
{
    /** @var  Order */
    protected $order;

    /**
     * @param Order $order
     */
    public function __construct(Order $order)
    {
        $this->order = $order;
    }
}
