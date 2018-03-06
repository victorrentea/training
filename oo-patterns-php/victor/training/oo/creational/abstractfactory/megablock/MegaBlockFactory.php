<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 1:27 AM
 */

namespace victor\training\oo\creational\abstractfactory\megablock;


use victor\training\oo\creational\abstractfactory\spi\BlockFactory;
use victor\training\oo\creational\abstractfactory\spi\Board;
use victor\training\oo\creational\abstractfactory\spi\Cube;

class MegaBlockFactory
//{} // INITIAL
// SOLUTION (
    implements BlockFactory
{

    function createCube(): Cube
    {
        return new MegaBlockCube();
    }

    function createBoard(): Board
    {
        return new MegaBlockBoard();
    }

    function __toString()
    {
        return "MegaBlock Factory";
    }
}
// SOLUTION )