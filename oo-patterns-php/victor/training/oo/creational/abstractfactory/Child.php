<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 12:43 AM
 */

namespace victor\training\oo\creational\abstractfactory;


use victor\training\oo\creational\abstractfactory\spi\BlockFactory;

class Child
{
    public function playWith(BlockFactory $cutie)
    {
        $board = $cutie->createBoard();
        $previousCube = $cutie->createCube();
        printf("Pun primul cub " . $previousCube . " pe tabla " . $board . "\n");
        for ($i = 2; $i <= 10; $i++) {
            $cube = $cutie->createCube();
            printf("\"Iau cubul " . i . ": " . $cube . " si il pun peste " . $previousCube . "\n");
            $cube->stackOnto($previousCube);
            $previousCube = $cube;
        }
        printf("Gata. Asta voi face inca 6 luni de acum inainte... ;p\n");
    }
}