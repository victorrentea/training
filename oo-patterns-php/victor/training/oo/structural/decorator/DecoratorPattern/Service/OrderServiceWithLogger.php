<?php

namespace MS\ExamplePHP\ProxyPattern\Service;

use MS\ExamplePHP\ObserverPattern\Service\OrderService as DefaultOrderService;
use MS\ExamplePHP\ObserverPattern\Service\OrderServiceInterface;
use Psr\Log\LoggerInterface;

class OrderServiceWithLogger implements OrderServiceInterface
{
    /** @var  DefaultOrderService */
    protected $orderService;

    /** @var  LoggerInterface */
    protected $loggerService;

    /**
     * @param DefaultOrderService $defaultOrderService
     * @param LoggerInterface     $loggerService
     */
    public function __construct(DefaultOrderService $defaultOrderService, LoggerInterface $loggerService)
    {
        $this->orderService = $defaultOrderService;
        $this->loggerService = $loggerService;
    }

    /**
     * @param array $data
     */
    public function createOrder(array $data)
    {
        $level = 0;
        $start = microtime(true);

        $this->orderService->createOrder($data);

        $this->loggerService->log($level, microtime(true) - $start);
    }
}
