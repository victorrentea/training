<?php
namespace Emag\Core\CodeceptionBundle\Tests\Unit\Service;

use Doctrine\Common\Persistence\ObjectManager;
use Doctrine\ORM\EntityManager;
use Emag\Core\CodeceptionBundle\Command\GenerateATFHelperClassCommand;
use Emag\Core\CodeceptionBundle\Entity\Stack;
use Emag\Core\CodeceptionBundle\Repository\StackRepository;
use Emag\Core\CodeceptionBundle\Service\GeneratorService;
use Symfony\Bundle\FrameworkBundle\Test\KernelTestCase;
use Symfony\Component\Console\Input\ArrayInput;
use Symfony\Component\Console\Output\BufferedOutput;
use Symfony\Component\DependencyInjection\ContainerInterface;
use Symfony\Component\Filesystem\Filesystem;

class GeneratorCommandTest extends \PHPUnit_Framework_TestCase
{
    /**
     * @throws \Twig_Error_Loader
     */
    public function testGenerateHelperCommand()
    {
        /** @var \Twig_Environment|\PHPUnit_Framework_MockObject_MockObject $twigEnvironment */
        $twigEnvironment = $this->getMockBuilder(\Twig_Environment::class)
            ->disableOriginalConstructor()
            ->getMock();

        /** @var ObjectManager|\PHPUnit_Framework_MockObject_MockObject $objectManager */
        $objectManager = $this->getMockBuilder(ObjectManager::class)
            ->disableOriginalConstructor()
            ->getMock();

        $generatorService = new GeneratorService($twigEnvironment, $objectManager);

        /** @var EntityManager|\PHPUnit_Framework_MockObject_MockObject $entityManager */
        $entityManager = $this->getMockBuilder(EntityManager::class)
            ->disableOriginalConstructor()
            ->getMock();

        $codeceptionPath = dirname(__FILE__);

        $fileSystemMock = $this->getMock(Filesystem::class);

        $generateCommand = new GenerateATFHelperClassCommand(
            $generatorService,
            $entityManager,
            $codeceptionPath,
            $fileSystemMock
        );

        $stacksRepoMock = $this->getMockBuilder(StackRepository::class)
            ->disableOriginalConstructor()
            ->getMock();

        $entityManager->expects($this->once())
            ->method('getRepository')
            ->with(Stack::class)
            ->willReturn($stacksRepoMock);

        $stacks = [];
        $stacksRepoMock->expects($this->any())
            ->method("findBy")
            ->willReturn($stacks);

        $objectManager->expects($this->once())
            ->method('getRepository')
            ->with(Stack::class)
            ->willReturn($stacksRepoMock);

        $this->assertEquals(0, $generateCommand->run(new ArrayInput([]), new BufferedOutput()));
    }

}
