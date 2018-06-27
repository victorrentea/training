<?php

namespace PhpUnitWorkshop;

class Customer
{
    private $name;

    /** @var Rental[] */
    private $rentals = [];

    public function __construct(string $name)
    {
        $this->name = $name;
    }

    public function addRental(Rental $rental)
    {
        $this->rentals[] = $rental;
    }

    public function getName(): string
    {
        return $this->name;
    }
}
