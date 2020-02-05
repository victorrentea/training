<?php
/**
 * Created by PhpStorm.
 * User: VictorRentea
 * Date: 08-Nov-18
 * Time: 03:20 PM
 */

namespace video;


use function sprintf;

class Trivia implements TriviaInterface
{
    private Questions $questions;

    /** @var Player[] */
    private array $players = [];

    private int $currentPlayerIndex = 0;
    private bool $isGettingOutOfPenaltyBox;

    function __construct()
    {
        $this->questions = new Questions();
    }

    function add($playerName)
    {
        $this->players[] = new Player($playerName);
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
        echoln("They have rolled a $roll");
        if (!$this->currentPlayer()->isInPenaltyBox()) {
            $this->x($roll);
            return;
        } else {
            if ($roll % 2 != 0) {
                $this->isGettingOutOfPenaltyBox = true;
                echoln($this->currentPlayerName() . " is getting out of the penalty box");
                $this->x($roll);
            } else {
                echoln($this->currentPlayerName() . " is not getting out of the penalty box");
                $this->isGettingOutOfPenaltyBox = false;
            }
        }
    }

    private function currentPlayerName(): string
    {
        return $this->currentPlayer()->getName();
    }

    private function currentPlayer(): Player
    {
        return $this->players[$this->currentPlayerIndex];
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
        if ($this->currentPlayer()->isInPenaltyBox() &&
            !$this->isGettingOutOfPenaltyBox) {

            $gameContinues = true;
        } else {
            echoln("Answer was correct!!!!");
            $this->currentPlayer()->addOneCoin();
            echoln(sprintf("%s now has %s Gold Coins.",
                $this->currentPlayerName(),
                $this->currentPlayer()->getPurse()));

            $gameContinues = !$this->currentPlayer()->hasWon();
        }
        $this->advanceToNextPlayer();
        return $gameContinues;
    }

    private function advanceToNextPlayer(): void
    {
        $this->currentPlayerIndex++;
        if ($this->currentPlayerIndex == $this->howManyPlayers()) {
            $this->currentPlayerIndex = 0;
        }
    }

    function wrongAnswer()
    {
        echoln("Question was incorrectly answered");
        echoln($this->currentPlayerName() . " was sent to the penalty box");
        $this->currentPlayer()->sendToPenaltyBox();
        $this->advanceToNextPlayer();
        return true;
    }


}

