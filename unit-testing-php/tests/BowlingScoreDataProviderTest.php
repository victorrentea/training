<?php

namespace PhpUnitWorkshopTest;

use PHPUnit\Framework\TestCase;

class BowlingScoreTest extends TestCase
{


    function data(): array
    {
        return [
        ['1-', 1],
        ['2-', 2],
        ['3-', 3],
        ['23', 5],
        [str_repeat('-', 20), 0],
        ['1111', 4],
        ['9-9-9-9-9-9-9-9-9-9-', 90],
        ];
    }


    /** @test */
    public function spareMinus()
    {
        $this->assertEquals(10, BowlingScore::calculateScore('6/--'));
    }
    /** @test */
    public function spareMinus2()
    {
        $this->assertEquals(12, BowlingScore::calculateScore('6/1-'));
    }
    /** @test */
    public function spareLast()
    {
        $this->assertEquals(10, BowlingScore::calculateScore('1/'));
    }

    /** @test */
    public function spareLast1()
    {
        $this->assertEquals(21, BowlingScore::calculateScore('1/1/'));
    }

    /** @test */
    public function strikeThree()
    {
        $this->assertEquals(22, BowlingScore::calculateScore('X33'));
    }

    /** @test */
    public function strikeThreeSpare()
    {
        $this->assertEquals(30, BowlingScore::calculateScore('X3/'));
    }






    /**
     * @test
     * @dataProvider data
     */
    public function parameterizedTest(string $scoreString, int $expectedTotalScore)
    {
        $this->assertEquals($expectedTotalScore, BowlingScore::calculateScore($scoreString));
    }

}
