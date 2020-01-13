<?php
namespace Emag\Core\CodeceptionBundle\Tests\Unit\Service;

use Doctrine\Common\Persistence\ObjectManager;
use Doctrine\Common\Persistence\ObjectRepository;
use Emag\Core\CodeceptionBundle\Entity\Distribution;
use Emag\Core\CodeceptionBundle\Entity\Environment;
use Emag\Core\CodeceptionBundle\Entity\Stack;
use Emag\Core\CodeceptionBundle\Service\GeneratorService;

class GeneratorServiceTest extends \PHPUnit_Framework_TestCase
{
    public function getTestValues()
    {

        return [
            [
                [],
                __DIR__."/Mocks/ATFHelper0Stacks.php"
            ],
            [
                [
                    1 => [
                        'scm' => 'http://scm.emag.local',
                        'atf' => 'http://atf.emag.local',
                    ]
                ],
                __DIR__."/Mocks/ATFHelper1Stack2Distributions.php"
            ],
            [
                [
                    1 => [
                        'scm' => 'http://scm.emag.local',
                        'atf' => 'http://atf.emag.local',
                    ]
                    ,
                    2 => [
                        'scm' => 'http://scm.emag.local2',
                        'atf' => 'http://atf.emag.local2',
                    ]
                ],
                __DIR__."/Mocks/ATFHelper2Stacks4Distributions.php"
            ]
        ];
    }

    /**
     * @dataProvider getTestValues
     */
    public function testGenerateHelper($distributions, $file)
    {
        $tlf = new \Twig_Loader_Filesystem();
        $tlf->addPath(__DIR__.'/../../../Resources/views');

        $engine = new \Twig_Environment($tlf);
        $objectManager = $this->getMockBuilder(ObjectManager::class)
            ->disableOriginalConstructor()
            ->getMock();

        $repositoryMock = $this->getMockBuilder(ObjectRepository::class)
            ->disableOriginalConstructor()
            ->getMock();

        $objectManager->expects($this->once())
            ->method('getRepository')
            ->with(Stack::class)
            ->willReturn($repositoryMock);

        $stacks = [];

        foreach ($distributions as $stackId => $distributionList) {
            $stack = new Stack();
            $stack->setId($stackId);

            foreach ($distributionList as $distr => $host) {
                $distribution = new Distribution();
                $distribution->setName($distr);

                $environment = new Environment();
                $environment->setHost($host);
                $environment->setDistribution($distribution);

                $stack->addEnvironment($environment);
            }

            $stacks[] = $stack;
        }

        $repositoryMock->expects($this->once())
            ->method("findBy")
            ->willReturn($stacks);

        $generatorService = new GeneratorService($engine, $objectManager);

        $helperClass = $generatorService->generateHelperClass($stacks, 'CodeceptionHelpers/ATFHelper.html.twig');

        $expected = file_get_contents($file);
        $expected = str_replace([" ", "\r\n"], ["", ""], $expected);

        $this->assertEquals($expected, str_replace([" ", "\n"], ["", ""], $helperClass));
    }
}