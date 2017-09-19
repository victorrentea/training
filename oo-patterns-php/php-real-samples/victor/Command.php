<?php

namespace MS\ExamplePHP\ProxyPattern\Service;

use Doctrine\ORM\EntityManagerInterface;
use MS\ExamplePHP\ObserverPattern\Service\OrderService as DefaultOrderService;
use MS\ExamplePHP\ObserverPattern\Service\OrderServiceInterface;



class MyDependency {
	function doStuff($nr) {
		
	}
}

class MessageConsumer {
	private $myDependency;
	private $myDependency2;
	public function consumeMessage($message) { // va fi invocata de supervizor
		// roaga-te putin
		// ba, da ce-o fi cumesaju asta ?
		// ce tre sa fac cu el
		if ($message instanceof DoStuffCommand) {
			$this->myDependency->doStuff($message->nr);
		} elseif ($message instanceof DoStuff2Command) {
			$this->myDependency2->doStuff($message->nr);
		}
	}
}

class MessagePublisher {
	public function sendMessage($command) {
		// ia coada, pune pe coada, vezi exceptii, roaga-te putin
	}
}


class DoStuffCommand {
	public static $nr;
	public __construct($nr) {
		$this->nr=$nr;
	}
}



class Logic {
	private $messagePublisher;
	function f(){
		//$myDependency.doStuff(3);
		$this->messagePublisher->sendMessage(new DoStuffCommand(3));
	}
}