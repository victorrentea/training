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
        if (!($cube instanceof LegoCube)) {
            $this->throwMismatchException();
        }
    }

    function stickIn(Board $board)
    {
        printf("Sticking in " . $board);
        if (!($board instanceof LegoBoard)) {
            $this->throwMismatchException();
        }

    }

    function __toString()
    {
        return "Lego Cube";
    }

    private function throwMismatchException(): void
    {
        throw new \RuntimeException("The LEGO cube is NOT compatible with any other brand");
    }
}