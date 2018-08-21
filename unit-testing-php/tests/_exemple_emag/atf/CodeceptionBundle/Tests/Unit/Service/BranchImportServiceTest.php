<?php

namespace Emag\Core\CodeceptionBundle\Tests\Unit\Service;


use Doctrine\Common\Persistence\ObjectManager;
use Emag\Core\CodeceptionBundle\Entity\BranchImport;
use Emag\Core\CodeceptionBundle\Entity\CodeBranch;
use Emag\Core\CodeceptionBundle\Service\BranchImportService;

class BranchImportServiceTest extends \PHPUnit_Framework_TestCase
{
    /** @var BranchImportService */
    protected $branchImportService;

    /** @var BranchImport */
    protected $branchImport;

    protected function setUp()
    {
        $this->branchImportService = new BranchImportService();

        $this->branchImport = new BranchImport();
    }

    public function testCheckImportStatusWithCodeceptionImportNotNullAndImportStatus1()
    {
        $this->branchImport->setStatus(BranchImport::IMPORT_STATUS_STARTED);

        $this->assertEquals([
            'error' => true,
            'type' => 'info',
            'status' => CodeBranch::IMPORT_STATUS_STARTED,
            'message' => [
                'body' => 'We\'ll refresh the page when it\'s done',
                'title' => 'The import process has started'
            ]
        ], $this->branchImportService->checkImportStatus($this->branchImport));
    }

    public function testCheckImportStatusWithCodeceptionImportNotNullAndImportStatus2()
    {
        $this->branchImport->setStatus(BranchImport::IMPORT_STATUS_FINISHED);

        $this->assertEquals(['error' => false], $this->branchImportService->checkImportStatus($this->branchImport));
    }

    public function testCheckImportStatusWithCodeceptionImportNotNullAndImportStatus3()
    {
        $this->branchImport->setStatus(BranchImport::IMPORT_STATUS_ERROR);

        $this->assertEquals([
            'error' => true,
            'type' => 'error',
            'status' => BranchImport::IMPORT_STATUS_ERROR,
            'message' => [
                'body' => 'There was a problem importing your branch. Please try again',
                'title' => ''
            ]
        ], $this->branchImportService->checkImportStatus($this->branchImport));
    }

    public function testCheckImportStatusWithCodeceptionImportNotNullAndImportStatusDefault()
    {
        $this->branchImport->setStatus(BranchImport::IMPORT_STATUS_PENDING);

        $this->assertEquals([
            'error' => true,
            'type' => 'info',
            'status' => BranchImport::IMPORT_STATUS_PENDING,
            'message' => [
                'body' => 'The import process is pending',
                'title' => ''
            ]
        ], $this->branchImportService->checkImportStatus($this->branchImport));
    }

    public function testCheckImportStatusWithCodeceptionImportNull()
    {
        $this->branchImport = null;

        $this->assertEquals([ 'error' => false ], $this->branchImportService->checkImportStatus($this->branchImport));
    }

    public function testStartImportJob()
    {
        $this->assertEquals(true , $this->branchImportService->startImportJob($this->branchImport));
        $this->assertEquals(BranchImport::IMPORT_STATUS_STARTED, $this->branchImport->getStatus());
    }

    public function testEndImportJob()
    {
        $this->assertEquals(true , $this->branchImportService->finishImportJob($this->branchImport));
        $this->assertEquals(BranchImport::IMPORT_STATUS_FINISHED, $this->branchImport->getStatus());
    }

    public function testEndImportJobWithError()
    {
        $this->assertEquals(true , $this->branchImportService->finishImportJobWithError($this->branchImport, new \Exception("Message", 404, new \Exception("Previous", 500))));
        $this->assertEquals(BranchImport::IMPORT_STATUS_ERROR, $this->branchImport->getStatus());
    }
}