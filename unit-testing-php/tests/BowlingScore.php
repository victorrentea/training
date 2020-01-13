<?php


namespace PhpUnitWorkshopTest;


class BowlingScore
{

    const SPARE = '/';
    const STRIKE = 'X';

    public static function calculateScore(string $string): int
    {
        $result = 0;


        for ($i=0; $i<strlen($string); $i+=2) {
            if ($string[$i+1] == static::SPARE) {
                $result += 10 + self::parse($string, $i + 2);

            } elseif ($string[$i] == static::STRIKE) {
                $result += 10 + self::parseFrame($string, $i + 1);
                $i--;
            } else {
                $result += self::parseFrame($string, $i);
            }
        }
        return $result;
    }

    private static function parse(string $string, int $i): int
    {
        return (int)substr($string, $i, 1);
    }

    /**
     * @param string $string
     * @param int $i
     * @return int
     */
    private static function parseFrame(string $string, int $i): int
    {
        if ($string[$i+1] == static::SPARE) {
            return 10;
        } else {
            return self::parse($string, $i) +
                self::parse($string, $i + 1);
        }
    }


}