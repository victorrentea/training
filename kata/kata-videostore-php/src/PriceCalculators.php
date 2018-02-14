<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 07-Feb-18
 * Time: 03:22 PM
 */

namespace video;


class PriceCalculators
{
    static public function calculateRegularPrice(int $daysRented): float
    {
        $price = 2;
        if ($daysRented > 2) {
            $price += ($daysRented - 2) * 1.5;
        }
        return $price;
    }

    static public function calculateChildrensPrice(int $daysRented): float
    {
        $price = 1.5;
        if ($daysRented > 3) {
            $price += ($daysRented - 3) * 1.5;
        }
        return $price;
    }

    static public function calculateNewReleasePrice(int $daysRented): int
    {
        return $daysRented * 3;
    }
}