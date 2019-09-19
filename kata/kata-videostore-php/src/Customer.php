<?php

namespace video;

class Customer
{
    private $name;
    /**
     * @var Rental[]
     */
    private $rentals = [];

    public function __construct(string $name)
    {
        $this->name = $name;
    }

    public function addRental(Rental $rental)
    {
        $this->rentals[] = $rental;
    }


    /**
     * @return string
     */
    public function getName()
    {
        return $this->name;
    }

    /**
     * @return Rental[]
     */
    public function getRentals(): array
    {
        return $this->rentals;
    }
}