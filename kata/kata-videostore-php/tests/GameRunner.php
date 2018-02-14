<?php


$output = "";
function echoln($string) {
    global $output;
    $output.=$string."\n";
}

interface IGame {
    function __construct();

    function roll($roll);
    function wasCorrectlyAnswered();
    function wrongAnswer();

    function add($playerName);
}


include __DIR__.'/Game.php';
include __DIR__.'/NewGame.php';

$notAWinner = true;




function getOutput(int $i, IGame $aGame) : string {
    srand($i);
    global $output;
    $output = "";
    $aGame->add("Chet");
    $aGame->add("Pat");
    $aGame->add("Sue");
    do {
        $aGame->roll(rand(0,5) + 1);
        if (rand(0,9) == 7) {
            $notAWinner = $aGame->wrongAnswer();
        } else {
            $notAWinner = $aGame->wasCorrectlyAnswered();
        }
    } while ($notAWinner);

    return $output;
}


for ($i = 1; $i < 1000; $i++) {
    $expected =getOutput($i, new Game());
    $actual = getOutput($i, new NewGame());
    assert($expected == $actual);
}



//echo $output;