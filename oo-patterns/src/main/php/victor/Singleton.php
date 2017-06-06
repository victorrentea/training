<?php

namespace MS\ExamplePHP\ProxyPattern\Service;

use Doctrine\ORM\EntityManagerInterface;
use MS\ExamplePHP\ObserverPattern\Service\OrderService as DefaultOrderService;
use MS\ExamplePHP\ObserverPattern\Service\OrderServiceInterface;


class Singleton {
	private static Singleton INSTANCE;
	private dateDe100MB;
	
	public static getInstance() {
		if (this->INSTANCE == null) {
			this->INTANCE = new Singleton();
		}
		return this->INSTANCE;
	}
}



gigel:


Singleton s = new Singleton(); // inca 100 MB la toata lumea