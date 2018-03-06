<?php // SOLUTION
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/19/2017
 * Time: 12:08 PM
 */

namespace victor\training\oo\creational\abstractfactory\megablock;


use victor\training\oo\creational\abstractfactory\spi\Board;

class MegaBlockBoard
//{} // INITIAL
// SOLUTION (
    implements Board
{


    function __toString()
    {
        return "MegaBlock Board";
    }
}
// SOLUTION )