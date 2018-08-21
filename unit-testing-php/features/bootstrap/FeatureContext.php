<?php

use Behat\Behat\Context\Context;
use Behat\Behat\Tester\Exception\PendingException;
use Behat\Gherkin\Node\PyStringNode;
use Behat\Gherkin\Node\TableNode;

/**
 * Defines application features from the specific context.
 */
class FeatureContext extends \PHPUnit\Framework\TestCase implements Context
{
    /** @var TennisScore */
    private $tennisScore;
    /**
     * @Given /^An empty game$/
     */
    public function anEmptyGame()
    {
        $this->tennisScore = new TennisScore();
    }

    /**
     * @Then /^The score is "(.+)"$/
     */
    public function theScoreIs(string $expected)
    {
        $this->assertEquals($expected, $this->tennisScore->getScore());
    }

    /** @When /^Player(\d) scores a point$/ */
    public function playerscoresAPoint(int $playerNo)
    {
        $this->tennisScore->addPoint($playerNo);
    }

    /**
     * @When /^Player(\d) scores (\d+) points$/
     */
    public function playerscoresPoints(int $player, int $points)
    {
        for ($i = 0; $i < $points; $i++) {
            $this->tennisScore->addPoint($player);
        }
    }
}


