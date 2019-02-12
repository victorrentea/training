<?php
/**
 * Created by PhpStorm.
 * User: VictorRentea
 * Date: 08-Nov-18
 * Time: 03:20 PM
 */

namespace video;

class Player {
    private $name;
    private $place = 0;

    public function __construct(string $name)
    {
        $this->name = $name;
    }

    public function getName(): string
    {
        return $this->name;
    }
    public function incrementPlace(int $roll) {
        $this->place += $roll;
        if ($this->place >= 12) {
            $this->place -= 12;
        }
    }

    public function getPlace(): int
    {
        return $this->place;
    }
}


class TriviaBetter implements ITrivia {
    const QUESTION_CATEGORY_NAME = ['Pop','Science', 'Sports', 'Rock'];
    private $questions = [[],[],[],[]];


    /** @var Player[] */
    private $players = [];
    private $purses = [0]; // la fel
    private $inPenaltyBox = [0]; // falafel
    private $currentPlayer = 0;
    private $isGettingOutOfPenaltyBox;

    function __construct()
    {
        for ($i = 0; $i < 50; $i++) {
            $this->questions[0][] = "Pop Question $i";
            $this->questions[1][] = "Science Question $i";
            $this->questions[2][] = "Sports Question $i";
            $this->questions[3][] = "Rock Question $i";
        }
    }
    function isPlayable() {
        return $this->howManyPlayers() >= 2;
    }
    function add(string $playerName) {
        $this->players[]=new Player($playerName);
        $this->purses[$this->howManyPlayers()] = 0;
        $this->inPenaltyBox[$this->howManyPlayers()] = false;
        echoln($playerName . " was added");
        echoln("They are player number " . $this->howManyPlayers());
        return true;
    }
    function howManyPlayers() {
        return count($this->players);
    }
    function  roll($roll) {
        echoln($this->players[$this->currentPlayer]->getName() . " is the current player");
        echoln("They have rolled a " . $roll);
        if ($this->inPenaltyBox[$this->currentPlayer]) {
            if ($roll % 2 != 0) {
                $this->isGettingOutOfPenaltyBox = true;
                echoln($this->players[$this->currentPlayer]->getName() . " is getting out of the penalty box");


                $this->players[$this->currentPlayer]->incrementPlace($roll);

                echoln($this->players[$this->currentPlayer]->getName()
                    . "'s new location is "
                    .$this->players[$this->currentPlayer]->getPlace());
                echoln("The category is " . $this->getCurrentCategoryName());
                $this->askQuestion();
            } else {
                echoln($this->players[$this->currentPlayer]->getName() . " is not getting out of the penalty box");
                $this->isGettingOutOfPenaltyBox = false;
            }
        } else {

            $this->players[$this->currentPlayer]->incrementPlace($roll);

            echoln($this->players[$this->currentPlayer]->getName()
                . "'s new location is "
                .$this->players[$this->currentPlayer]->getPlace());
            echoln("The category is " . $this->getCurrentCategoryName());
            $this->askQuestion();
        }
    }

    function  askQuestion() {
        echoln(array_shift($this->questions[$this->players[$this->currentPlayer]->getPlace() % 4]));
    }
    private function getCurrentCategoryName() {
        return self::QUESTION_CATEGORY_NAME[$this->players[$this->currentPlayer]->getPlace() % 4];
    }
    function wasCorrectlyAnswered() {
        if ($this->inPenaltyBox[$this->currentPlayer]){
            if ($this->isGettingOutOfPenaltyBox) {
                echoln("Answer was correct!!!!");
                $this->purses[$this->currentPlayer]++;
                echoln($this->players[$this->currentPlayer]->getName()
                    . " now has "
                    .$this->purses[$this->currentPlayer]
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
            echoln($this->players[$this->currentPlayer]->getName()
                . " now has "
                .$this->purses[$this->currentPlayer]
                . " Gold Coins.");
            $winner = $this->didPlayerWin();
            $this->currentPlayer++;
            if ($this->currentPlayer == $this->howManyPlayers()) $this->currentPlayer = 0;
            return $winner;
        }
    }
    function wrongAnswer(){
        echoln("Question was incorrectly answered");
        echoln($this->players[$this->currentPlayer]->getName() . " was sent to the penalty box");
        $this->inPenaltyBox[$this->currentPlayer] = true;
        $this->currentPlayer++;
        if ($this->currentPlayer == $this->howManyPlayers()) $this->currentPlayer = 0;
        return true;
    }
    function didPlayerWin() {
        return !($this->purses[$this->currentPlayer] == 6);
    }
}