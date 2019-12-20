<?php


namespace video\clean;


class ManyParamsVsVO
{
    public function placeOrder(string $fname, string $lname, string $city, string $streetName, int $streetNumber)
    {
        if ($fname === '' || $lname === '') {
            throw new \Exception();
        }
        echo "Some logic \n";
    }
}

(new ManyParamsVsVO())->placeOrder('John', 'Doe', 'St. Albergue', 'Paris', 99);

class AnotherClass {
    public function otherMethod(string $firstName, string $lastName, int $x) {
    	if ($firstName === '' || $lastName === null) throw new \Exception();

    	echo "Another distant Logic";
    }
}

class Person {
    private $id;
    private $firstName;
    private $lastName;
    private $phone;

    public function __construct(string $firstName, string $lastName)
    {
        $this->firstName = $firstName;
        $this->lastName = $lastName;
        if ($firstName === '' || $lastName === '') throw new \Exception();
    }

    public function getFirstName(): string
    {
        return $this->firstName;
    }

    public function setFirstName(string $firstName): void
    {
        $this->firstName = $firstName;
    }

    public function getLastName(): string
    {
        return $this->lastName;
    }

    public function setLastName(string $lastName): void
    {
        $this->lastName = $lastName;
    }

}

class PersonService {
    public function f(Person $person) {
        $fullName = $person->getFirstName() . ' ' . strtoupper($person->getLastName());
        echo $fullName;
    }
    public function p(string $city, string $streetName, int $streetNumber) {
        echo "Living in " . $city . " on St. " . $streetName . " " . $streetNumber;
    }
}