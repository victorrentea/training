<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 10:37 PM
 */

namespace victor\training\oo\structural\adapter\domain;


use victor\training\oo\structural\adapter\external\LdapUserWebServiceClient;

foreach (glob("../external/*.php") as $filename) require_once $filename;
include "User.php";
include "LdapUserWSAdapter.php"; // SOLUTION

class UserService
{
    private $wsClient;

    public function __construct(LdapUserWebServiceClient $wsClient)
    {
        $this->wsClient = $wsClient;
    }

    public function importUserFromLdap(string $username)
    {
        $list = $this->wsClient->search(strtoupper($username), null, null);
        if (count($list) !== 1)
        {
            throw new \Exception('There is no single user matching username ' . $username);
        }

        $ldapUser = $list[0];
        $fullName = $ldapUser->getfName() . ' ' . strtoupper($ldapUser->getlName());
        $user = new User();
        $user->setUsername($ldapUser->getUId());
        $user->setFullName($fullName);
        $user->setWorkEmail($ldapUser->getWorkEmail());

        if ($user->getWorkEmail() !== null) {
            printf('Send welcome email to ' . $user->getWorkEmail() . "\n");
        }
        printf("Insert user in my database\n");
    }

    public function searchUserInLdap(string $username) {
        $list = $this->wsClient->search(strtoupper($username), null, null);
        $results = array();
        foreach ($list as $ldapUser) {
            $fullName = $ldapUser->getfName() . ' ' . strtoupper($ldapUser->getlName());
            $user = new User();
            $user->setUsername($ldapUser->getUId());
            $user->setFullName($fullName);
            $user->setWorkEmail($ldapUser->getWorkEmail());
            $results[] = $user;
        }
        return $results;
    }

}

$userService = new UserService(new LdapUserWebServiceClient()); // INITIAL

printf(implode(",",$userService->searchUserInLdap("jdoe")) . "\n");
$userService->importUserFromLdap('jdoe');