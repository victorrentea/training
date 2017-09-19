<?php

namespace MS\ExamplePHP\ProxyPattern\Service;

use Doctrine\ORM\EntityManagerInterface;
use MS\ExamplePHP\ObserverPattern\Service\OrderService as DefaultOrderService;
use MS\ExamplePHP\ObserverPattern\Service\OrderServiceInterface;


class OldUglySMSSender {
	
	public function sendSMSXCADA($phoneStr, $textMessage) {
		
		if (invalidNumber($phoneStr)) {
			throw new UglySmsException(1);
		}
		if ($textMessage->length() > 200) {
			throw new UglySmsException(2);
		}
		
		actuallyFinallySendTheDarnSMS();
	}
}


class SmsSenderAdapter {
	/** @var OldUglySMSSender */
	private final $delegate;
	
	/** @param OldUglySMSSender */
	__construct ($delegate) {
		$this->delegate = $delegate;
	}
	/**
		@var Phone $phone
		..
	*/
	public function sendSms(PhoneNumber $phone, string $textMessage) {		
		try {
			$delegate->sendSMSXCADA($phone->toString(), $textMessage);
		} catch (Exception $exception) {
			throw new EmagException($exception); //:)
		} 
	}
}

class BusinessLogicClass{
	/**@var SmsSenderAdapter */
	private $smsSenderAdapter;
	
	// !!! ENTERING THE ZEN AREA !!!!
	public function zenMethod() {
		
		$this->smsSenderAdapter.sendSms($customer->getPhone(), $myMessage);
	}
	
}



