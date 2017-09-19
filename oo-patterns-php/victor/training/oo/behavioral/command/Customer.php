<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 11:32 PM
 */

namespace victor\training\oo\behavioral\command;

include "PizzaMan.php";
include "Waitress.php";
include "PizzaCommand.php";

class Customer
{
    /** @var  Waitress */
    private $waitress;

    /**
     * Customer constructor.
     * @param $waitress
     */
    public function __construct($waitress)
    {
        $this->waitress = $waitress;
    }


    public function act() {
        printf("Shouting for a pizza!\n");
		$this->waitress->orderPizza("Capriciosa", "thin");
	}

}

$waitress = new Waitress(new PizzaMan());
$customer = new Customer($waitress);
$customer->act();
$customer->act();
$customer->act();
$customer->act();

$waitress->flushOrders();
