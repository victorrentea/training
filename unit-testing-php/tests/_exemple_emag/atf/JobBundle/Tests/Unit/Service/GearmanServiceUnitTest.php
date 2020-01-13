<?php
namespace Emag\Core\JobBundle\Tests\Unit\Service;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\ORM\EntityManager;
use Emag\Core\BaseBundle\Exception\GearmanExecuteJobException;
use Emag\Core\JobBundle\Entity\Job;
use Emag\Core\JobBundle\Service\GearmanService;
use Mmoreram\GearmanBundle\Service\GearmanClient;
use PHPUnit_Framework_MockObject_MockObject;

/**
 * @group unit-tests
 */
class GearmanServiceUnitTest extends \PHPUnit_Framework_TestCase
{
    public const JOB_PATH = 'Emag\Core\JobBundle\Entity\Job';
    public const JOB_INFO_PATH = 'Emag\Core\JobBundle\Entity\JobInfo';
    public const RANDOM_TEST_ID = 1;
    public const GEARMAN_EXECUTE_EXCEPTION_PATH = 'Emag\Core\BaseBundle\Exception\GearmanExecuteJobException';
    public const GEARMAN_CLIENT_PATH = 'Mmoreram\GearmanBundle\Service\GearmanClient';
    public const ONLY_ONE_TEST_SENT_TO_GEARMAN = 1;
    public const ENTITY_MANAGER_PATH = 'Doctrine\ORM\EntityManager';
    public const RANDOM_ALPHANUMERIC = 'abc';

    public function dataProviderRunningTest()
    {
        return array(
            array(static::RANDOM_TEST_ID),
        );
    }

    public function dataProviderExceptionTest()
    {
        return array(
            array(static::RANDOM_TEST_ID),
            array(static::RANDOM_ALPHANUMERIC)
        );
    }

    public function testExecuteBackgroundTestExceptionNullObject()
    {
        /** @var GearmanService $gearmanService */
        $gearmanService = new GearmanService();

        /** @var Job|PHPUnit_Framework_MockObject_MockObject $jobMock */
        $jobMock = $this->getMock(static::JOB_PATH, array('getId'));
        $jobMock->expects($this->once())
            ->method('getId')
            ->willReturn(0);

        $this->setExpectedException(static::GEARMAN_EXECUTE_EXCEPTION_PATH, GearmanService::JOB_NOT_FOUND_EXCEPTION);

        $gearmanService->executeBackgroundTest($jobMock);
    }

    public function testExecuteBackgroundTestExceptionNoJobInfo()
    {
        /** @var Job|PHPUnit_Framework_MockObject_MockObject $jobMock */
        $jobMock = $this->getMock(static::JOB_PATH, array('getId', 'getJobInfos'));
        $jobMock->expects($this->once())
            ->method('getId')
            ->willReturn(static::RANDOM_TEST_ID);
        $jobMock->expects($this->once())
            ->method('getJobInfos')
            ->willReturn(new ArrayCollection());

        /** @var EntityManager|PHPUnit_Framework_MockObject_MockObject $entityManagerMock */
        $entityManagerMock = $this->getMockBuilder(static::ENTITY_MANAGER_PATH)
            ->disableOriginalConstructor()
            ->setMethods(array('find', 'refresh', 'persist', 'flush'))
            ->getMock();

        /** @var GearmanService $gearmanService */
        $gearmanService = new GearmanService();

        $gearmanService->setEntityManager($entityManagerMock);

        $this->setExpectedException(
            static::GEARMAN_EXECUTE_EXCEPTION_PATH,
            GearmanService::NO_AVAILABLE_JOB_INFO_TO_RUN
        );

        $gearmanService->executeBackgroundTest($jobMock);
    }

    /**
     * @expectedException \Emag\Core\BaseBundle\Exception\GearmanExecuteJobException
     * @expectedExceptionMessage There are no available tests to un for this job
     */
    public function testExecuteBackgroundTestExceptionNoJobInfoSetError()
    {
        /** @var Job|PHPUnit_Framework_MockObject_MockObject $jobMock */
        $jobMock = $this->getMock(static::JOB_PATH, array('getId', 'getJobInfos'));
        $jobMock->expects($this->once())
            ->method('getId')
            ->willReturn(static::RANDOM_TEST_ID);
        $jobMock->expects($this->once())
            ->method('getJobInfos')
            ->willReturn(new ArrayCollection());

        /** @var EntityManager|PHPUnit_Framework_MockObject_MockObject $entityManagerMock */
        $entityManagerMock = $this->getMockBuilder(static::ENTITY_MANAGER_PATH)
            ->disableOriginalConstructor()
            ->setMethods(array('find', 'refresh', 'persist', 'flush'))
            ->getMock();

        /** @var GearmanService $gearmanService */
        $gearmanService = new GearmanService();

        $gearmanService->setEntityManager($entityManagerMock);

        $gearmanService->executeBackgroundTest($jobMock);
    }

    public function testExecuteBackgroundTestWorking()
    {
        /** @var Job|PHPUnit_Framework_MockObject_MockObject $jobMock */
        $jobInfoMock = $this->getMock(static::JOB_INFO_PATH, array('getId', 'getJobInfoStatus'));
        $jobInfoMock->expects($this->once())
            ->method('getId')
            ->willReturn(static::RANDOM_TEST_ID);
        $jobInfoMock->expects($this->once())
            ->method('getJobInfoStatus')
            ->willReturn(Job::STATUS_PENDING);
        $jobInfo = new ArrayCollection();
        $jobInfo->add($jobInfoMock);

        /** @var Job|PHPUnit_Framework_MockObject_MockObject $jobMock */
        $jobMock = $this->getMock(static::JOB_PATH, array('getId', 'getJobInfos'));
        $jobMock->expects($this->once())
            ->method('getId')
            ->willReturn(static::RANDOM_TEST_ID);
        $jobMock->expects($this->exactly(2))
            ->method('getJobInfos')
            ->willReturn($jobInfo);

        /** @var EntityManager|PHPUnit_Framework_MockObject_MockObject $entityManagerMock */
        $entityManagerMock = $this->getMockBuilder(static::ENTITY_MANAGER_PATH)
            ->disableOriginalConstructor()
            ->setMethods(array('find', 'refresh', 'persist', 'flush'))
            ->getMock();

        /** @var GearmanService $gearmanService */
        $gearmanService = new GearmanService();

        $gearmanService->setEntityManager($entityManagerMock);

        /** @var GearmanClient|PHPUnit_Framework_MockObject_MockObject $gearmanClientMock */
        $gearmanClientMock = $this->getMockBuilder(static::GEARMAN_CLIENT_PATH)
            ->disableOriginalConstructor()
            ->setMethods(array('doBackgroundJob'))
            ->getMock();
        $gearmanClientMock->expects($this->once())
            ->method('doBackgroundJob')
            ->willReturn(array(array(static::RANDOM_TEST_ID)));

        $gearmanService->setGearmanClient($gearmanClientMock);

        $response = $gearmanService->executeBackgroundTest($jobMock);

        $this->assertEquals(static::ONLY_ONE_TEST_SENT_TO_GEARMAN, count($response));

    }

}
