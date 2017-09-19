<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 11:32 PM
 */

namespace victor\training\oo\behavioral\command;

include "PizzaMan.php";

class Customer
{

    private $pizzaMan;

    public function __construct(PizzaMan $pizzaMan)
    {
        $this->pizzaMan = $pizzaMan;
    }

    public function act() {
        printf("Shouting for a pizza!\n");
		$this->pizzaMan->bakePizza("Capriciosa", "thin");
	}

}

$customer = new Customer(new PizzaMan());
$customer->act();
