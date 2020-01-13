<?php
namespace Emag\Core\CodeceptionBundle\Tests\Unit\Entity;

use Emag\Core\CodeceptionBundle\Entity\CodeBranch;
use Emag\Core\CodeceptionBundle\Entity\CodeRepository;

class CodeBranchTest extends \PHPUnit_Framework_TestCase
{
    public const BRANCH_NAME = 'branch-name';
    public const IMPORT_STATUS = 2;

    public function testEntityGettersAndSetters()
    {
        $codeBranch = new CodeBranch();

        $this->assertEquals($codeBranch, $codeBranch->setBranchName($branchName = static::BRANCH_NAME));
        $this->assertEquals($codeBranch, $codeBranch->setRepository($repository = new CodeRepository()));
        $this->assertEquals($codeBranch, $codeBranch->setImportStart($importStart = new \DateTime()));
        $this->assertEquals($codeBranch, $codeBranch->setImportEnd($importEnd = new \DateTime()));
        $this->assertEquals($codeBranch, $codeBranch->setImportStatus($importStatus = static::IMPORT_STATUS));

        $this->assertEquals($branchName, $codeBranch->getBranchName());
        $this->assertEquals($repository, $codeBranch->getRepository());
        $this->assertEquals($importStart, $codeBranch->getImportStart());
        $this->assertEquals($importEnd, $codeBranch->getImportEnd());
        $this->assertEquals($importStatus, $codeBranch->getImportStatus());
    }
}
