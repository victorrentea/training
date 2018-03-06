<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 1:36 AM
 */

namespace training\oo\creational\builder;


class Address
{
    /* @var String */
    private $streetName;

    /* @var Integer */
    private $streetNumber;

    /* @var String */
    private $city;

    /* @var String */
    private $country;

    // INITIAL (
//    public function getStreetName(): String
//    {
//        return $this->streetName;
//    }
//
//    public function setStreetName(String $streetName): void
//    {
//        $this->streetName = $streetName;
//    }
//
//    public function getStreetNumber(): int
//    {
//        return $this->streetNumber;
//    }
//
//    public function setStreetNumber(int $streetNumber): void
//    {
//        $this->streetNumber = $streetNumber;
//    }
//
//    public function getCity(): String
//    {
//        return $this->city;
//    }
//
//    public function setCity(String $city): void
//    {
//        $this->city = $city;
//    }
//
//    public function getCountry(): String
//    {
//        return $this->country;
//    }
//
//    public function setCountry(String $country): void
//    {
//        $this->country = $country;
//    }
    // INITIAL )

    // SOLUTION (
    public function getStreetName(): ?String
{
    return $this->streetName;
}

    public function setStreetName(?String $streetName): Address
    {
        $this->streetName = $streetName;
        return $this;
    }

    public function getStreetNumber(): ?int
    {
        return $this->streetNumber;
    }

    public function setStreetNumber(?int $streetNumber): Address
    {
        $this->streetNumber = $streetNumber;
        return $this;
    }

    public function getCity(): ?String
    {
        return $this->city;
    }

    public function setCity(?String $city): Address
    {
        $this->city = $city;
        return $this;
    }

    public function getCountry(): ?String
    {
        return $this->country;
    }

    public function setCountry(?String $country): Address
    {
        $this->country = $country;
        return $this;
    }
    // SOLUTION )


}