<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 12:40 AM
 */

namespace victor\training\oo\creational\abstractfactory\lego;


use victor\training\oo\creational\abstractfactory\spi\BlockFactory;
use victor\training\oo\creational\abstractfactory\spi\Board;
use victor\training\oo\creational\abstractfactory\spi\Cube;

class LegoFactory implements BlockFactory
{

    function createCube(): Cube
    {
        return new LegoCube();
    }

    function createBoard(): Board
    {
        return new LegoBoard();
    }

    function __toString()
    {
        return "Lego Factory";
    }
}