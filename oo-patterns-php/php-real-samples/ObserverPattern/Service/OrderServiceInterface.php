<?php

namespace MS\ExamplePHP\ObserverPattern\Service;

interface OrderServiceInterface
{
    /**
     * @param array $data
     */
    public function createOrder(array $data);
}
