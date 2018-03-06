<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 1:31 AM
 */

namespace training\oo\creational\builder;


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

    public function getName(): String
    {
        return $this->name;
    }

    public function setName(String $name): Customer
    {
        $this->name = $name;
        return $this;
    }

    public function getPhone(): String
    {
        return $this->phone;
    }

    public function setPhone(String $phone): Customer
    {
        $this->phone = $phone;
        return $this;
    }

    public function getLabels(): array
    {
        return $this->labels;
    }

    public function setLabels(array $labels): Customer
    {
        $this->labels = $labels;
        return $this;
    }

    public function getAddress(): ?Address
    {
        return $this->address;
    }

    public function setAddress(?Address $address): Customer
    {
        $this->address = $address;
        return $this;
    }

    public function getCreateDate(): \DateTime
    {
        return $this->createDate;
    }

    public function setCreateDate(\DateTime $createDate): Customer
    {
        $this->createDate = $createDate;
        return $this;
    }

}