<?php
namespace Emag\Core\JobBundle\Tests\Unit\Entity;

use Emag\Core\JobBundle\Entity\Job;
use Emag\Core\JobBundle\Entity\JobInfo;

class JobInfoTest extends \PHPUnit_Framework_TestCase
{
    public function testFinishJobWillAlsoSetStatusToErrorWhenStuckInProgress()
    {
        $jobInfo = new JobInfo();

        $jobInfo->startJob();
        $jobInfo->finishJob();

        $this->assertNotEquals(Job::STATUS_IN_PROGRESS, $jobInfo->getJobInfoStatus());
        $this->assertEquals(Job::STATUS_ERROR, $jobInfo->getJobInfoStatus());
    }
}
