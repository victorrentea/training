<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 01-Aug-18
 * Time: 11:06 AM
 */


class TennisScore
{
    private $player1Score = 0;

    private $player2Score = 0;

    private const SCORE_LABELS = [
        0 => "Love",
        1 => "Fifteen",
        2 => "Thirty",
        3 => "Forty"
    ];


    /** @test
     * @
     */
    public function getScore()
    {
        if ($this->player1Score >= 4 &&
            $this->player1Score - $this->player2Score >= 2) {
            return "Game won Player1";
        }
        if (
            $this->player1Score >= 3 &&
            $this->player2Score >= 3
        ) {
            if ($this->player1Score - $this->player2Score == 1) {
                return 'Advantage Player1';
            }

            if ($this->player2Score - $this->player1Score == 1) {
                return 'Advantage Player2';
            }

            if ($this->player1Score == $this->player2Score) {
                return 'Deuce';
            }
        }






        return self::SCORE_LABELS[
            $this->player1Score].
            "-".
            self::SCORE_LABELS[$this->player2Score
            ];
    }

    public function addPoint(int $player)
    {
        if ($player == 1) {
            $this->player1Score++;
        } else {
            $this->player2Score++;
        }
    }
}