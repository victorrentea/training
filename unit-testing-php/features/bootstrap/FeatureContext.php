<?php

use Behat\Behat\Context\Context;
use Behat\Behat\Tester\Exception\PendingException;
use Behat\Gherkin\Node\PyStringNode;
use Behat\Gherkin\Node\TableNode;

/**
 * Defines application features from the specific context.
 */
class FeatureContext implements Context
{
    /**
     * Initializes context.
     *
     * Every scenario gets its own context instance.
     * You can also pass arbitrary arguments to the
     * context constructor through behat.yml.
     */
    public function __construct()
    {
    }

    /**
     * @Given A new game of size :arg1 x :arg2
     */
    public function aNewGameOfSizeX($arg1, $arg2)
    {
        throw new PendingException();
    }

    /**
     * @When I print the board
     */
    public function iPrintTheBoard()
    {
        throw new PendingException();
    }

    /**
     * @Then the output should contain exactly:
     */
    public function theOutputShouldContainExactly(PyStringNode $string)
    {
        throw new PendingException();
    }
}
