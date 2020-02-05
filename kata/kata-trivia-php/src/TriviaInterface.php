<?php

namespace video;

interface TriviaInterface
{
    function add($playerName);

    function roll($roll);

    function wasCorrectlyAnswered();

    function wrongAnswer();
}