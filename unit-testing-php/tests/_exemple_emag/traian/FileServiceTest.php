<?php declare(strict_types=1);

namespace Tests\DefaultBundle\Service\PaymentParsing;

use MktpFinance\DefaultBundle\Exception\FileException;
use MktpFinance\DefaultBundle\Service\PathService;
use MktpFinance\DefaultBundle\Service\PaymentParsing\FileService;
use Symfony\Bundle\FrameworkBundle\Test\KernelTestCase;
use Symfony\Component\Validator\ConstraintViolationListInterface;
use \Mockery as Mockery;

/**
 * Class FileServiceTest
 * @package Tests\DefaultBundle\Service\PaymentParsing
 */
class FileServiceTest extends KernelTestCase
{
    /** @var FileService $fileService */
    private $fileService;

    /** @var string */
    private $testFile;

    /** @var string */
    private $testPath;

    public function setUp()
    {
        self::bootKernel();

        $this->testPath = static::$kernel->getRootDir()
            . '/../tests/MktpFinance/DefaultBundle/Service/PaymentParsing/Files/';
        $this->testFile = $this->testPath . 'unknown_demo.xlsx';

        $validator = static::$kernel->getContainer()->get('validator');

        $pathService = Mockery::mock(PathService::class);
        $pathService->shouldReceive('getPaymentFilesDirPath')->andReturn($this->testPath);
        $pathService->shouldReceive('getPaymentDocumentsDirPath')->andReturn($this->testPath);

        $this->fileService = new FileService($validator, $pathService);
    }

    public function testValidate()
    {
        $this->assertInstanceOf(ConstraintViolationListInterface::class, $this->fileService->validate($this->testFile));
    }

    public function testFailedMove()
    {
        $file = $this->testPath . 'test.txt';
        copy($this->testFile, $file);
        $this->expectException(FileException::class);
        $this->fileService->movePaymentDocument($file, 'test.txt');
    }

    /**
     * @dataProvider paymentDocumentProvider
     *
     * @param $isPaymentDocument
     *
     * @throws FileException
     */
    public function testMoveOnDuplicateAppendName($isPaymentDocument)
    {
        $file = $this->testPath . 'test.txt';
        copy($this->testFile, $file);
        $destination = $this->fileService->moveOnDuplicateAppendName($file, 'new.txt', $isPaymentDocument);
        $this->assertInternalType('string', $destination);
        unlink($destination);
    }

    public function testFailedMoveOnDuplicateAppendName()
    {
        $file = $this->testPath . 'test_1.txt';
        copy($this->testFile, $file);
        $destination = $this->fileService->moveOnDuplicateAppendName($file, 'test_1.txt');
        unlink($destination);
    }

    /**
     * @depends testMoveOnDuplicateAppendName
     *
     * @return array
     */
    public function paymentDocumentProvider(): array
    {
        return [[true], [false]];
    }

    public function tearDown()
    {
        foreach (glob($this->testPath . '/*.txt') as $f) {
            @unlink($f);
        }
        parent::tearDown();
    }

}