<?php
namespace training\oo\creational\builder;

include "Customer.php";
include "Address.php";


$customer = new Customer();
$customer->setName('John Doe');
$labels = [];
$labels []= 'Label1';
$customer->setLabels($labels);
$address = new Address();
$address->setStreetName('Viorele');
$address->setStreetNumber(4);
$address->setCity('Bucharest');
$customer->setAddress($address);
