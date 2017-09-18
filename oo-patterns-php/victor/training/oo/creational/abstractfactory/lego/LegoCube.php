<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 12:40 AM
 */

namespace victor\training\oo\creational\abstractfactory\lego;


use victor\training\oo\creational\abstractfactory\spi\Board;
use victor\training\oo\creational\abstractfactory\spi\Cube;

class LegoCube implements Cube
{

    function stackOnto(Cube $cube)
    {
        printf("Stacking onto cube " . $cube);
    }

    function stickIn(Board $board)
    {
        printf("Sticking in " . $board);
    }

    function __toString()
    {
        return "Lego Cube";
    }
}