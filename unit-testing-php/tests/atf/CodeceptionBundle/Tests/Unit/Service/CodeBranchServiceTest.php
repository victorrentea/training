<?php

namespace Emag\Core\CodeceptionBundle\Tests\Unit\Service;

use Doctrine\Common\Persistence\ObjectManager;
use Emag\Core\CodeceptionBundle\Entity\CodeBranch;
use Emag\Core\CodeceptionBundle\Service\CodeBranchService;

class CodeBranchServiceTest extends \PHPUnit_Framework_TestCase
{
    /** @var CodeBranchService */
    protected $codeBranchService;

    public function setUp()
    {
        parent::setUp();

        /** @var ObjectManager|\PHPUnit_Framework_MockObject_MockObject $managerMock */
        $managerMock = $this->getMockBuilder(ObjectManager::class)->disableOriginalConstructor()->getMock();

        $this->codeBranchService = new CodeBranchService();
        $this->codeBranchService->setManager($managerMock);
    }

    public function testRemoveBranches()
    {
        self::assertTrue(
            $this->codeBranchService->removeBranches([
                new CodeBranch(), new CodeBranch()
            ])
        );
    }
}
