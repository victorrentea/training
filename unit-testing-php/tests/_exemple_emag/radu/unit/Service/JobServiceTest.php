<?php
namespace App\Tests\Service
{

    use App\Entity\Test;
    use App\Service\JobService;
    use PHPUnit\Framework\MockObject\MockObject;
    use PHPUnit\Framework\TestCase;
    use Symfony\Component\Filesystem\Filesystem;

    class JobServiceTest extends TestCase
    {
        /** @var Filesystem|MockObject */
        private $fileSystemMock;

        /** @var JobService $jobService */
        private $jobService;

        /** @var string $scriptsPath */
        private $scriptsPath;

        public function setUp()
        {
            parent::setUp();

            $this->fileSystemMock = self::createMock(Filesystem::class);

            $this->scriptsPath = getcwd() . '/tests/Mockups';

            $this->jobService = new JobService(
                $this->fileSystemMock,
                $this->scriptsPath,
                'test',
                10,
                10,
                100 
            );

            require_once $this->scriptsPath . '/constants.php';
        }

        /**
         * @return \Generator
         */
        public function fileExistsProvider()
        {
            yield [ true ];
            yield [ false ];
        }

        /**
         * @dataProvider fileExistsProvider
         * @param bool $fileExists
         */
        public function testIsLocked($fileExists)
        {
            $this->fileSystemMock
                ->expects($this->once())
                ->method('exists')
                ->willReturn($fileExists);

            $this->assertEquals($fileExists, $this->jobService->isLocked());
        }

        public function testRun()
        {
            $this->assertTrue(
                $this->jobService->run(new Test([
                    'id' => 123,
                    'type' => 1,
                    'country' => 'ro',
                    'campaign' => 998,
                    'seller' => null
                ]))
            );
        }

        /**
         * @return \Generator
         */
        public function envsProvider()
        {
            yield [ 'dev' ];
            yield [ 'production' ];
        }

        /**
         * @dataProvider envsProvider
         * @param string $env
         */
        public function testGetCommand(string $env)
        {
            $test = new Test([
                'id' => 123,
                'type' => 1,
                'country' => 'ro',
                'seller' => null,
                'campaign' => 998,
            ]);

            $devMaxProducts = 100;
            $requestsPerServer = 10;
            $batchSize = 10;
            $col = array_flip(CSV_HEAD);
            $devParameters = $env == 'dev' ? ' -m ' . $devMaxProducts : '';

            $jobService = new JobService(
                $this->fileSystemMock,
                $this->scriptsPath,
                $env,
                $requestsPerServer,
                $batchSize,
                $devMaxProducts
            );

            $this->assertEquals(
                $this->scriptsPath . "/campaign.sh" .
                " -u \"{$test->getId()}\"" .
                " -t \"{$test->getType()}\"" .
                " -l \"{$test->getCountry()}\"" .
                " -i \"{$test->getCampaign()}\"" .
                " -q 0" .
                " --structure \"" . 
                    "[{$col['Offer ID']}," .
                    " {$col['Part number']}," .
                    " {$col['Part number key']}," .
                    " {$col['Product name']}," .
                    " {$col['Vendor']}," .
                    " {$col['CMS Price']}," .
                    " {$col['Site price']}," .
                    " {$col['Buy button']}," .
                    " {$col['URL']}]\"" .
                " --requests {$requestsPerServer}" .
                " --size {$batchSize}" .
                $devParameters .
                " -a -c",
                $jobService->getCommand($test)
            );
        }
    }
}

namespace App\Service
{
    /**
     * @param $command
     * @param $returnVar
     */
    function system($command, &$returnVar)
    {
        $returnVar = 0;
    }
}
