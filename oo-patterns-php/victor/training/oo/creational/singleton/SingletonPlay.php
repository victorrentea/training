<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/17/2017
 * Time: 7:51 PM
 */

namespace victor\training\oo\behavioral\singleton;
include('AppConfiguration.php');


printf('Configuration setting a = ' . (new AppConfiguration())->getProperty('a') . "\n");
printf('Configuration setting b = ' . (new AppConfiguration())->getProperty('b'). "\n");
printf('Configuration setting a = ' . (new AppConfiguration())->getProperty('a') . "\n");