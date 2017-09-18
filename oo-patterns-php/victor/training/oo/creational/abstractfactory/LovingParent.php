<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 12:42 AM
 */

namespace victor\training\oo\creational\abstractfactory;

use victor\training\oo\creational\abstractfactory\lego\LegoFactory;

foreach (glob("*.php") as $filename) require_once $filename;
foreach (glob("spi/*.php") as $filename) require_once $filename;
foreach (glob("lego/*.php") as $filename) require_once $filename;



$childOne = new Child();

printf("Got back from work...\n");
$factory = new LegoFactory();
printf("Brought a present: " . $factory . " \n");
printf("Hm....\n");
$childOne->playWith($factory);
printf("Good Night!\n");