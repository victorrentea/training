<?php

function bigUglyMethod(int $a, int $b, int $c, int $d, int $e)
{
    // complex logic
    // complex logic
    // complex logic

    // more complex logic
    // more complex logic
    // more complex logic
}

// The method is called from various foreign places in the codebase
bigUglyMethod(1, 2, 3, 4, 5);
bigUglyMethod(1, 2, 3, 4, 5);
bigUglyMethod(1, 2, 3, 4, 5);
bigUglyMethod(1, 2, 3, 4, 5);
bigUglyMethod(1, 2, 3, 4, 5);

// TODO From my use-case, I call it too, to do more within:
bigUglyMethod(1, 2, 3, 4, 5);


// ============== "BOSS" LEVEL: A log harder to break down =================

function bossLevel(bool $stuff, bool $fluff, array $trebi)
{
    $i = 0;
    $j = 1;
    // more code
    if ($stuff) {
        // more code
        if ($fluff) {
            // more code
            foreach ($trebi as $treaba) {
                $i++;
                // more code
                // TODO HERE, when call this method, I want MY own custom code to run here
                // more code
            }
            // more code
        }
    }
    // more code

}