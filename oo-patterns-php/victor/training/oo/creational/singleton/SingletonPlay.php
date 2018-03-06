<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/17/2017
 * Time: 7:51 PM
 */

namespace victor\training\oo\behavioral\singleton;
include('AppConfiguration.php');


// INITIAL (
//printf("Configuration setting a = " . AppConfiguration::getInstance()->getProperty("a") . "\n");
//printf("Configuration setting b = " . AppConfiguration::getInstance()->getProperty("b"). "\n");
//printf("Configuration setting a = " . AppConfiguration::getInstance()->getProperty("a") . "\n");
// INITIAL )

// SOLUTION (
printf("Configuration setting a = " . AppConfiguration::getInstance()->getProperty("a") . "\n");
printf("Configuration setting b = " . AppConfiguration::getInstance()->getProperty("b"). "\n");
printf("Configuration setting a = " . AppConfiguration::getInstance()->getProperty("a") . "\n");

// SOLUTION )