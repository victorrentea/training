<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 10:35 PM
 */

namespace victor\training\oo\structural\adapter\external;

class LdapUserWebServiceClient
{
    /* @return LdapUser[] */
    public function search(?string $uId, ?string $fName, ?string $lName) {
        // Imagine a search URL is formed here and a GET is then performed
        // Then, the response JSON list is converted to LdapUser objects
        return DummyData::getData();
    }

}