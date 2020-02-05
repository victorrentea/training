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
        $this->inPenaltyBox[$this->howManyPlayers()] = false;
        echoln($playerName . " was added");
        echoln("They are player number " . $this->howManyPlayers());
        return true;
    }

    private function howManyPlayers(): int
    {
        return count($this->players);
    }

    function roll($roll)
    {
        echoln($this->currentPlayerName() . " is the current player");
        echoln("They have rolled a " . $roll);
        if ($this->currentPlayer()->isInPenaltyBox()) {
            if ($roll % 2 != 0) {
                $this->isGettingOutOfPenaltyBox = true;
                echoln($this->currentPlayerName() . " is getting out of the penalty box");
                $this->x($roll);
            } else {
                echoln($this->currentPlayerName() . " is not getting out of the penalty box");
                $this->isGettingOutOfPenaltyBox = false;
            }
        } else {
            $this->x($roll);
        }
    }

    function currentCategory()
    {
        $currentPlace = $this->currentPlayer()->getPlace();
        return Questions::CATEGORIES[$currentPlace % 4];
    }

    private function askQuestion()
    {
        echoln($this->questions->getNextQuestion($this->currentCategory()));
    }

    function wasCorrectlyAnswered()
    {
        if ($this->currentPlayer()->isInPenaltyBox()) {
            if ($this->isGettingOutOfPenaltyBox) {
                echoln("Answer was correct!!!!");
                return $this->y();
            } else {
                $this->currentPlayer++;
                if ($this->currentPlayer == $this->howManyPlayers()) $this->currentPlayer = 0;
                return true;
            }
        } else {
            // FIXME Possible Bug mail sent to BIZ on 05.02.2020 by virentea
            echoln("Answer was corrent!!!!");
            return $this->y();
        }
    }

    function didPlayerWin()
    {
        return !($this->currentPlayer()->getPurse() == 6);
    }

    function wrongAnswer()
    {
        echoln("Question was incorrectly answered");
        echoln($this->currentPlayerName() . " was sent to the penalty box");
        $this->currentPlayer()->sendToPenaltyBox();
        $this->currentPlayer++;
        if ($this->currentPlayer == $this->howManyPlayers()) $this->currentPlayer = 0;
        return true;
    }

    private function currentPlayerName(): string
    {
        return $this->currentPlayer()->getName();
    }

    private function x($roll): void
    {
        $this->currentPlayer()->incrementPlace($roll);
        echoln($this->currentPlayerName()
            . "'s new location is "
            . $this->currentPlayer()->getPlace());
        echoln("The category is " . $this->currentCategory());
        $this->askQuestion();
    }

    private function currentPlayer(): Player
    {
        return $this->players[$this->currentPlayer];
    }

    /**
     * @return bool
     */
    private function y(): bool
    {
        $this->currentPlayer()->addOneCoin();
        echoln($this->currentPlayerName()
            . " now has "
            . $this->currentPlayer()->getPurse()
            . " Gold Coins.");
        $winner = $this->didPlayerWin();
        $this->currentPlayer++;
        if ($this->currentPlayer == $this->howManyPlayers()) $this->currentPlayer = 0;
        return $winner;
    }


}

