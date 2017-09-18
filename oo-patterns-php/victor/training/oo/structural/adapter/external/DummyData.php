<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 10:30 PM
 */

namespace victor\training\oo\structural\adapter\external;


class DummyData
{
    /* @return LdapUser[] */
    public static function getData() {
        $ldapUser1 = (new LdapUser())
            ->setfName("John")
            ->setlName("DOE")
            ->setuId("jdoe")
            ->setCreationDate(new \DateTime())
            ->setWorkEmail("0123456789")
            ->setEmailAddresses(array(new LdapUserPhone("WORK", "0123456789")));
        $ldapUser2 = (new LdapUser())
            ->setfName("Jane")
            ->setlName("DOE")
            ->setuId("janedoe")
            ->setCreationDate(new \DateTime());
//        return array($ldapUser1, $ldapUser2);
        return array($ldapUser1);
    }
}