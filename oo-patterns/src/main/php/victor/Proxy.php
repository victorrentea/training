<?php

namespace MS\ExamplePHP\ProxyPattern\Service;

use Doctrine\ORM\EntityManagerInterface;
use MS\ExamplePHP\ObserverPattern\Service\OrderService as DefaultOrderService;
use MS\ExamplePHP\ObserverPattern\Service\OrderServiceInterface;



// ---- generata din WSDL si pe masina client si pe server

interface MySoapWebService {
	public function getUserResponsibility($x);
}


// ---- pe masina server

class MyEndpointImplenenbtatin implements MySoapWebService {
	public function getUserResponsibility($x) {
		//actual implem. database. 
	}
}

// ---- pe masina client
{
	$proxy = webServiceFramework.createProxy();
	$t0 = time()
	$proxy.getUserResponsibility($x);
	$t2 = time()
		
	
}
