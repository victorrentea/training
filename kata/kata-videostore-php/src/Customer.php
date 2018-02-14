<?php
namespace video;

class Customer
{
    private $name;

    /**
     * @var Rental[]
     */
    private $rentals = [];

    /**
     * @return Rental[]
     */
    public function getRentals(): array
    {
        return $this->rentals;
    }

    public function __construct(string $name) {
        $this->name = $name;
    }

	public function addRental (Rental $rental) {
        $this->rentals[] = $rental;
    }

	public function getName (): string
    {
		return $this->name;
	}

}