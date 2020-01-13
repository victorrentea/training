<?php

namespace PhpUnitWorkshopTest;

use PHPUnit\Framework\TestCase;

class BowlingScoreTest extends TestCase
{

    /** @test */
    public function dummy()
    {
        $this->assertEquals(1, BowlingScore::calculateScore('1-'));
    }

    /** @test */
    public function dummy2()
    {
        $this->assertEquals(2, BowlingScore::calculateScore('2-'));
    }

    /** @test */
    public function dummy3()
    {
        $this->assertEquals(3, BowlingScore::calculateScore('3-'));
    }

    /** @test */
    public function dummy4()
    {
        $this->assertEquals(5, BowlingScore::calculateScore('23'));
    }

    /** @test */
    public function chifla()
    {
        $this->assertEquals(0, BowlingScore::calculateScore(str_repeat('-', 20)));
    }

    /** @test */
    public function ciorap()
    {
        $this->assertEquals(4, BowlingScore::calculateScore('1111'));
    }
    /** @test */
    public function nouazero()
    {
        $this->assertEquals(90, BowlingScore::calculateScore('9-9-9-9-9-9-9-9-9-9-'));
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




}
