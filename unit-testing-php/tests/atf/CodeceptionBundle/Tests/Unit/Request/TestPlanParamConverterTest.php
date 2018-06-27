<?php

namespace Emag\Core\CodeceptionBundle\Request\ParamConverter;

use Emag\Core\CodeceptionBundle\Entity\TestingPlan;
use Emag\Core\CodeceptionBundle\Service\TestingPlanService;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;
use Symfony\Component\HttpFoundation\Request;

/**
 * Class TestPlanParamConverterTest
 * @package Emag\Core\CodeceptionBundle\Request\ParamConverter
 */
class TestPlanParamConverterTest extends \PHPUnit_Framework_TestCase
{
    /** @var TestPlanParamConverter */
    protected $testPlanParamConverter;

    /** @var TestingPlanService|\PHPUnit_Framework_MockObject_MockObject */
    protected $testPlanServiceMock;

    public function setUp()
    {
        parent::setUp();

        $this->testPlanParamConverter = new TestPlanParamConverter();

        $this->testPlanServiceMock = $this->getMockBuilder(TestingPlanService::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->testPlanParamConverter->setTestPlanService($this->testPlanServiceMock);
    }

    /**
     * @return \Generator
     */
    public function _runParams()
    {
        yield [
            'request' => [
                'testingPlan' => 'ATF Test Plan'
            ]
            , 'result' => new TestingPlan()
        ];

        yield [
            'request' => [
                'testingPlan' => 2
            ]
            , 'result' => new TestingPlan()
        ];

        yield [
            'request' => [
                'testingPlan' => 'weird'
            ]
            , 'result' => null
        ];
    }

    /**
     * @dataProvider _runParams
     *
     * @param array $request
     * @param TestingPlan|null $result
     */
    public function testApply(array $request = [], ?TestingPlan $result = null)
    {
        /** @var Request $request*/
        $requestMock = new Request([], [], [ 'testingPlan' => $request['testingPlan'] ]);

        $testPlanParam = $requestMock->attributes->get('testingPlan');

        /** @var ParamConverter|\PHPUnit_Framework_MockObject_MockObject $paramConverterMock */
        $paramConverterMock = $this->getMockBuilder(ParamConverter::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->testPlanServiceMock
            ->expects($this->once())
            ->method('findOneBy')
            ->with([
                ctype_digit($testPlanParam) ? 'id' : 'name' => $testPlanParam
            ])
            ->willReturn($result);

        $this->testPlanParamConverter->apply($requestMock, $paramConverterMock);
    }
}