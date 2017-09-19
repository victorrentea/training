<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/19/2017
 * Time: 5:05 PM
 */

namespace victor\training\oo\behavioral\strategy;

class EUTaxCalculator implements TaxCalculator
{
    function computeTax(float $tobaccoValue, float $otherValue = 0): float
    {
        return $tobaccoValue / 3;
    }

    function canProcess(string $originCountry): bool
    {
        return in_array($originCountry, array("FR", "ES", "RO"));
    }
}