<?php

namespace PhpUnitWorkshopTest;

use Hamcrest\Text\StringContains;
use PHPUnit\Framework\Constraint\TraversableContains;
use PHPUnit\Framework\TestCase;

class DataProvider extends TestCase
{
    /**
     * @dataProvider dataSet
     */
    public function testCheckValue(int $n, string $expectedResult) {
        self::assertEquals($expectedResult,$this->fizzBuzz($n));
    }

    public function dataSet():array {
        return [
            '1 is 1' => [1, '1'],
            '2 is 2' => [2, '2'],
            '3 is Fizz' => [3, 'Fizz'],
            '5 is Buzz' => [5, 'Buzz'],
            '15 is Fizz Buzz' => [15, 'Fizz Buzz']
        ];
    }

    private function fizzBuzz(int $n): String {
        $arr = [];
        if ($n % 3 == 0) {
            $arr []= 'Fizz';
        }
        if ($n % 5 == 0) {
            $arr []= 'Buzz';
        }
        if (empty($arr)) {
            return ''.$n;
        } else {
            return join(' ', $arr);
        }
    }

}
