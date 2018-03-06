<?php // SOLUTION
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/19/2017
 * Time: 12:51 PM
 */

namespace victor\training\oo\structural\adapter\domain;


use victor\training\oo\structural\adapter\external\LdapUserWebServiceClient;

class LdapUserWSAdapter
{

    private $wsClient;

    public function __construct(LdapUserWebServiceClient $wsClient)
    {
        $this->wsClient = $wsClient;
    }

    /**
     * @return User[]
     */
    public function searchByUsername(string $username) {
        $ldapUsers = $this->wsClient->search(strtoupper($username), null, null);

        $results = array();
        foreach ($ldapUsers as $ldapUser) {
            $fullName = $ldapUser->getfName() . " " . strtoupper($ldapUser->getlName());
            $user = new User();
            $user->setUsername($username);
            $user->setFullName($fullName);
            $user->setWorkEmail($ldapUser->getWorkEmail());
            array_push($results, $user);
        }
        return $results;
    }

}