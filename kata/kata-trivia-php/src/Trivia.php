<?php
/**
 * Created by PhpStorm.
 * User: VictorRentea
 * Date: 08-Nov-18
 * Time: 03:20 PM
 */

namespace video;


class Trivia implements TriviaInterface
{
    private Questions $questions;

    /** @var Player[] */
    private array $players = [];
    private array $playerNames = [];
    private array $places = [0];
    private array $purses = [0];
    private array $inPenaltyBox = [0];

    private int $currentPlayer = 0;
    private bool $isGettingOutOfPenaltyBox;

    function __construct()
    {
        $this->questions = new Questions();
    }

    function add($playerName)
    {
        $this->players[] = new Player($playerName);
        $this->playerNames[]=$playerName;
        $this->places[$this->howManyPlayers()] = 0;
        $this->purses[$this->howManyPlayers()] = 0;
        $this->inPenaltyBox[$this->howManyPlayers()] = false;
        echoln($playerName . " was added");
        echoln("They are player number " . $this->howManyPlayers());
        return true;
    }

    private function howManyPlayers(): int
    {
        return count($this->playerNames);
    }

    function roll($roll)
    {
        echoln($this->playerNames[$this->currentPlayer] . " is the current player");
        echoln("They have rolled a " . $roll);
        if ($this->inPenaltyBox[$this->currentPlayer]) {
            if ($roll % 2 != 0) {
                $this->isGettingOutOfPenaltyBox = true;
                echoln($this->playerNames[$this->currentPlayer] . " is getting out of the penalty box");
                $this->places[$this->currentPlayer] = $this->places[$this->currentPlayer] + $roll;
                if ($this->places[$this->currentPlayer] > 11) $this->places[$this->currentPlayer] = $this->places[$this->currentPlayer] - 12;
                echoln($this->playerNames[$this->currentPlayer]
                    . "'s new location is "
                    . $this->places[$this->currentPlayer]);
                echoln("The category is " . $this->currentCategory());
                $this->askQuestion();
            } else {
                echoln($this->playerNames[$this->currentPlayer] . " is not getting out of the penalty box");
                $this->isGettingOutOfPenaltyBox = false;
            }
        } else {
            $this->places[$this->currentPlayer] = $this->places[$this->currentPlayer] + $roll;
            if ($this->places[$this->currentPlayer] > 11) $this->places[$this->currentPlayer] = $this->places[$this->currentPlayer] - 12;
            echoln($this->playerNames[$this->currentPlayer]
                . "'s new location is "
                . $this->places[$this->currentPlayer]);
            echoln("The category is " . $this->currentCategory());
            $this->askQuestion();
        }
    }

    function currentCategory()
    {
        $currentPlace = $this->places[$this->currentPlayer];
        return Questions::CATEGORIES[$currentPlace % 4];
    }

    private function askQuestion()
    {
        echoln($this->questions->getNextQuestion($this->currentCategory()));
    }

    function wasCorrectlyAnswered()
    {
        if ($this->inPenaltyBox[$this->currentPlayer]) {
            if ($this->isGettingOutOfPenaltyBox) {
                echoln("Answer was correct!!!!");
                $this->purses[$this->currentPlayer]++;
                echoln($this->playerNames[$this->currentPlayer]
                    . " now has "
                    . $this->purses[$this->currentPlayer]
                    . " Gold Coins.");
                $winner = $this->didPlayerWin();
                $this->currentPlayer++;
                if ($this->currentPlayer == $this->howManyPlayers()) $this->currentPlayer = 0;
                return $winner;
            } else {
                $this->currentPlayer++;
                if ($this->currentPlayer == $this->howManyPlayers()) $this->currentPlayer = 0;
                return true;
            }
        } else {
            echoln("Answer was corrent!!!!");
        $this->purses[$this->currentPlayer]++;
            echoln($this->playerNames[$this->currentPlayer]
                . " now has "
                . $this->purses[$this->currentPlayer]
                . " Gold Coins.");
            $winner = $this->didPlayerWin();
            $this->currentPlayer++;
            if ($this->currentPlayer == $this->howManyPlayers()) $this->currentPlayer = 0;
            return $winner;
        }
    }

    function didPlayerWin()
    {
        return !($this->purses[$this->currentPlayer] == 6);
    }

    function wrongAnswer()
    {
        echoln("Question was incorrectly answered");
        echoln($this->playerNames[$this->currentPlayer] . " was sent to the penalty box");
        $this->inPenaltyBox[$this->currentPlayer] = true;
        $this->currentPlayer++;
        if ($this->currentPlayer == $this->howManyPlayers()) $this->currentPlayer = 0;
        return true;
    }


}

