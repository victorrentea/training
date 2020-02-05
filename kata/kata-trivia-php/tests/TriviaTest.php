<?php
/**
 * Created by PhpStorm.
 * User: VictorRentea
 * Date: 08-Nov-18
 * Time: 03:21 PM
 */

namespace video;

use PHPUnit\Framework\TestCase;

$outputString = '';
function echoln($string)
{
    global $outputString;
    $outputString .= $string . "\n";
}

//include 'Trivia.php';

class TriviaTest extends TestCase
{

    public function testX()
    {
        echo "------------------------" . "\n";

        for ($i = 0; $i<1000;$i ++) {
            $expectedString = $this->runGame(new TriviaOld(), $i);
            $refactoredString = $this->runGame(new Trivia(), $i);
        }
        self::assertEquals($expectedString, $refactoredString);
    }

    private function runGame(TriviaInterface $game, int $seed): string
    {
        global $outputString;
        $outputString = '';
        $game->add("Chet");
        $game->add("Pat");
        $game->add("Sue");

        srand($seed);
        do {
            $game->roll(rand(0, 5) + 1);
            if (rand(0, 9) == 7) {
                $notAWinner = $game->wrongAnswer();
            } else {
                $notAWinner = $game->wasCorrectlyAnswered();
            }
        } while ($notAWinner);


//        echo $outputString;
        return $outputString;
    }
}