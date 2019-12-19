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

    /**
     * @return String
     */
    public function getStreetName(): String
    {
        return $this->streetName;
    }

    /**
     * @param String $streetName
     */
    public function setStreetName(String $streetName): void
    {
        $this->streetName = $streetName;
    }

    /**
     * @return int
     */
    public function getStreetNumber(): int
    {
        return $this->streetNumber;
    }

    /**
     * @param int $streetNumber
     */
    public function setStreetNumber(int $streetNumber): void
    {
        $this->streetNumber = $streetNumber;
    }

    /**
     * @return String
     */
    public function getCity(): String
    {
        return $this->city;
    }

    /**
     * @param String $city
     */
    public function setCity(String $city): void
    {
        $this->city = $city;
    }

    /**
     * @return String
     */
    public function getCountry(): String
    {
        return $this->country;
    }

    /**
     * @param String $country
     */
    public function setCountry(String $country): void
    {
        $this->country = $country;
    }



}