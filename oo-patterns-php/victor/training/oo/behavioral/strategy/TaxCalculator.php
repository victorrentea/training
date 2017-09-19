<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/19/2017
 * Time: 4:55 PM
 */

namespace victor\training\oo\behavioral\strategy;


abstract class TaxCalculator
{
    const ISO = "";

    function canProcess(string $originCountry): bool {
        return static::ISO === $originCountry;
    }
    abstract function computeTax(float $tobaccoValue, float $otherValue = 0): float;
}