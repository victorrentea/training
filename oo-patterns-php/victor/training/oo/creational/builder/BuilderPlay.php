<?php
namespace training\oo\creational\builder;

include "Customer.php";
include "Address.php";

// SOLUTION (
$customer = (new Customer())
    ->setName("John Doe")
    ->setLabels(array("Label1"))
    ->setAddress((new Address())
        ->setStreetName("Viorele")
        ->setStreetNumber(4)
        ->setCity("Bucharest"));
// SOLUTION )


$customer = new Customer();
$customer->setName("John Doe");
$labels = [];
$labels []= "Label1";
$customer->setLabels($labels);
$address = new Address();
$address->setStreetName("Viorele");
$address->setStreetNumber(4);
$address->setCity("Bucharest");
$customer->setAddress($address);
