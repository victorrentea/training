<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 01-Aug-18
 * Time: 12:55 PM
 */

namespace PhpUnitWorkshop\mocks;


class Random
{
    // random seed
    private $RSeed = 0;

    public function __construct(int $RSeed)
    {
        $this->RSeed = $RSeed;
    }

// set seed
    public function seed($s = 0) {
        $this->RSeed = abs(intval($s)) % 9999999 + 1;
        self::nextIntInRange();
    }
    // generate random number
    public function nextIntInRange($min = 0, $max = 9999999) {
        if ($this->RSeed == 0) self::seed(mt_rand());
        $this->RSeed = ($this->RSeed * 125) % 2796203;
        return $this->RSeed % ($max - $min + 1) + $min;
    }

    public function nextInt($max)
    {
        return $this->nextIntInRange(0, $max);
    }
}