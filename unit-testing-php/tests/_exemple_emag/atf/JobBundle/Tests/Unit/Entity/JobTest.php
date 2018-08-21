<?php
namespace Emag\Core\JobBundle\Tests\Unit\Entity;

use Emag\Core\CodeceptionBundle\Entity\Test;
use Emag\Core\CodeceptionBundle\Entity\TestingPlan;
use Emag\Core\JobBundle\Entity\Job;

class JobTest extends \PHPUnit_Framework_TestCase
{
    public const TESTING_PLAN_NAME = 'Some testing plan name';
    public const TEST_NAME = 'Some test name';
    public const DEFAULT_NAME = 'Cest File';

    /**
     * Test that Job::getName()
     * Will return the testing plan name
     * When the job contains a testing plan
     */
    public function testGetNameWillReturnTestingPlanNameWhenTheJobContainsATestingPlan()
    {
        $testingPlan = new TestingPlan();
        $testingPlan->setName(static::TESTING_PLAN_NAME);

        $job = new Job();
        $job->setTest(null);
        $job->setTestingPlan($testingPlan);

        $this->assertEquals(static::TESTING_PLAN_NAME, $job->getName());
    }

    /**
     * Test that Job::getName()
     * Will return the test name
     * When the job contains a test
     */
    public function testGetNameWillReturnTestNameWhenTheJobContainsATest()
    {
        $test = new Test();
        $test->setName(static::TEST_NAME);

        $job = new Job();
        $job->setTest($test);
        $job->setTestingPlan(null);

        $this->assertEquals(static::TEST_NAME, $job->getName());
    }

    /**
     * Test that Job::getName()
     * Will return the test name
     * When the job contains a test
     */
    public function testGetNameWillReturnADefaultNameWhenTheJobDoesNotContainsATestOrATestingPlan()
    {
        $job = new Job();
        $job->setTest(null);
        $job->setTestingPlan(null);

        $this->assertEquals(static::DEFAULT_NAME, $job->getName());
    }

    /**
     * @param $browser
     * @dataProvider provideBrowserName
     */
    public function testSetBrowserWillNormalizeTheBrowserStringByLowerCasingIt($browser)
    {
        $job = new Job();
        $job->setBrowser($browser);

        $this->assertEquals(strtolower($browser), $job->getBrowser());
    }

    public function provideBrowserName()
    {
        return [
            ['firefox'],
            ['Firefox'],
            ['FIREFOX'],
            ['FiReFoX'],
        ];
    }
}
