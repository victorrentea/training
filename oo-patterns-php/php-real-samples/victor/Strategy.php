<?php

namespace MS\ExamplePHP\ProxyPattern\Service;

use Doctrine\ORM\EntityManagerInterface;
use MS\ExamplePHP\ObserverPattern\Service\OrderService as DefaultOrderService;
use MS\ExamplePHP\ObserverPattern\Service\OrderServiceInterface;


interface class CustomsCalculator {
	function canHandle($countryIso);
	function calculate($value);
}

public class UeTvaCalculator implements CustomsCalculator {
	function calculate($value) { return $value; }
	function canHandle($countryIso) {return inarray(UE_COUNTRIES, $package->getDestinationCountryISO();}
}

public class ChinaTvaCalculator implements CustomsCalculator {
	function calculate($value) { return $value*2; }
	function canHandle($countryIso) { 
		return $package->getDestinationCountryISO() == "CH";
	}
}

public class DefaultTvaCalculator implements CustomsCalculator {
	function calculate($value) {return $value * 1.50;}
	function canHandle($countryIso) {return true;}
}


public class CustomsManager {
	
	private $algorithms = [];
	
	public function addAlgo($algo) {
		$this->algorithms->add($algo);
	}
	
	public function calculateCustoms($package) {
		foreach ($this->algorithms as $algo) {
			if ($algo->canHandle($package->getCountryIso())) {
				$algo.calculate($package->getValue());	
				return;
			}
		}
		throw new RuntimeExce();
	}
}

{
	private $customsManager;
	
	function () {
		$this->customsManager->add(new ChineTvaCalculator());
		$this->customsManager->add(new UeTvaCalculator());
		$this->customsManager->add(new GreatBrexitTvaCalculator());
		$this->customsManager->add(new DefaultTvaCalculator());
		
		
		if (package->getOriginCountryIso() == "GB") {
			tvaCalculator = new GreatBrexitTvaCalculator();
		} else if () {}
		
		/** @var IActionHander */
		var $handler = ;
		button.addClickListener($handler);
)
	}
}