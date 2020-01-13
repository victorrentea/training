<?php
namespace Emag\Core\JobBundle\Tests\Util;

use Emag\Core\CodeceptionBundle\Entity\Stack;
use Emag\Core\CodeceptionBundle\Entity\Test;
use Emag\Core\JobBundle\Entity\Job;
use Emag\Core\JobBundle\Entity\JobInfo;

class JobInfoBuilder
{
    private $id;
    private $stackId;
    private $browser;
    private $testType;
    private $jobInfoType;

    /**
     * @return JobInfo
     */
    public function getJobInfo()
    {
        $jobInfo = new JobInfo();

        if (!empty($this->id)) {
            $reflectionProperty = new \ReflectionProperty(JobInfo::class, 'id');
            $reflectionProperty->setAccessible(true);
            $reflectionProperty->setValue($jobInfo, $this->id);
        }

        $jobInfo->setJobInfoType($this->jobInfoType);

        if (!empty($this->testType)) {
            $test = new Test();
            $test->setType($this->testType);
            $jobInfo->setTest($test);
        }

        $job = new Job();
        $job->getJobInfos()->add($jobInfo);
        $jobInfo->setJob($job);

        if (!empty($this->stackId)) {
            $stack = new Stack();
            $reflectionProperty = new \ReflectionProperty(Stack::class, 'id');
            $reflectionProperty->setAccessible(true);
            $reflectionProperty->setValue($stack, $this->stackId);
            $job->setStack($stack);
        }

        if (!empty($this->browser)) {
            $job->setBrowser($this->browser);
        }

        return $jobInfo;
    }

    /**
     * @param int $id
     * @return $this
     */
    public function withId($id)
    {
        $this->id = $id;

        return $this;
    }

    /**
     * @param int $stackId
     * @return $this
     */
    public function withStackId($stackId)
    {
        $this->stackId = $stackId;

        return $this;
    }

    /**
     * @param string $browser
     * @return $this
     */
    public function withBrowser($browser)
    {
        $this->browser = $browser;

        return $this;
    }

    /**
     * @param string $testType
     * @return $this
     */
    public function withTestType($testType)
    {
        $this->testType = $testType;

        return $this;
    }

    /**
     * @param $type
     * @return $this
     */
    public function withJobInfoType($type)
    {
        $this->jobInfoType = $type;

        return $this;
    }
}