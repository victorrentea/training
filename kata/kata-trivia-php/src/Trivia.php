<?php
/**
 * Created by PhpStorm.
 * User: VictorRentea
 * Date: 08-Nov-18
 * Time: 03:20 PM
 */

namespace video;


class Trivia
{
    private array $players = [];
    private array $places = [];
    private array $purses = [];
    private array $inPenaltyBox = [];

    private Questions $questions;

    private int $currentPlayer = 0;
    private bool $isGettingOutOfPenaltyBox;

    function __construct()
    {
        $this->questions = new Questions();
    }

    function add($playerName)
    {
        array_push($this->players, $playerName);
        $this->places[$this->howManyPlayers()] = 0;
        $this->purses[$this->howManyPlayers()] = 0;
        $this->inPenaltyBox[$this->howManyPlayers()] = false;
        echo $playerName . " was added" . "\n";
        echo "They are player number " . count($this->players) . "\n";
        return true;
    }

    private function howManyPlayers(): int
    {
        return count($this->players);
    }

    function roll($roll)
    {
        echo $this->players[$this->currentPlayer] . " is the current player" . "\n";
        echo "They have rolled a " . $roll . "\n";
        if ($this->inPenaltyBox[$this->currentPlayer]) {
            if ($roll % 2 != 0) {
                $this->isGettingOutOfPenaltyBox = true;
                echo $this->players[$this->currentPlayer] . " is getting out of the penalty box" . "\n";
                $this->places[$this->currentPlayer] = $this->places[$this->currentPlayer] + $roll;
                if ($this->places[$this->currentPlayer] > 11) $this->places[$this->currentPlayer] = $this->places[$this->currentPlayer] - 12;
                echo $this->players[$this->currentPlayer]
                    . "'s new location is "
                    . $this->places[$this->currentPlayer] . "\n";
                echo "The category is " . $this->currentCategory() . "\n";
                $this->askQuestion();
            } else {
                echo $this->players[$this->currentPlayer] . " is not getting out of the penalty box" . "\n";
                $this->isGettingOutOfPenaltyBox = false;
            }
        } else {
            $this->places[$this->currentPlayer] = $this->places[$this->currentPlayer] + $roll;
            if ($this->places[$this->currentPlayer] > 11) $this->places[$this->currentPlayer] = $this->places[$this->currentPlayer] - 12;
            echo $this->players[$this->currentPlayer]
                . "'s new location is "
                . $this->places[$this->currentPlayer] . "\n";
            echo "The category is " . $this->currentCategory() . "\n";
            $this->askQuestion();
        }
    }

    function currentCategory()
    {
        $currentPlace = $this->places[$this->currentPlayer];
        $categories = [
            0 => 'Pop',
            1 => 'Science',
            2 => 'Sports',
            3 => 'Rock'
        ];
        return $categories[$currentPlace % 4];
    }

    private function askQuestion()
    {
        echo $this->questions->getNextQuestion($this->currentCategory());
    }

    function wasCorrectlyAnswered()
    {
        if ($this->inPenaltyBox[$this->currentPlayer]) {
            if ($this->isGettingOutOfPenaltyBox) {
                echo "Answer was correct!!!!" . "\n";
                $this->purses[$this->currentPlayer]++;
                echo $this->players[$this->currentPlayer]
                    . " now has "
                    . $this->purses[$this->currentPlayer]
                    . " Gold Coins." . "\n";
                $winner = $this->didPlayerWin();
                $this->currentPlayer++;
                if ($this->currentPlayer == count($this->players)) $this->currentPlayer = 0;
                return $winner;
            } else {
                $this->currentPlayer++;
                if ($this->currentPlayer == count($this->players)) $this->currentPlayer = 0;
                return true;
            }
        } else {
            echo "Answer was corrent!!!!" . "\n";
            $this->purses[$this->currentPlayer]++;
            echo $this->players[$this->currentPlayer]
                . " now has "
                . $this->purses[$this->currentPlayer]
                . " Gold Coins." . "\n";
            $winner = $this->didPlayerWin();
            $this->currentPlayer++;
            if ($this->currentPlayer == count($this->players)) $this->currentPlayer = 0;
            return $winner;
        }
    }

    function didPlayerWin()
    {
        return !($this->purses[$this->currentPlayer] == 6);
    }

    function wrongAnswer()
    {
        echo "Question was incorrectly answered" . "\n";
        echo $this->players[$this->currentPlayer] . " was sent to the penalty box" . "\n";
        $this->inPenaltyBox[$this->currentPlayer] = true;
        $this->currentPlayer++;
        if ($this->currentPlayer == count($this->players)) $this->currentPlayer = 0;
        return true;
    }


}

