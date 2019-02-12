<?php
/**
 * Created by PhpStorm.
 * User: VictorRentea
 * Date: 08-Nov-18
 * Time: 03:21 PM
 */

namespace video;

$outputString = '';
function echoln($string) {
    global $outputString;
    $outputString .= $string."\n";
}

include 'ITrivia.php';
include 'Trivia.php';
include 'TriviaBetter.php';

class TriviaTest extends \PHPUnit\Framework\TestCase
{

    public function testX()
    {
        for ($seed = 0; $seed < 50; $seed ++) {
            $expectedOutput = $this->runGame(new Trivia(), $seed);
            $actualOutput = $this->runGame(new TriviaBetter(), $seed);
            $this->assertEquals($expectedOutput, $actualOutput);
        }
    }

    function runGame(ITrivia $aGame, int $seed): string
    {
        global $outputString;
        $outputString = '';
        echoln("------------------------");

        $aGame->add("Chet");
        $aGame->add("Pat");
        $aGame->add("Sue");

        srand($seed);
        do {
            $aGame->roll(rand(0, 5) + 1);
            if (rand(0, 9) == 7) {
                $notAWinner = $aGame->wrongAnswer();
            } else {
                $notAWinner = $aGame->wasCorrectlyAnswered();
            }
        } while ($notAWinner);
        return $outputString;
    }
}