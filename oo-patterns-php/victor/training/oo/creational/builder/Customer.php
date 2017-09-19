<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 1:31 AM
 */

namespace victor\training\oo\creational\builder;


class Customer
{
    /* @var String */
    private $name;

    /* @var String */
    private $phone;

    /* @var String[] */
    private $labels = array();

    /* @var Address */
    private $address;

    /* @var \DateTime */
    private $createDate;

    public function __construct()
    {
        $this->labels = [];
    }

    public function addLabel(string $label): Customer {
        $this->labels[] = $label;
        return $this;
    }

    /**
     * @return String
     */
    public function getName(): String
    {
        return $this->name;
    }

    /**
     * @param String $name
     * @return Customer
     */
    public function setName(String $name): Customer
    {
        $this->name = $name;
        return $this;
    }

    /**
     * @return String
     */
    public function getPhone(): String
    {
        return $this->phone;
    }

    /**
     * @param String $phone
     * @return Customer
     */
    public function setPhone(String $phone): Customer
    {
        $this->phone = $phone;
        return $this;
    }

    /**
     * @return String[]
     */
    public function getLabels(): array
    {
        return $this->labels;
    }

    /**
     * @param String[] $labels
     * @return Customer
     */
    public function setLabels(array $labels): Customer
    {
        $this->labels = $labels;
        return $this;
    }

    /**
     * @return Address
     */
    public function getAddress(): Address
    {
        return $this->address;
    }

    /**
     * @param Address $address
     * @return Customer
     */
    public function setAddress(Address $address): Customer
    {
        $this->address = $address;
        return $this;
    }

    /**
     * @return \DateTime
     */
    public function getCreateDate(): \DateTime
    {
        return $this->createDate;
    }

    /**
     * @param \DateTime $createDate
     * @return Customer
     */
    public function setCreateDate(\DateTime $createDate): Customer
    {
        $this->createDate = $createDate;
        return $this;
    }



}