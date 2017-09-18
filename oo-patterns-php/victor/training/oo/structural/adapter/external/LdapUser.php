<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 10:25 PM
 */

namespace victor\training\oo\structural\adapter\external;


class LdapUser
{
    /* @var string */
    private $fName;
    /* @var string */
    private $lName;
    /* @var \DateTime */
    private $creationDate;
    /* @var string */
    private $uId;
    /* @var ?string */
    private $workEmail;
    /* @var LdapUserPhone[] */
    private $emailAddresses = array();

    /**
     * @return string
     */
    public function getFName(): string
    {
        return $this->fName;
    }

    /**
     * @param string $fName
     * @return LdapUser
     */
    public function setFName(string $fName): LdapUser
    {
        $this->fName = $fName;
        return $this;
    }

    /**
     * @return string
     */
    public function getLName(): string
    {
        return $this->lName;
    }

    /**
     * @param string $lName
     * @return LdapUser
     */
    public function setLName(string $lName): LdapUser
    {
        $this->lName = $lName;
        return $this;
    }

    /**
     * @return \DateTime
     */
    public function getCreationDate(): \DateTime
    {
        return $this->creationDate;
    }

    /**
     * @param \DateTime $creationDate
     * @return LdapUser
     */
    public function setCreationDate(\DateTime $creationDate): LdapUser
    {
        $this->creationDate = $creationDate;
        return $this;
    }

    /**
     * @return string
     */
    public function getUId(): string
    {
        return $this->uId;
    }

    /**
     * @param string $uId
     * @return LdapUser
     */
    public function setUId(string $uId): LdapUser
    {
        $this->uId = $uId;
        return $this;
    }

    /**
     * @return mixed
     */
    public function getWorkEmail()
    {
        return $this->workEmail;
    }

    /**
     * @param mixed $workEmail
     * @return LdapUser
     */
    public function setWorkEmail($workEmail)
    {
        $this->workEmail = $workEmail;
        return $this;
    }

    /**
     * @return LdapUserPhone[]
     */
    public function getEmailAddresses(): array
    {
        return $this->emailAddresses;
    }

    /**
     * @param LdapUserPhone[] $emailAddresses
     * @return LdapUser
     */
    public function setEmailAddresses(array $emailAddresses): LdapUser
    {
        $this->emailAddresses = $emailAddresses;
        return $this;
    }


}