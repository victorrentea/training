<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 12:17 AM
 */

namespace victor\training\oo\creational\abstractfactory\spi;


interface BlockFactory
{
    function createCube(): Cube;
    function createBoard(): Board;
    function __toString();
}