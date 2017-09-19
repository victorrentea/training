<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/19/2017
 * Time: 3:31 PM
 */

namespace victor\training\oo\behavioral\command;


class Waitress
{
    /** @var Command[] */
    private $carnetel = array();
    private $pizzaMan;

    public function __construct(PizzaMan $pizzaMan)
    {
        $this->pizzaMan = $pizzaMan;
    }


    public function orderPizza(string $pizzaType, string $crustType) {
        $this->carnetel[] = new PizzaCommand($this->pizzaMan, $pizzaType, $crustType);
    }
    public function orderBeer(string $beerType) {
        $this->carnetel[] = new BeerCommand($this->barman, $pizzaType, $crustType);
    }

    public function flushOrders() {
        foreach ($this->carnetel as $command) {
            $command->execute();
        }
    }



}