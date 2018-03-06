<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 11:10 PM
 */

namespace victor\training\oo\structural\decorator;

include "ExpensiveMath.php";
include "ExpensiveMathCacheDecorator.php";

// function testItAndIfPrimeGetNextOne(int $n, ExpensiveMath $math) { // INITIAL
function testItAndIfPrimeGetNextOne(int $n, IExpensiveMath $math) { // SOLUTION

        $isPrime = $math->isPrime($n);
    printf("isPrime(" . $n . "): " . ($isPrime ? "Yes" : "No")."\n");
    if ($isPrime) {
        printf("next prime after ".$n.": " . $math->getNextPrimeAfter($n + 1)."\n");
    }

}
$math = new ExpensiveMath();
$math = new ExpensiveMathCacheDecorator($math); // SOLUTION
printf("Start... \n");
testItAndIfPrimeGetNextOne(179426549, $math);
testItAndIfPrimeGetNextOne(179426549, $math);
