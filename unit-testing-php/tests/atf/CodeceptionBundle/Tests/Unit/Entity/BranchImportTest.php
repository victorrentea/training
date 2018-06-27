<?php
namespace Emag\Core\CodeceptionBundle\Tests\Unit\Entity;

use Emag\Core\CodeceptionBundle\Entity\BranchImport;
use Emag\Core\CodeceptionBundle\Entity\CodeceptionInstance;
use Emag\Core\UserBundle\Entity\User;

class BranchImportTest extends \PHPUnit_Framework_TestCase
{
    public const BRANCH_NAME = 'branch-name';
    public const IMPORT_STATUS = 2;

    public function testEntityGettersAndSetters()
    {
        $branchImport = new BranchImport();

        $this->assertEquals($branchImport, $branchImport->setBranchName($branchName = static::BRANCH_NAME));
        $this->assertEquals($branchImport, $branchImport->setCodeceptionInstance($codeceptionInstance = new CodeceptionInstance()));
        $this->assertEquals($branchImport, $branchImport->setUser($user = new User()));
        $this->assertEquals($branchImport, $branchImport->setFinishedAt($finishedAt = new \DateTime()));
        $this->assertEquals($branchImport, $branchImport->setStatus($status = static::IMPORT_STATUS));

        $this->assertEquals($branchName, $branchImport->getBranchName());
        $this->assertEquals($codeceptionInstance, $branchImport->getCodeceptionInstance());
        $this->assertEquals($user, $branchImport->getUser());
        $this->assertEquals($finishedAt, $branchImport->getFinishedAt());
        $this->assertEquals($status, $branchImport->getStatus());
    }
}
