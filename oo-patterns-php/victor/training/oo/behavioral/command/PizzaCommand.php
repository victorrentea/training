<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/19/2017
 * Time: 3:28 PM
 */

namespace victor\training\oo\behavioral\command;


class PizzaCommand implements Command
{
    /** @var  PizzaMan */
    private $pizzaMan;

    /** @var string */
    private $pizzaType;

    /** @var string */
    private $crustType;

    public function __construct(PizzaMan $pizzaMan, string $pizzaType, string $crustType)
    {
        $this->pizzaMan = $pizzaMan;
        $this->pizzaType = $pizzaType;
        $this->crustType = $crustType;
    }

    public function execute() {
        $this->pizzaMan->bakePizza($this->pizzaType, $this->crustType);
    }

    /**
     * @return PizzaMan
     */
    public function getPizzaMan(): PizzaMan
    {
        return $this->pizzaMan;
    }

    /**
     * @return string
     */
    public function getPizzaType(): string
    {
        return $this->pizzaType;
    }

    /**
     * @return string
     */
    public function getCrustType(): string
    {
        return $this->crustType;
    }




}