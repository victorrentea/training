<?php
namespace App\Tests\Service;

use App\Entity\{ Test, NotificationEmail };
use App\Repository\TestRepository;
use App\Service\{ JobService, TestService };
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\Common\Persistence\ObjectManager;
use PHPUnit\Framework\MockObject\MockObject;
use PHPUnit\Framework\TestCase;
use Symfony\Component\EventDispatcher\EventDispatcherInterface;
use Symfony\Component\Filesystem\Filesystem;
use Symfony\Component\HttpFoundation\File\File;
use Symfony\Component\Validator\{ ConstraintViolation, ConstraintViolationList, Validator\ValidatorInterface };

/**
 * Class TestServiceTest
 * @package App\Tests\Service
 */
class TestServiceTest extends TestCase
{
    /** @var Filesystem $fileSystem */
    private $fileSystem;

    /** @var ObjectManager|MockObject $objectManagerMock */
    private $objectManagerMock;

    /** @var TestService $testService */
    private $testService;

    /** @var ValidatorInterface|MockObject $validatorMock */
    private $validatorMock;

    /** @var JobService|MockObject $jobServiceMock */
    private $jobServiceMock;

    /** @var string $scriptsPath */
    private $scriptsPath;

    /** @var TestRepository|MockObject $testRepositoryMock */
    private $testRepositoryMock;

    /** @var EventDispatcherInterface|MockObject $eventDispatcherMock */
    private $eventDispatcherMock;

    public function setUp()
    {
        parent::setUp();

        $this->fileSystem = new Filesystem();
        $this->objectManagerMock = self::createMock(ObjectManager::class);
        $this->validatorMock = self::createMock(ValidatorInterface::class);
        $this->jobServiceMock = self::createMock(JobService::class);

        $this->scriptsPath = getcwd() . '/tests/Mockups';

        $this->eventDispatcherMock = $this->createMock(EventDispatcherInterface::class);
        $this->testRepositoryMock = $this->createMock(TestRepository::class);

        $this->objectManagerMock
            ->expects($this->any())
            ->method('getRepository')
            ->willReturn($this->testRepositoryMock);

        $this->testService = new TestService(
            $this->fileSystem,
            $this->objectManagerMock,
            $this->validatorMock,
            $this->eventDispatcherMock,
            $this->jobServiceMock,
            $this->scriptsPath
        );
    }

    /**
     * @return \Generator
     */
    public function _validatorErrorsProvider()
    {
        yield [
                new ConstraintViolationList([
                    new ConstraintViolation('', '', [], [], '', ''),
                    new ConstraintViolation('', '', [], [], '', '')
                ])
            ];
        yield [ new ConstraintViolationList() ];
    }

    /**
     * @dataProvider _validatorErrorsProvider
     * @param $errors
     */
    public function testIsValid($errors)
    {
        $test = new Test();

        $this->validatorMock
            ->expects($this->once())
            ->method('validate')
            ->with($test)
            ->willReturn($errors);

        $this->assertEquals(!(bool) count($errors), $this->testService->isValid($test));
    }

    public function testGetErrors()
    {
        $this->assertEquals([], $this->testService->getErrors());
    }

    public function testEnd()
    {
        $test = new Test();

        $this->objectManagerMock
            ->expects($this->once())
            ->method('persist')
            ->with($test);

        $this->objectManagerMock
            ->expects($this->once())
            ->method('flush');

        $this->assertEquals($this->testService, $this->testService->end($test));
    }

    /**
     * @return \Generator
     */
    public function _fileTypesProvider()
    {
        yield [ new Test([ 'id' => 123 ]), 'input' ];
        yield [ new Test([ 'id' => 123 ]), 'output' ];
        yield [ new Test([ 'id' => 123 ]), '' ];
        yield [ new Test([ 'id' => 0 ]), 'input' ];

        $parent = new Test([
            'id' => 123
        ]);

        $test = new Test([
            'id' => 456,
            'parent' => $parent
        ]);

        $parent->addChild($test);
        yield [ $test, 'input' ];
    }

    /**
     * @dataProvider _fileTypesProvider
     * @param Test $test
     * @param string $type
     */
    public function testGetFile(Test $test, string $type)
    {
        $this->assertEquals(
            $this->getFile($test, $type),
            $this->testService->getFile($test, $type)
        );
    }

    /**
     * @return \Generator
     */
    public function _testProvider()
    {
        $date = new \DateTime();

        $test = new Test([
            'id' => 123,
            'type' => 1,
            'country' => 'ro',
            'campaign' => 999,
            'createdAt' => $date,
            'finishedAt' => $date
        ]);

        foreach (range(0, 1) as $index) {
            $test->addChild(new Test([
                'id' => $index + 2,
                'type' => 1,
                'parent' => $test,
                'country' => 'ro',
                'campaign' => 999,
                'createdAt' => $date,
                'finishedAt' => $date
            ]));
        }

        yield [ $test ];
    }

    /**
     * @dataProvider _testProvider
     * @param Test $test
     * @param bool $withReruns
     * @param bool $extendDetails
     */
    public function testGetInfo(Test $test, $withReruns = true, $extendDetails = true)
    {
        $myInfo = $this->getInfo($test, $withReruns, $extendDetails);
        if ($withReruns && ! $test->getParent()) {
            $reruns = $this->findAll($test->getChildren(), false, true);

            $this->testRepositoryMock
                ->expects($this->once())
                ->method('findTests')
                ->willReturn($reruns);
        }

        if (isset($reruns) && isset($reruns['data'])) {
            /**
             * @var int $index
             * @var Test $rerun
             */
            foreach ($reruns['data'] as $index => $rerun) {
                $lastMaxChild = $this->getLastChild($test, $rerun['id']) ?: $test;

                $this->testRepositoryMock
                    ->expects($this->at($index * 2 + 1))
                    ->method('getLastChild')
                    ->willReturn($lastMaxChild);
            }
        }

        $this->assertEquals($myInfo, $this->testService->getInfo($test, $withReruns, $extendDetails));
    }

    /**
     * @return \Generator
     */
    public function _parentTestsProvider()
    {
        yield [ new Test() ];
        yield [ false ];
    }

    /**
     * @dataProvider _parentTestsProvider
     * @param Test|bool $return
     */
    public function testGetLastChild($return)
    {
        $test = new Test([ 'id' => 1 ]);

        $this->testRepositoryMock
            ->expects($this->once())
            ->method('getLastChild')
            ->with($test->getId())
            ->willReturn($return);

        $this->assertEquals($return, $this->testService->getLastChild($test));
    }

    public function _testCreateProvider()
    {
            $test = new Test([
                'id' => 123,
                'type' => 1,
                'country' => 'ro',
                'campaign' => '998'
            ]);

            yield [
                $test
            ];

            yield [
                $test,
                [
                    'tech.team.atf@emag.ro'
                ]
            ];

            yield [
                $test,
                [
                    'tech.team.atf@emag.ro',
                    'tech.team.atf@emag.ro'
                ]
            ];
        }

        /**
         * @dataProvider _testCreateProvider
         * @param Test $test
         * @param array $emails
         * @internal param $locked
         */
        public function testCreate(Test $test, array $emails = [])
        {
            $this->objectManagerMock
                ->expects($this->at(0))
                ->method('persist')
                ->with($test);

            $this->objectManagerMock
                ->expects($this->at(1))
                ->method('flush');

            $i = 1;
            foreach ($emails as $email) {
                $this->objectManagerMock
                    ->expects($this->at(++$i))
                    ->method('persist')
                    ->with(new NotificationEmail([
                        'test' => $test,
                        'email' => $email
                    ]));
            }

            if (count($emails)) {
                $this->objectManagerMock
                    ->expects($this->at($i + 1))
                    ->method('flush');
            }

            $this->assertEquals($this->testService, $this->testService->create($test,$emails));
        }

    /**
     * @return \Generator
     */
    public function _testsProvider()
    {
        $date = new \DateTime();

        $ids = [123, 345, 678];
        $tests = new ArrayCollection([]);
        $startFromIndex = 4;

        foreach (range(1, 3) as $index) {
            $test = new Test([
                'id' => $ids[$index - 1],
                'type' => 1,
                'country' => 'ro',
                'campaign' => 999,
                'createdAt' => $date,
                'finishedAt' => $date
            ]);

            $endAtIndex = $startFromIndex + rand(0, 2);

            foreach (range($startFromIndex, $endAtIndex) as $i) {
                $startFromIndex = $endAtIndex;

                $test->addChild(new Test([
                    'id' => $i,
                    'parent' => $test,
                    'type' => 1,
                    'country' => 'ro',
                    'campaign' => 999,
                    'createdAt' => $date,
                    'finishedAt' => $date
                ]));
            }
            $startFromIndex++;

            $tests->add($test);
        }

        yield [
            [
                'total' => $tests->count(),
                'data' => $tests
            ]
        ];
    }

    /**
     * @dataProvider _testsProvider
     * @param array $tests
     */
    public function testFindAll(array $tests)
    {
        $this->testRepositoryMock
            ->expects($this->once())
            ->method('findTests')
            ->willReturn($tests);

        /** @var Test $test */
        foreach ($tests['data'] as $i => $test) {
            if ($test->getParent()) {
                $lastMaxChild = $this->getLastChild($test->getParent()) ?: $test->getParent();
            } else {
                $lastMaxChild = $this->getLastChild($test) ?: $test;
            }

            $this->testRepositoryMock
                ->expects($this->at($i))
                ->method('getLastChild')
                ->willReturn($lastMaxChild);
        }

        $this->assertEquals($this->findAll($tests['data']), $this->testService->findAll());
    }

    /**
     * @param Collection $tests
     * @param bool $reruns
     * @param bool $details
     * @return array
     */
    private function findAll(
        Collection $tests,
        bool $reruns = false,
        bool $details = false
    ): array
    {
        $tests = [
            'data' => $tests,
            'total' => count($tests),
        ];

        $this->testRepositoryMock
            ->expects($this->once())
            ->method('findTests')
            ->willReturn($tests);

        $results = [];

        /** @var Test $test */
        foreach ($tests['data'] as $i => $test) {
            $testInfo = $this->getInfo($test, $reruns, $details);

            if ($test->getParent()) {
                /** @var Test|null $lastChild */
                $lastChild = $this->getLastChild($test->getParent());
            } else {
                /** @var Test|null $lastChild */
                $lastChild = $this->getLastChild($test) ?: $test;
            }

            $testInfo['hasRunningChildren'] = !$lastChild ? false : !boolval($lastChild->getFinishedAt());

            $testInfo['shouldRerun'] = false;

            if ($lastChild) {
                /** @var File|bool $lastChildOutputFile */
                $lastChildOutputFile = $this->getFile($lastChild, 'retry');

                if ($lastChildOutputFile) {
                    $testInfo['shouldRerun'] = $this->fileIsValid($lastChildOutputFile);
                }
            } else {
                $testInfo['shouldRerun'] = $this->fileIsValid($this->scriptsPath . "/csv/business-{$test->getId()}.csv");
            }

            $results[] = $testInfo;
        }

        return [
            'total' => $tests['total'],
            'data' => $results
        ];
    }

    /**
     * @param Test $test
     * @param bool $withReruns
     * @param bool $extendDetails
     * @return array
     */
    private function getInfo(Test $test, bool $withReruns = true, bool $extendDetails = true): array
    {
        $reruns = [];

        if ($withReruns && ! $test->getParent()) {
            $reruns = $this->findAll($test->getChildren(), false, true);
        }

        $returnData = [
            'id'         => $test->getId(),
            'type'       => $test->getType(),
            'country'    => $test->getCountry(),
            'campaign'   => $test->getCampaign(),
            'seller'   => $test->getSeller(),
            'createdAt'  => $test->getCreatedAt(),
            'finishedAt' => $test->getFinishedAt(),
        ];

        $noOfLines = 0;

        $inputFile = $this->scriptsPath . "/csv/input-{$test->getId()}.csv";
        $input = $this->getFile($test, 'input');

        if ($input) {
            $inputFile = $input->getPathname();
        }

        $retryFile = $this->scriptsPath . "/csv/retry-{$test->getId()}.csv";
        $businessFile = $this->scriptsPath . "/csv/business-{$test->getId()}.csv";

        $files = [
            'input' => [
                'empty' => true,
                'size'  => 0
            ],
            'retry' => [
                'empty' => true,
                'size'  => 0
            ],
            'output' => [
                'empty' => true,
                'size'  => 0
            ],
        ];

        if ($extendDetails) {
            $issues = [
                'total'  => 0,
                'cart'   => 0,
                'image'  => 0,
                'price'  => 0,
            ];

            if ($this->fileSystem->exists($inputFile) && ($handle = fopen($inputFile, 'r')) !== FALSE) {
                fgetcsv($handle);

                while (fgetcsv($handle) !== false) {
                    $noOfLines++;
                }

                $files['input']['empty'] = !boolval($noOfLines);
                $files['input']['size'] = filesize($inputFile);

                fclose($handle);
            }

            if (isset($retryFile) && $this->fileSystem->exists($retryFile) && ($handle = fopen($retryFile, 'r')) !== FALSE) {
                fgetcsv($handle);

                while (($data = fgetcsv($handle)) !== FALSE) {
                    $issues['cart']   += $data[7]  != 'Ok';
                    $issues['image']  += $data[8]  != 'Ok';
                    $issues['price']  += $data[9]  != 'Ok';

                    $issues['total']++;
                }

                $files['retry']['empty'] = !boolval($issues['total']);
                $files['retry']['size'] = filesize($retryFile);

                fclose($handle);
            }

            $returnData['issues'] = $issues;
            $returnData['inputNoOfLines'] = $noOfLines;
        } else {
            if ($this->fileIsValid($inputFile)) {
                $files['input']['empty'] = false;
                $files['input']['size'] = filesize($inputFile);
            }

            if (isset($retryFile) && $this->fileIsValid($retryFile)) {
                $files['retry']['empty'] = false;
                $files['retry']['size'] = filesize($retryFile);
            }
        }

        if ($this->fileIsValid($businessFile)) {
            $files['output']['empty'] = false;
            $files['output']['size'] = filesize($businessFile);
        }

        $returnData = array_merge($returnData, $files);

        return !$withReruns || $test->getParent() ?
            $returnData :
            [
                'data' => $returnData,
                'reruns' => $reruns,
            ];
    }

    /**
     * @param Test $test
     * @param int $maxId
     * @return Test|null
     */
    private function getLastChild(Test $test, ?int $maxId = null): ?Test
    {
        $children = $test->getChildren();

        if ($maxId) {
            $children = $children->filter(function($child) use ($maxId) {
                return $child->getId() < $maxId;
            });
        }

        return $children->last() ?: null;
    }

    /**
     * @param Test $test
     * @param string $type
     * @return bool|File
     */
    private function getFile(Test $test, string $type)
    {
        if ($type == 'output') {
            $type = 'business';
        }

        $parent = $test->getParent();
        if ($parent && $type == 'input') {
            $lastMaxChild = $this->getLastChild($parent, $test->getId()) ?: $parent;
            $type = 'retry';
            $id = $lastMaxChild->getId();
        } else {
            $id = $test->getId();
        }

        $file = $this->scriptsPath . "/csv/{$type}-{$id}.csv";

        return $this->fileSystem->exists($file) ? new File($file): false;
    }

    private function fileIsValid($file): bool
    {
        if ($this->fileSystem->exists($file) && ($handle = fopen($file, 'r')) !== FALSE) {
            fgetcsv($handle);
            $secondLine = fgetcsv($handle) !== false;
            fclose($handle);

            return $secondLine;
        }

        return false;
    }
}
