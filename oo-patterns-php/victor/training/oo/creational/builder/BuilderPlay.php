<?php
namespace training\oo\creational\builder;

include "Customer.php";
include "Address.php";

$customer = (new Customer())
    ->setName("John Doe")
    ->setLabels(array("Label1"))
    ->setAddress((new Address())
        ->setStreetName("Viorele")
        ->setStreetNumber(4)
        ->setCity("Bucharest"));