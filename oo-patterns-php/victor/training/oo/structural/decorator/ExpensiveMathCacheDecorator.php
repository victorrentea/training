<?php // SOLUTION
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/19/2017
 * Time: 1:05 PM
 */

namespace victor\training\oo\structural\decorator;

class ExpensiveMathCacheDecorator implements IExpensiveMath
{

    /**
     * @var IExpensiveMath
     */
    private $delegate;

    public function __construct($delegate)
    {
        $this->delegate = $delegate;
    }


    private $cache = array();


//    private function getKey(string $methodName, int $number): string {
//        return $methodName . $number;
//    }

    function getNextPrimeAfter(int $number): int
    {
        if (!isset($this->cache[$number])) {
            $this->cache[$number] = $this->delegate->getNextPrimeAfter($number);
        }
        return $this->cache[$number];
    }

    function isPrime(int $number): bool
    {
        if (!isset($this->cache[$number])) {
            $this->cache[$number] = $this->delegate->isPrime($number);
        }
        return $this->cache[$number];
    }
}