<?php // SOLUTION
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/19/2017
 * Time: 12:06 PM
 */

namespace victor\training\oo\creational\abstractfactory\megablock;


use victor\training\oo\creational\abstractfactory\spi\Board;
use victor\training\oo\creational\abstractfactory\spi\Cube;

class MegaBlockCube
//{} // INITIAL
// SOLUTION (
    implements Cube
{

    function stackOnto(Cube $cube)
    {
        echo "Stacking onto cube " . $cube;
    }

    function stickIn(Board $board)
    {
        echo "Stickin into board " . $board;
    }

    function __toString()
    {
        return "MegaBlock Cube";
    }
}
// SOLUTION )
