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
    private $labels;

    /* @var Address */
    private $address;

    /* @var \DateTime */
    private $createDate;

    public function __construct()
    {
        $this->labels = [];
    }

    public function getName(): ?String
    {
        return $this->name;
    }
    public function setName(String $name)
    {
        $this->name = $name;
    }

    public function getPhone(): String
    {
        return $this->phone;
    }

    public function setPhone(String $phone)
    {
        $this->phone = $phone;
    }

    public function getLabels(): array
    {
        return $this->labels;
    }

    public function setLabels(array $labels)
    {
        $this->labels = $labels;
    }

    public function getAddress(): Address
    {
        return $this->address;
    }

    public function setAddress(Address $address)
    {
        $this->address = $address;
    }

    public function getCreateDate(): \DateTime
    {
        return $this->createDate;
    }

    public function setCreateDate(\DateTime $createDate)
    {
        $this->createDate = $createDate;
    }

}