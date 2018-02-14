<?php


class Player
{
    private $name;
    private $place = 0;
    private $purse = 0;
    private $isInPenaltyBox = false;

    public function __construct(string $name)
    {
        $this->name = $name;
    }

    public function incrementPurse(): void {
        $this->purse ++;
    }

    public function getName(): string
    {
        return $this->name;
    }

    public function setPlace(int $place)
    {
        $this->place = $place;
    }

    public function setPurse(int $purse)
    {
        $this->purse = $purse;
    }
    public function getPlace(): int
    {
        return $this->place;
    }

    public function getPurse(): int
    {
        return $this->purse;
    }

    public function isInPenaltyBox(): bool
    {
        return $this->isInPenaltyBox;
    }

    public function setIsInPenaltyBox(bool $isInPenaltyBox)
    {
        $this->isInPenaltyBox = $isInPenaltyBox;
    }


}

class NewGame implements IGame
{

    /** @var Player[] */
    private $players;

    private $popQuestions;
    private $scienceQuestions;
    private $sportsQuestions;
    private $rockQuestions;

    private $currentPlayer = 0;
    private $isGettingOutOfPenaltyBox;

    function __construct()
    {
        $this->popQuestions = array();
        $this->scienceQuestions = array();
        $this->sportsQuestions = array();
        $this->rockQuestions = array();

        for ($i = 0; $i < 50; $i++) {
            array_push($this->popQuestions, "Pop Question " . $i);
            array_push($this->scienceQuestions, ("Science Question " . $i));
            array_push($this->sportsQuestions, ("Sports Question " . $i));
            array_push($this->rockQuestions, $this->createRockQuestion($i));
        }
    }

    function createRockQuestion($index)
    {
        return "Rock Question " . $index;
    }

    function isPlayable()
    {
        return ($this->howManyPlayers() >= 2);
    }


    function add($playerName)
    {
        $this->players[] = new Player($playerName);

        echoln($playerName . " was added");
        echoln("They are player number " . count($this->players));
        return true;
    }

    function howManyPlayers()
    {
        return count($this->players);
    }

    function roll($roll)
    {
        echoln($this->players[$this->currentPlayer]->getName() . " is the current player");
        echoln("They have rolled a " . $roll);

        if ($this->inPenaltyBox[$this->currentPlayer]) {
            if ($roll % 2 != 0) {
                $this->isGettingOutOfPenaltyBox = true;

                echoln($this->players[$this->currentPlayer]->getName() . " is getting out of the penalty box");
                $this->players[$this->currentPlayer]->setPlace($this->players[$this->currentPlayer]->getPlace() + $roll);
                if ($this->players[$this->currentPlayer]->getPlace() > 11)
                    $this->players[$this->currentPlayer]->setPlace($this->players[$this->currentPlayer]->getPlace() - 12);

                echoln($this->players[$this->currentPlayer]->getName()
                    . "'s new location is "
                    . $this->players[$this->currentPlayer]->getPlace());
                echoln("The category is " . $this->currentCategory());
                $this->askQuestion();
            } else {
                echoln($this->players[$this->currentPlayer]->getName() . " is not getting out of the penalty box");
                $this->isGettingOutOfPenaltyBox = false;
            }

        } else {

            $this->players[$this->currentPlayer]->setPlace($this->players[$this->currentPlayer]->getPlace() + $roll);
            if ($this->players[$this->currentPlayer]->getPlace() > 11) $this->players[$this->currentPlayer]->setPlace($this->players[$this->currentPlayer]->getPlace() - 12);

            echoln($this->players[$this->currentPlayer]->getName()
                . "'s new location is "
                . $this->players[$this->currentPlayer]->getPlace());
            echoln("The category is " . $this->currentCategory());
            $this->askQuestion();
        }

    }

    function askQuestion()
    {
        if ($this->currentCategory() == "Pop")
            echoln(array_shift($this->popQuestions));
        if ($this->currentCategory() == "Science")
            echoln(array_shift($this->scienceQuestions));
        if ($this->currentCategory() == "Sports")
            echoln(array_shift($this->sportsQuestions));
        if ($this->currentCategory() == "Rock")
            echoln(array_shift($this->rockQuestions));
    }


    function currentCategory()
    {
        if ($this->players[$this->currentPlayer]->getPlace() == 0) return "Pop";
        if ($this->players[$this->currentPlayer]->getPlace() == 4) return "Pop";
        if ($this->players[$this->currentPlayer]->getPlace() == 8) return "Pop";
        if ($this->players[$this->currentPlayer]->getPlace() == 1) return "Science";
        if ($this->players[$this->currentPlayer]->getPlace() == 5) return "Science";
        if ($this->players[$this->currentPlayer]->getPlace() == 9) return "Science";
        if ($this->players[$this->currentPlayer]->getPlace() == 2) return "Sports";
        if ($this->players[$this->currentPlayer]->getPlace() == 6) return "Sports";
        if ($this->players[$this->currentPlayer]->getPlace() == 10) return "Sports";
        return "Rock";
    }

    function wasCorrectlyAnswered()
    {
        if ($this->inPenaltyBox[$this->currentPlayer]) {
            if ($this->isGettingOutOfPenaltyBox) {
                echoln("Answer was correct!!!!");
                $this->players[$this->currentPlayer]->incrementPurse();
                echoln($this->players[$this->currentPlayer]->getName()
                    . " now has "
                    . $this->players[$this->currentPlayer]->getPurse()
                    . " Gold Coins.");

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

            echoln("Answer was corrent!!!!");
            $this->players[$this->currentPlayer]->incrementPurse();
            echoln($this->players[$this->currentPlayer]->getName()
                . " now has "
                . $this->players[$this->currentPlayer]->getPurse()
                . " Gold Coins.");

            $winner = $this->didPlayerWin();
            $this->currentPlayer++;
            if ($this->currentPlayer == count($this->players)) $this->currentPlayer = 0;

            return $winner;
        }
    }

    function wrongAnswer()
    {
        echoln("Question was incorrectly answered");
        echoln($this->players[$this->currentPlayer]->getName() . " was sent to the penalty box");
        $this->players[$this->currentPlayer]->setIsInPenaltyBox(true);

        $this->currentPlayer++;
        if ($this->currentPlayer == count($this->players)) $this->currentPlayer = 0;
        return true;
    }


    function didPlayerWin()
    {
        return !($this->players[$this->currentPlayer]->getPurse() == 6);
    }
}
