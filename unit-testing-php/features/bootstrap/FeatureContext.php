<?php

use Behat\Behat\Context\Context;
use Behat\Behat\Tester\Exception\PendingException;
use Behat\Gherkin\Node\PyStringNode;
use Behat\Gherkin\Node\TableNode;
use Mockery\MockInterface;
use PhpUnitWorkshopTest\BowlingScore;

/**
 * Defines application features from the specific context.
 */
class FeatureContext extends \PHPUnit\Framework\TestCase implements Context
{
    /** @var BowlingGameCuDependinte */
    private $service;
    /** @var ScoreProvider|MockInterface */
    private $mockScoreProvider;
    /**
     * @When /^The score string is '(.*)'$/
     */
    public function theScoreStringIs(string $inputScoreString)
    {
        $this->mockScoreProvider->shouldReceive("getScore")
            ->andReturn($inputScoreString);
    }

    /**
     * @Then /^The final score is (\d+)$/
     */
    public function theFinalScoreIs($expectedTotalScore)
    {
       $this->assertEquals($expectedTotalScore, $this->service->calculateScore());
    }

    /**
     * @Given /^A mock Score String Provider$/
     */
    public function aMockScoreStringProvider()
    {
        $this->mockScoreProvider = Mockery::mock(ScoreProvider::class);

        $this->service = new BowlingGameCuDependinte($this->mockScoreProvider);
    }
}


class BowlingGameCuDependinte {
    private $scoreProvider;

    public function __construct(ScoreProvider $scoreProvider)
    {
        $this->scoreProvider = $scoreProvider;
    }

    public function calculateScore(): int {
        return BowlingScore::calculateScore($this->scoreProvider->getScore());
    }

}
class ScoreProvider {

    public function getScore():string
    {
        throw new Exception("E inchis la non-stop");
    }
}

