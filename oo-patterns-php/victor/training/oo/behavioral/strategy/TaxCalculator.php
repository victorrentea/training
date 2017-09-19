<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/19/2017
 * Time: 4:55 PM
 */

namespace victor\training\oo\behavioral\strategy;


interface TaxCalculator
{
    function canProcess(string $originCountry): bool;
    function computeTax(float $tobaccoValue, float $otherValue = 0): float;
}