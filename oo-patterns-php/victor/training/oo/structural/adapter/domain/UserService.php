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
        if (count($users) != 1)
        {
            throw new \Exception("There is no single user matching username " . $username);
        }
		$user = $users[0];
		if ($user->getWorkEmail() != null) {
            printf("Send welcome email to " . $user->getWorkEmail() . "\n");
        }
		printf("Insert user in my database\n");
	}

	public function searchUserInLdap(string $username) {
        return $this->adapter->searchByUsername($username);
	}
	// SOLUTION )
}

$adapter = new LdapUserWSAdapter(new LdapUserWebServiceClient()); // SOLUTION
$userService = new UserService($adapter); // SOLUTION
printf(implode(",",$userService->searchUserInLdap("jdoe")) . "\n");
$userService->importUserFromLdap("jdoe");