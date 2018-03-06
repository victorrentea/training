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
include "LdapUserWSAdapter.php";

class UserService
{
    // SOLUTION (
    private $adapter;

    public function __construct(LdapUserWSAdapter $adapter)
    {
        $this->adapter = $adapter;
    }

    public function importUserFromLdap(string $username)
    {
        $users = $this->adapter->searchByUsername($username);
        if (count($users) != 1) {
            throw new \Exception("There is no single user matching username " . $username);
        }
        $user = $users[0];
        if ($user->getWorkEmail() != null) {
            printf("Send welcome email to " . $user->getWorkEmail() . "\n");
        }
        printf("Insert user in my database\n");
    }

    public function searchUserInLdap(string $username)
    {
        return $this->adapter->searchByUsername($username);
    }
    // SOLUTION )

    // INITIAL (
//    private $wsClient;
//
//    public function __construct(LdapUserWebServiceClient $wsClient)
//    {
//        $this->wsClient = $wsClient;
//    }
//
//    public function importUserFromLdap(string $username)
//    {
//        $list = $this->wsClient->search(strtoupper($username), null, null);
//        if (count($list) != 1)
//        {
//            throw new \Exception("There is no single user matching username " . $username);
//        }
//
//        $ldapUser = $list[0];
//        $fullName = $ldapUser->getfName() . " " . strtoupper($ldapUser->getlName());
//        $user = new User();
//        $user->setUsername($username);
//        $user->setFullName($fullName);
//        $user->setWorkEmail($ldapUser->getWorkEmail());
//
//        if ($user->getWorkEmail() != null) {
//            printf("Send welcome email to " . $user->getWorkEmail() . "\n");
//        }
//        printf("Insert user in my database\n");
//    }
//
//    public function searchUserInLdap(string $username) {
//        $list = $this->wsClient->search(strtoupper($username), null, null);
//        $results = array();
//        foreach ($list as $ldapUser) {
//            $fullName = $ldapUser->getfName() . " " . strtoupper($ldapUser->getlName());
//            $user = new User();
//            $user->setUsername($username);
//            $user->setFullName($fullName);
//            $user->setWorkEmail($ldapUser->getWorkEmail());
//            array_push($results, $user);
//        }
//        return $results;
//    }
// INITIAL )

}

//$userService = new UserService(new LdapUserWebServiceClient()); // INITIAL

$adapter = new LdapUserWSAdapter(new LdapUserWebServiceClient()); // SOLUTION
$userService = new UserService($adapter); // SOLUTION
printf(implode(",",$userService->searchUserInLdap("jdoe")) . "\n");
$userService->importUserFromLdap("jdoe");