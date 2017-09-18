<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 12:31 AM
 */

namespace victor\training\oo\creational\abstractfactory\spi;


interface Cube
{
    function stackOnto(Cube $cube);
    function stickIn(Board $board);
    function __toString();
}