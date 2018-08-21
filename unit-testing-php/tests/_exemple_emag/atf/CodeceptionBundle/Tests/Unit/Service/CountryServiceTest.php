<?php
namespace Emag\Core\CodeceptionBundle\Tests\Unit\Service;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Persistence\ObjectManager;
use Emag\Core\CodeceptionBundle\Entity\Country;
use Emag\Core\CodeceptionBundle\Entity\Distribution;
use Emag\Core\CodeceptionBundle\Repository\CountryRepository;
use Emag\Core\CodeceptionBundle\Service\CountryService;

class CountryServiceTest extends \PHPUnit_Framework_TestCase
{
    /** @var ObjectManager|\PHPUnit_Framework_MockObject_MockObject $objectManagerMock */
    private $objectManagerMock;

    /** @var CountryService $countryService */
    private $countryService;

    public function setUp()
    {
        /** @var ObjectManager|\PHPUnit_Framework_MockObject_MockObject objectManagerMock */
        $this->objectManagerMock = $this->getMockBuilder(ObjectManager::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->countryService = new CountryService();
        $this->countryService->setManager($this->objectManagerMock);
    }

    public function testGet()
    {
        $country1 = new Country();
        $country1->setCode('code1');
        $country2 = new Country();
        $country2->setCode('code2');
        $country3 = new Country();
        $country3->setCode('code3');

        $countries = new ArrayCollection([
            $country1,
            $country2,
            $country3,
        ]);

        $countryRepositoryMock = $this->getMockBuilder(CountryRepository::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->objectManagerMock
            ->expects($this->once())
            ->method('getRepository')
            ->willReturn($countryRepositoryMock);

        $countryRepositoryMock
            ->expects($this->once())
            ->method('findBy')
            ->willReturn($countries);

        $result = [];

        /** @var Country $country */
        foreach ($countries as $country) {
            $result[$country->getCode()] = $country;
        }

        $this->assertEquals($result, $this->countryService->get());
    }

    public function testFindCountries()
    {
        $country1 = new Country();
        $country1->setCode('code1');
        $country1->setName('name1');
        $country2 = new Country();
        $country2->setCode('code2');
        $country1->setName('name2');
        $country3 = new Country();
        $country3->setCode('code3');
        $country1->setName('name3');

        $countries = new ArrayCollection([
            $country1,
            $country2,
            $country3,
        ]);

        $distribution = new Distribution();
        $distribution->setCountries($countries);

        $result = [];

        /** @var Country $country */
        foreach ($countries as $country) {
            $result[] = [
                'id' => $country->getId(),
                'name' => $country->getName(),
                'code' => $country->getCode(),
            ];
        }

        $this->assertEquals($result, $this->countryService->findCountries($distribution));
    }
}