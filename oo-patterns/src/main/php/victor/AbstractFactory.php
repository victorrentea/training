<?php

namespace MS\ExamplePHP\ProxyPattern\Service;

use MS\ExamplePHP\ObserverPattern\Service\OrderService as DefaultOrderService;
use MS\ExamplePHP\ObserverPattern\Service\OrderServiceInterface;
use Psr\Log\LoggerInterface;


{
	@var UIComponentFactory 
	private $themeFactory;
	
	{
		this->$themeFactory->create
	}
	
	
}


///---------------------

interface  UIComponentFactory {
	/** @return IButton */
    function createButton();
	function createPanel();
	function createTextField();
}

interface  IButton {
	function click();
	function draw();
}
	
// -------------------
	
class SteelThemeComponentFactory implements UIComponentFactory {
	public function createButton() {
		return new SteelThemeButton();
	}
	public function createPanel();
	public function createTextField();
}

class SteelThemeButton implements IButton {
	public function click() {
		
	}
	
	public function draw() {
		// deseneaza Heavy Metal
	}
}

// -----------
class HelloKittyThemeComponentFactory implements UIComponentFactory {
	public function createButton() {
		return new SteelThemeButton();
	}
	public function createPanel();
	public function createTextField();
}

class HelloKittyThemeButton implements IButton {
	public function click() {
		
	}
	
	public function draw() {
		// deseneaza flower power
	}
}
