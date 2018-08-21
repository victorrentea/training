<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 02-Aug-28
 * Time: 22:06 AM
 */

namespace PhpUnitWorkshopTest;


use PHPUnit\Framework\TestCase;

class TennisScoreTest extends TestCase
{
    /** @var TennisScore */
    private $tennisScore;

    protected function setUp()/* The :void return type declaration that should be here would cause a BC issue */
    {
        $this->tennisScore = new TennisScore();
    }

    private function setPlayerScore(int $player, int $points)
    {
        for ($i = 0; $i < $points; $i++) {
            $this->tennisScore->addPoint($player);
        }
    }

    private function setScore(int $player1Score, int $player2Score)
    {
        $this->setPlayerScore(1, $player1Score);
        $this->setPlayerScore(2, $player2Score);
    }

    public function testScoreLoveLove()
    {
        $this->assertEquals("Love-Love",
            $this->tennisScore->getScore());
    }

    public function testScoreLoveFifteen()
    {
        $this->setScore(0, 1);
        $this->assertEquals("Love-Fifteen",
            $this->tennisScore->getScore());
    }

    public function testScoreLoveThirty()
    {
        $this->setScore(0, 2);

        $this->assertEquals("Love-Thirty",
            $this->tennisScore->getScore());
    }

    public function testScoreLoveForty()
    {
        $this->setScore(0, 3);
        $this->assertEquals("Love-Forty",
            $this->tennisScore->getScore());
    }

    public function testScoreFifteenLove()
    {
        $this->setScore(1, 0);
        $this->assertEquals("Fifteen-Love",
            $this->tennisScore->getScore());
    }

    public function testScoreDeuce()
    {
        $this->setScore(3, 3);
        $this->assertEquals("Deuce",
            $this->tennisScore->getScore());
    }

    public function testScoreAdvantagePlayer2()
    {
        $this->setScore(3, 4);
        $this->assertEquals("Advantage Player2",
            $this->tennisScore->getScore());
    }

    public function testScoreAdvantagePlayer1()
    {
        $this->setScore(4, 3);
        $this->assertEquals("Advantage Player1",
            $this->tennisScore->getScore());
    }

    public function testScoreGameWonPlayer1() {
        $this->setScore(4, 2);
        $this->assertEquals("Game won Player1",
            $this->tennisScore->getScore());
    }
}