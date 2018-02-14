<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 07-Feb-18
 * Time: 04:02 PM
 */


class FizzBuzz
{

    const DIVIDER_TO_TEXT = [
        3 => 'Fizz',
        5 => 'Buzz',
        7 => 'Wizz'
    ];

    const DIGIT_TO_TEXT = [
        3 => 'Fizz',
        5 => 'Buzz'
    ];

    public static function getStringForInt(int $i): string
    {
        if ($i <= 0) {
            throw new Exception();
        }

        $pieces = [];

        foreach (self::DIGIT_TO_TEXT as $digit => $text) {
            if (preg_match("/$digit/", (string)$i)) {
                $pieces[$digit] = $text;
            }
        }

        foreach (self::DIVIDER_TO_TEXT as $divider => $text) {
            if ($i % $divider == 0) {
                $pieces[$divider] = $text;
            }
        }

        ksort($pieces);
        if (!empty($pieces)) {
            return implode(' ', $pieces);
        }

        return (string)$i;
    }
}