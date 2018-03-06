<?php // SOLUTION
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/19/2017
 * Time: 1:04 PM
 */

namespace victor\training\oo\structural\decorator;


interface IExpensiveMath
{

    function getNextPrimeAfter(int $number): int;

    function isPrime(int $number): bool;
}