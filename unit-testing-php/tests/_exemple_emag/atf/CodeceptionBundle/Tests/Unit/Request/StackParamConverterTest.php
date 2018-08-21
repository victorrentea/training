<?php

namespace Emag\Core\CodeceptionBundle\Request\ParamConverter;

use Emag\Core\CodeceptionBundle\Entity\Stack;
use Emag\Core\CodeceptionBundle\Service\StackService;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;
use Symfony\Component\HttpFoundation\Request;

/**
 * Class StackParamConverterTest
 * @package Emag\Core\CodeceptionBundle\Request\ParamConverter
 */
class StackParamConverterTest extends \PHPUnit_Framework_TestCase
{
    /** @var StackParamConverter */
    protected $stackParamConverter;

    /** @var StackService|\PHPUnit_Framework_MockObject_MockObject */
    protected $stackServiceMock;

    public function setUp()
    {
        parent::setUp();

        $this->stackParamConverter = new StackParamConverter();

        $this->stackServiceMock = $this->getMockBuilder(StackService::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->stackParamConverter->setStackService($this->stackServiceMock);
    }

    /**
     * @return \Generator
     */
    public function _runParams()
    {
        yield [
            'request' => [
                'stack' => 'Atf Environment'
            ]
            , 'result' =>  new Stack()
        ];

        yield [
            'request' => [
                'stack' => 2
            ]
            , 'result' => new Stack()
        ];

        yield [
            'request' => [
                'stack' => 'weird'
            ]
            , 'result' => null
        ];
    }

    /**
     * @dataProvider _runParams
     *
     * @param array $request
     * @param Stack|null $result
     */
    public function testApply(array $request = [], ?Stack $result = null)
    {
        /** @var Request */
        $requestMock = new Request([], [], [ 'stack' => $request['stack'] ]);

        $stackParam = $requestMock->attributes->get('stack');

        /** @var ParamConverter|\PHPUnit_Framework_MockObject_MockObject $paramConverterMock */
        $paramConverterMock = $this->getMockBuilder(ParamConverter::class)
            ->disableOriginalConstructor()
            ->getMock();

        $this->stackServiceMock
            ->expects($this->once())
            ->method('findOneBy')
            ->with([
                ctype_digit($stackParam) ? 'id' : 'name' => $stackParam
            ])
            ->willReturn($result);

        $this->stackParamConverter->apply($requestMock, $paramConverterMock);
    }
}
