<?php

namespace Emag\Core\CodeceptionBundle\Tests\Unit\Service\CodeceptionService
{

    use Emag\Core\CodeceptionBundle\Service\CodeceptionService;
    use Emag\Core\JobBundle\Entity\JobInfo;

    class MakeLogFilePathTest extends \PHPUnit_Framework_TestCase
    {
        public function testMakeLogFilePath()
        {
            /** @var CodeceptionService|\PHPUnit_Framework_MockObject_MockObject $codeceptionService */
            $codeceptionService = $this->getMockBuilder(CodeceptionService::class)
                ->disableOriginalConstructor()
                ->setMethods(['getLogFilePath'])
                ->getMock();
            $codeceptionService->expects($this->exactly(2))
                ->method('getLogFilePath')
                ->willReturn('/var/log/123.log');

            $jobInfo = new JobInfo();
            $codeceptionService->makeLogFilePath($jobInfo);
        }
    }
}

namespace Emag\Core\CodeceptionBundle\Service
{
    function file_exists($filename)
    {
        return false;
    }
}
