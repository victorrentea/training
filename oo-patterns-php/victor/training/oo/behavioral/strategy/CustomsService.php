<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 11:54 PM
 */

namespace victor\training\oo\behavioral\strategy;

class CustomsService
{

    public function computeAddedCustomsTax(string $originCountry, float $tobaccoValue, float $otherValue): float { // UGLY API we CANNOT change
        switch ($originCountry) {
            case 'UK': return $tobaccoValue/2 + $otherValue/2;
            case 'CH': return $tobaccoValue + $otherValue;
            case 'FR':
            case 'ES': // other EU country codes...
            case 'RO': return $tobaccoValue/3;
            default: throw new \RuntimeException("Not a valid country ISO2 code: {$originCountry}");
        }
    }

}


$customsService = new CustomsService();
printf('Tax for (RO,100,100) = ' . $customsService->computeAddedCustomsTax("RO", 100, 100) . '\n');
printf('Tax for (CH,100,100) = ' . $customsService->computeAddedCustomsTax("CH", 100, 100) . '\n');
printf('Tax for (UK,100,100) = ' . $customsService->computeAddedCustomsTax("UK", 100, 100) . '\n');
