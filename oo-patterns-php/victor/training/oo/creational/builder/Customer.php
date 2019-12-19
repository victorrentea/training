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

    /**
     * @return String
     */
    public function getName(): String
    {
        return $this->name;
    }

    /**
     * @param String $name
     */
    public function setName(String $name): void
    {
        $this->name = $name;
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
     */
    public function setPhone(String $phone): void
    {
        $this->phone = $phone;
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
     */
    public function setLabels(array $labels): void
    {
        $this->labels = $labels;
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
     */
    public function setAddress(Address $address): void
    {
        $this->address = $address;
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
     */
    public function setCreateDate(\DateTime $createDate): void
    {
        $this->createDate = $createDate;
    }

}