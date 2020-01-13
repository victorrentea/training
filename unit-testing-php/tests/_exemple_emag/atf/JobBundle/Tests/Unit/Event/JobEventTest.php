<?php

namespace Emag\Core\JobBundle\Tests\Unit\Event;

use Emag\Core\CodeceptionBundle\Entity\Country;
use Emag\Core\CodeceptionBundle\Entity\Stack;
use Emag\Core\CodeceptionBundle\Entity\Test;
use Emag\Core\CodeceptionBundle\Entity\TestingPlan;
use Emag\Core\JobBundle\Entity\JobInfo;
use Emag\Core\JobBundle\Entity\JobSchedule;
use Emag\Core\JobBundle\Event\JobEvent;
use Emag\Core\UserBundle\Entity\User;
use Symfony\Component\Validator\Constraints\Count;

class JobEventTest extends \PHPUnit_Framework_TestCase
{
    public function testConstructorSetsAllTheRightVariablesInAllTheRightPlaces()
    {
        $browser = 'firefox';
        $stack = new Stack();
        $test = new Test();
        $testingPlan = new TestingPlan();
        $user = new User();
        $iterations = 1;
        $parameters = [];
        $importedParameters = [];
        $breakpoint = 'breakpoint';
        $takeSnapshots = true;
        $jobSchedule = new JobSchedule();
        $notificationEmails = 'atf.test@emag.ro';
        $serialExecution = false;
        $jobInfoName = "Some name";

        $event = new JobEvent(
            $browser,
            $stack,
            $test,
            $testingPlan,
            $user,
            $iterations,
            $parameters,
            $importedParameters,
            $breakpoint,
            $takeSnapshots,
            $jobSchedule,
            $notificationEmails,
            $serialExecution,
            $jobInfoName
        );

        $this->assertEquals($browser, $event->getBrowser());
        $this->assertEquals($stack, $event->getStack());
        $this->assertEquals($test, $event->getTest());
        $this->assertEquals($testingPlan, $event->getTestingPlan());
        $this->assertEquals($user, $event->getUser());
        $this->assertEquals($iterations, $event->getIterations());
        $this->assertEquals($parameters, $event->getParameters());
        $this->assertEquals($importedParameters, $event->getImportedParameters());
        $this->assertEquals($breakpoint, $event->getBreakpoint());
        $this->assertEquals($takeSnapshots, $event->getTakeSnapshots());
        $this->assertEquals($jobSchedule, $event->getJobSchedule());
        $this->assertEquals($notificationEmails, $event->getNotificationEmails());
        $this->assertEquals($serialExecution, $event->isSerialExecution());
        $this->assertEquals($jobInfoName, $event->getJobInfoName());
    }

    public function testJobInfoFerrying()
    {
        $event = new JobEvent('firefox', new Stack());
        $jobInfo = new JobInfo();

        $event->setJobInfo($jobInfo);
        
        $this->assertEquals($jobInfo, $event->getJobInfo());
    }

    public function testIsTestWillReturnTrueWhenTheEventContainsATest()
    {
        $event = new JobEvent('firefox', new Stack(), new Test());

        $this->assertTrue($event->isTest());
    }

    public function testIsTestWillReturnFalseWhenTheEventDoesNotContainATest()
    {
        $event = new JobEvent('firefox', new Stack());

        $this->assertFalse($event->isTest());
    }

    public function testIsTestingPlanWillReturnTrueWhenTheEventContainsATestingPlan()
    {
        $event = new JobEvent('firefox', new Stack(), null, new TestingPlan());

        $this->assertTrue($event->isTestingPlan());
    }

    public function testIsTestingPlanWillReturnFalseWhenTheEventDoesNotContainATestingPlan()
    {
        $event = new JobEvent('firefox', new Stack(), new Test(), null);

        $this->assertFalse($event->isTestingPlan());
    }

    public function testGetCountryWillReturnNullWhenTheEventDoesNotContainATest()
    {
        $event = new JobEvent('firefox', new Stack());

        $this->assertNull($event->getCountry());
    }

    public function testGetCountryWillReturnTheTestCountryWhenTheEventContainsATest()
    {
        $test = new Test();
        $country = new Country();
        $country->setName('Absurdistan');
        $country->setCode('ABS');
        $test->setCountry($country);
        $event = new JobEvent('firefox', new Stack(), $test);

        $this->assertEquals($country, $event->getCountry());
    }
}
