<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 11:54 PM
 */

namespace victor\training\oo\behavioral\strategy;

include "TaxCalculator.php";

class CustomsService
{

    public function computeAddedCustomsTax(string $originCountry, float $tobaccoValue, float $otherValue): float { // UGLY API we CANNOT change
		return $this->determineTaxCalculator($originCountry)
            ->computeTax($tobaccoValue, $otherValue);
    }

    private function determineTaxCalculator(string $originCountry): TaxCalculator
    {
        switch ($originCountry) {
            case "UK": return new UKTaxCalculator();
            case "CH": return new ChinaTaxCalculator();
            case "FR":
            case "ES": // other EU country codes...
            case "RO": return new EUTaxCalculator();
            default: throw new \Exception("Not a valid country ISO2 code: " . $originCountry);
        }
    }
}

class UKTaxCalculator implements TaxCalculator {
    function computeTax(float $tobaccoValue, float $otherValue = 0): float
    {
        return $tobaccoValue/2 + $otherValue/2;
    }
}
class ChinaTaxCalculator implements TaxCalculator {
    function computeTax(float $tobaccoValue, float $otherValue = 0): float
    {
        return $tobaccoValue + $otherValue;
    }
}
class EUTaxCalculator implements TaxCalculator {
    function computeTax(float $tobaccoValue, float $otherValue = 0): float
    {
        return $tobaccoValue/3;
    }
}



$customsService = new CustomsService();
printf("Tax for (RO,100,100) = " . $customsService->computeAddedCustomsTax("RO", 100, 100) . "\n");
printf("Tax for (CH,100,100) = " . $customsService->computeAddedCustomsTax("CH", 100, 100). "\n");
printf("Tax for (UK,100,100) = " . $customsService->computeAddedCustomsTax("UK", 100, 100). "\n");
