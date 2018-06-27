<?php
namespace Emag\Core\CoreApiBundle\Tests\Unit\Entity;

use Emag\Core\CoreApiBundle\Entity\StackInfo;

class StackInfoTest extends \PHPUnit_Framework_TestCase
{
    public function testCreateFromArray()
    {
        $info = [
            "code" => "1337",
            "name" => "Stack 1337",
            "deployment_status" => "ready",
            "type" => "testing",
            "description" => "Full testing Stack for RO, BG, HU",
            "owner" => "john.b.hacker",
            "blocked" => false,
            "environment" => "test"
        ];

        $stackInfo = StackInfo::createFromArray($info);

        $this->assertEquals($info['code'], $stackInfo->code);
        $this->assertEquals($info['name'], $stackInfo->name);
        $this->assertEquals($info['deployment_status'], $stackInfo->deploymentStatus);
        $this->assertEquals($info['type'], $stackInfo->type);
        $this->assertEquals($info['description'], $stackInfo->description);
        $this->assertEquals($info['owner'], $stackInfo->owner);
        $this->assertEquals($info['blocked'], $stackInfo->blocked);
        $this->assertEquals($info['environment'], $stackInfo->environment);
    }
}
