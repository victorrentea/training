<?php

namespace MS\ExamplePHP\ProxyPattern\Service;

use MS\ExamplePHP\ObserverPattern\Service\OrderService as DefaultOrderService;
use MS\ExamplePHP\ObserverPattern\Service\OrderServiceInterface;
use Psr\Log\LoggerInterface;


class OrderBuilder 
{
    /** @var  Order */
    private $order;
	
	/**
	
	class Order {
		public function __construct() {
			
		}
	
		setCreationDate($date) {}
		setDeliveryAddress($address)
		getItems() : List<OrderItems>
	}
	
	*/

    public function __construct()
    {
        $this->order = new Order();
    }

	public function withCreationDate($date) {
		this->order->setCreationDate($date);
		return $this;
	}
	
	/**
     * @param Address $data
     */
	public function withDeliveryAddress($address) {
		if ($address instanceof AddressBuilder) {
			$address = $address->build();
		}
		this->order->setDeliveryAddress($address);
		return $this;
	}
	
	
	public function withItem($item) {
		this->order->addItem(item);
		return $this;
	}
	
	public function build() {
		
		// validari TONE de VALIDARI TONE TONE
		
		return clone this->order;
	}
	
	{
		
		$orderBuilder = new OrderBuilder()
			->withCreationDate(new Date())
			->withItem(new OrderItem())
			->withDeliveryAddress(new AddressBuilder()
				->withStreetName("Sos Bucuresti Nord"));
				
		$order1 = orderBuilder
				->withCreationDate(altaData)
				->build();
		$order2 = orderBuilder->build();
		
		$order1->getAddress()->setStreetName("Alta strada")
		$order2->getAddress()->getStreetName()
			
	}
	
	class ObjectMother {
		public static function anOrder() {
			return new OrderBuiler()
				->withItem(...);
		}
	}
	
	
}
