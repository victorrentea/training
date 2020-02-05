<?php

namespace video;

class Player
{
    private string $name;
    private int $place = 0;
    private int $purse = 0;
    private bool $inPenaltyBox = false;

    public function __construct(string $name)
    {
        $this->name = $name;
    }

    public function getName(): string
    {
        return $this->name;
    }

    public function getPlace(): int
    {
        return $this->place;
    }
    public function incrementPlace($roll)
    {
        $this->place += $roll;
        if ($this->place >= 12) {
            $this->place -= 12;
        }
    }

    public function getPurse(): int
    {
        return $this->purse;
    }
    public function addOneCoin()
    {
        $this->purse++;
    }

    public function isInPenaltyBox(): bool
    {
        return $this->inPenaltyBox;
    }
    public function sendToPenaltyBox()
    {
        $this->inPenaltyBox = true;
    }

    public function hasWon(): bool
    {
        return $this->purse == 6;
    }

}