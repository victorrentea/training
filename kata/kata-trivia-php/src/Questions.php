<?php

namespace video;

class Questions
{
    private array $popQuestions = [];
    private array $scienceQuestions = [];
    private array $sportsQuestions = [];
    private array $rockQuestions = [];

    public function __construct()
    {
        for ($i = 0; $i < 50; $i++) {
            $this->popQuestions[] = "Pop Question $i";
            $this->scienceQuestions[] = "Science Question $i";
            $this->sportsQuestions[] = "Sports Question $i";
            $this->rockQuestions[] = "Rock Question $i";
        }
    }

    public function getNextQuestion(string $currentCategory): string
    {
        if ($currentCategory == "Pop") {
            return array_shift($this->popQuestions);
        } else if ($currentCategory == "Science") {
            return array_shift($this->scienceQuestions);
        } else if ($currentCategory == "Sports") {
            return array_shift($this->sportsQuestions);
        } else if ($currentCategory == "Rock") {
            return array_shift($this->rockQuestions);
        }
        throw new \Exception($currentCategory);
    }

}

//echo array_shift((new Questions())->getPopQuestions());
//echo array_shift((new Questions())->getPopQuestions());