<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/19/2017
 * Time: 12:44 PM
 */

namespace victor\training\oo\structural\facade;


class MyAppFacade
{
    /**
     * @var CustomerRepository
     */
    private $customerRepository;

    /**
     * @var CustomerService
     */
    private $customerService;

    /**
     * @var OrderService
     */
    private $orderService;

    public function placeOrder(Order order) {
        $customer = $this->customerRepository->get(customerId);
        $this->customerService->getGoldCardFor($customer);
        $this->orderService->placeOrder(order);
    }
}