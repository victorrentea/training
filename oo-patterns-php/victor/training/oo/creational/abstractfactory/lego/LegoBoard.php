<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 12:41 AM
 */

namespace victor\training\oo\creational\abstractfactory\lego;


use victor\training\oo\creational\abstractfactory\spi\Board;

class LegoBoard implements Board
{

    function __toString()
    {
        return "Lego Board";
    }
}