<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 11:34 PM
 */

namespace victor\training\oo\behavioral\command;


class PizzaMan
{

    public function bakePizza(string $pizzaType, string $crustType)
    {
        printf(" Baking pizza " . $pizzaType . " with crust " . $crustType . " ...");
        sleep(1);
        printf("Done!");
    }

}