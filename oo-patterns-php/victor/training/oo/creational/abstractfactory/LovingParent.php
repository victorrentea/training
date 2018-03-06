<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 12:42 AM
 */

namespace victor\training\oo\creational\abstractfactory;

use victor\training\oo\creational\abstractfactory\lego\LegoFactory;
use victor\training\oo\creational\abstractfactory\megablock\MegaBlockFactory;

foreach (glob("*.php") as $filename) require_once $filename;
foreach (glob("spi/*.php") as $filename) require_once $filename;
foreach (glob("lego/*.php") as $filename) require_once $filename;
foreach (glob("megablock/*.php") as $filename) require_once $filename;



$childOne = new Child();

printf("Got back from work...\n");
//$factory = new LegoFactory(); // 100 RON // INITIAL
$factory = new MegaBlockFactory(); // 20 RON // SOLUTION
printf("Brought you a present: ${factory}\n");
printf("Hm....\n");

$childOne->playWith($factory);

printf("Good Night!\n");