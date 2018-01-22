<?php

namespace MS\ExamplePHP\ObserverPattern\Service;

use MS\ExamplePHP\ObserverPattern\Event\OrderCreateEvent;
use MS\ExamplePHP\ObserverPattern\Model\Delivery;
use MS\ExamplePHP\ObserverPattern\Model\Invoice;
use MS\ExamplePHP\ObserverPattern\Model\Order;
use Symfony\Component\EventDispatcher\EventDispatcherInterface;

class InvoiceService
{
    /** @var EventDispatcherInterface  */
    protected $dispatcher;

    /**
     * @param EventDispatcherInterface $dispatcher
     */
    public function __construct(EventDispatcherInterface $dispatcher)
    {
        $this->dispatcher = $dispatcher;

        $dispatcher->addListener('order.create', [$this, 'onOrderCreate']);
    }

    /**
     * @param OrderCreateEvent $event
     */
    public function onOrderCreate(OrderCreateEvent $event)
    {
        $order = $event->getOrder();
        $this->createInvoice($order);
    }

    /**
     * @param Order $order
     */
    public function createInvoice(Order $order)
    {
        $delivery = new Invoice();
        $delivery->loadFromOrder($order);

        /* @TODO validate and persist data */
    }
}
