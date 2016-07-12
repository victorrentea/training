<?php

namespace MS\ExamplePHP\ProxyPattern\Service;

use Doctrine\ORM\EntityManagerInterface;
use MS\ExamplePHP\ObserverPattern\Service\OrderService as DefaultOrderService;
use MS\ExamplePHP\ObserverPattern\Service\OrderServiceInterface;

class OrderServiceWithTx implements OrderServiceInterface
{
    /** @var  DefaultOrderService */
    protected $orderService;

    /** @var  EntityManagerInterface */
    protected $manager;

    /**
     * @param DefaultOrderService    $defaultOrderService
     * @param EntityManagerInterface $manager
     */
    public function __construct(DefaultOrderService $defaultOrderService, EntityManagerInterface $manager)
    {
        $this->orderService = $defaultOrderService;
        $this->manager = $manager;
    }

    /**
     * @param array $data
     *
     * @throws \Exception
     */
    public function createOrder(array $data)
    {
        try {
            $this->manager->beginTransaction();

            try {
                $result = $this->orderService->createOrder($data);

                $this->manager->commit();

                return $result;
            } catch (\Exception $ex) {
                $this->manager->rollback();

                throw $ex;
            }
        } catch (\Exception $ex) {
            throw $ex;
        }
    }
}
