<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/19/2017
 * Time: 5:07 PM
 */

namespace victor\training\oo\behavioral\strategy;

class ChinaTaxCalculator implements TaxCalculator
{
    function computeTax(float $tobaccoValue, float $otherValue = 0): float
    {
        return $tobaccoValue + $otherValue;
    }

    function canProcess(string $originCountry): bool
    {
        return $originCountry === "CH";
    }
}