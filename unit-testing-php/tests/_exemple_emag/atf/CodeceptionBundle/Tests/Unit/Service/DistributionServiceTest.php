<?php

namespace Emag\Core\CodeceptionBundle\Tests\Unit\Service;


use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Persistence\ObjectManager;
use Emag\Core\CodeceptionBundle\Entity\Country;
use Emag\Core\CodeceptionBundle\Service\CountryService;
use Emag\Core\CodeceptionBundle\Service\DistributionService;
use GuzzleHttp\Client;
use GuzzleHttp\Psr7\Response;
use Emag\Core\CodeceptionBundle\Repository\DistributionRepository;
use Emag\Core\CodeceptionBundle\Entity\Distribution;

class DistributionServiceTest extends \PHPUnit_Framework_TestCase
{
    /** @var DistributionService $distributionService */
    private $distributionService;

    /** @var Client|\PHPUnit_Framework_MockObject_MockObject */
    private $clientMock;

    /** @var CountryService|\PHPUnit_Framework_MockObject_MockObject */
    private $countryServiceMock;

    public function setUp()
    {
        /** @var Client|\PHPUnit_Framework_MockObject_MockObject clientMock */
        $this->clientMock = $this
            ->getMockBuilder(Client::class)
            ->setMethods(['get'])
            ->disableOriginalConstructor()
            ->getMock();

        /** @var CountryService|\PHPUnit_Framework_MockObject_MockObject countryServiceMock */
        $this->countryServiceMock = $this
            ->getMockBuilder(CountryService::class)
            ->disableOriginalConstructor()
            ->getMock();

        /** @var DistributionService distributionService */
        $this->distributionService = new DistributionService();

        $this->distributionService->setClient($this->clientMock);
        $this->distributionService->setCountryService($this->countryServiceMock);
    }

    public function testFetchAll()
    {
        $response = new Response(200, [], '{"mktp-spk": {"distributions": {"mktp-spk-bg": {"name": "Marketplace Order Spike (BG)","organization-scope": "bg-scope","deployment": {"environment": {"dev": {"machine": {"template": "mktp_spk-dev","slots": 1,"allocation-service": "openstack"}},"test": {"machine": {"template": "mktp_spk-dev","slots": 1,"allocation-service": "openstack"}}}}},"mktp-spk-pl": {"name": "Marketplace Order Spike (PL)","organization-scope": "pl-scope","deployment": {"environment": {"dev": {"machine": {"template": "mktp_spk-dev","slots": 1,"allocation-service": "openstack"}},"test": {"machine": {"template": "mktp_spk-dev","slots": 1,"allocation-service": "openstack"}}}}},"mktp-spk-hu": {"name": "Marketplace Order Spike (HU)","organization-scope": "hu-scope","deployment": {"environment": {"dev": {"machine": {"template": "mktp_spk-dev","slots": 1,"allocation-service": "openstack"}},"test": {"machine": {"template": "mktp_spk-dev","slots": 1,"allocation-service": "openstack"}}}}},"mktp-spk-ro": {"name": "Marketplace Order Spike (RO)","organization-scope": "ro-scope","deployment": {"environment": {"dev": {"machine": {"template": "mktp_spk-dev","slots": 1,"allocation-service": "openstack"}},"test": {"machine": {"template": "mktp_spk-dev","slots": 1,"allocation-service": "openstack"}}}}}}}}');

        $this->clientMock
            ->expects($this->once())
            ->method('get')
            ->willReturn($response);

        $this->assertEquals([
            'mktp-spk' => [
                "mktp-spk-bg",
                "mktp-spk-pl",
                "mktp-spk-hu",
                "mktp-spk-ro",
            ]
        ], $this->distributionService->fetchAll());
    }

    public function testInsert()
    {
        $countriesCollection = [];

        foreach (['RO', 'BG', 'HU', 'PL'] as $country) {
            ${strtolower($country)} = new Country();
            ${strtolower($country)}->setCode($country);
            ${strtolower($country)}->setDistributions(new ArrayCollection());

            $countriesCollection[strtolower($country)] = ${strtolower($country)};
        }

        $this->countryServiceMock
            ->expects($this->any())
            ->method('get')
            ->willReturn($countriesCollection);

        /** @var ObjectManager|\PHPUnit_Framework_MockObject_MockObject $objectManagerMock */
        $objectManagerMock = $this->getMockBuilder(ObjectManager::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->distributionService->setManager($objectManagerMock);

        $objectManagerMock
            ->expects($this->any())
            ->method('persist');

        $objectManagerMock
            ->expects($this->once())
            ->method('flush');

        $this->assertEquals([
            'success' => true
        ], $this->distributionService->insert([
            'mktp-spk' => [
                "mktp-spk-bg",
                "mktp-spk-pl",
                "mktp-spk-hu",
                "mktp-spk-ro",
            ],
            'mktp-spk2' => [
                "mktp-spk-rbh"
            ],
        ]));
    }

    public function testFilterUniqueAllCoreDistributions()
    {
        $this->assertEquals(
            [
                'mktp-spk' => [
                    1 => "mktp-spk-pl",
                    2 => "mktp-spk-hu",
                ],
                'mktp-spk2' => [
                    "mktp-spk-rbh"
                ],
            ],
            $this->distributionService->filterUniqueAllCoreDistributions(
                [
                    'mktp-spk' => [
                        "mktp-spk-bg",
                        "mktp-spk-pl",
                        "mktp-spk-hu",
                        "mktp-spk-ro",
                    ],
                    'mktp-spk2' => [
                        "mktp-spk-rbh"
                    ],
                ],
                [
                    [
                        'name' => 'mktp-spk-bg',
                        'code' => 'mktp-spk-bg',
                        'countries' => [
                            [
                                'id' => 2,
                                'name' => 'Bulgaria',
                                'code' => 'BG',
                            ]
                        ],
                    ],
                    [
                        'name' => 'mktp-spk-ro',
                        'code' => 'mktp-spk-ro',
                        'countries' => [
                            [
                                'id' => 1,
                                'name' => 'Romania',
                                'code' => 'RO',
                            ]
                        ],
                    ],
                ]
            )
        );
    }

    public function testFilterStackCoreDistributions() {
        /** @var ObjectManager|\PHPUnit_Framework_MockObject_MockObject $managerMock */
        $managerMock = $this->getMockBuilder(ObjectManager::class)
                ->disableOriginalConstructor()
                ->getMock();

        $distributionRepositoryMock = $this->getMockBuilder(DistributionRepository::class)
            ->disableOriginalConstructor()
            ->getMock();

        $distribution = new Distribution;
        $distribution->setName("atf");

        $distributionRepositoryMock
            ->method("findDistributions")
            ->willReturn(new ArrayCollection([
                $distribution
            ]));

        $managerMock
            ->method("getRepository")
            ->willReturn($distributionRepositoryMock);

        $this->distributionService->setManager($managerMock);

        $res = $this->distributionService->filterStackCoreDistributions(["atf", "eos", "mktp"]);

        $this->assertEquals(["atf"], $res);
    }
}