<?php
/**
 * Created by PhpStorm.
 * User: VictorRentea
 * Date: 08-Nov-18
 * Time: 03:36 PM
 */

namespace video;


interface ITrivia
{

    function add(string $playerName);

    function roll(int $roll);

    function wasCorrectlyAnswered();

    function wrongAnswer();
}