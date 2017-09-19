<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 11:54 PM
 */

namespace victor\training\oo\behavioral\strategy;

include "TaxCalculator.php";
include "UKTaxCalculator.php";
include "ChinaTaxCalculator.php";
include "EUTaxCalculator.php";

class CustomsService
{
    /** @var TaxCalculato[r] */
    private static $ALL_TAX_CALCULATORS;

    function __construct()
    {
        self::$ALL_TAX_CALCULATORS = [new EUTaxCalculator(), new ChinaTaxCalculator(), new UKTaxCalculator()];
    }



    public function computeAddedCustomsTax(string $originCountry, float $tobaccoValue, float $otherValue): float { // UGLY API we CANNOT change
		return $this->determineTaxCalculator($originCountry)
            ->computeTax($tobaccoValue, $otherValue);
    }

    private function determineTaxCalculator(string $originCountry): TaxCalculator
    {
        foreach (self::$ALL_TAX_CALCULATORS as $taxCalculator) {
            if ($taxCalculator->canProcess($originCountry)) {
                return $taxCalculator;
            }
        }
        throw new \Exception("Not a valid country ISO2 code: " . $originCountry);
    }
}


$customsService = new CustomsService();
printf("Tax for (RO,100,100) = " . $customsService->computeAddedCustomsTax("RO", 100, 100) . "\n");
printf("Tax for (CH,100,100) = " . $customsService->computeAddedCustomsTax("CH", 100, 100). "\n");
printf("Tax for (UK,100,100) = " . $customsService->computeAddedCustomsTax("UK", 100, 100). "\n");
