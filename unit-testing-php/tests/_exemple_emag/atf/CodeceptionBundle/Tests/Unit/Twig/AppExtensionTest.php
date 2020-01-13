<?php
namespace Emag\Core\CodeceptionBundle\Tests\Unit\Twig;

use Emag\Core\CodeceptionBundle\Entity\Test;
use Emag\Core\CodeceptionBundle\Entity\TestStatus;
use Emag\Core\CodeceptionBundle\Service\TestStatusService;
use Emag\Core\CodeceptionBundle\Twig\AppExtension;
use Emag\Core\UserBundle\Entity\User;
use Symfony\Bundle\FrameworkBundle\Routing\Router;
use Symfony\Component\Routing\Route;
use Symfony\Component\Routing\RouteCollection;
use Symfony\Component\Security\Core\Authentication\Token\Storage\TokenStorage;
use Symfony\Component\Security\Core\Authentication\Token\UsernamePasswordToken;
use Symfony\Component\Security\Core\Authorization\AuthorizationChecker;
use Symfony\Component\Security\Core\Authorization\AuthorizationCheckerInterface;

class AppExtensionTest extends \PHPUnit_Framework_TestCase
{
    /** @var Router|\PHPUnit_Framework_MockObject_MockObject */
    private $routerMock;

    /** @var TokenStorage */
    private $securityTokenStorageMock;

    /** @var AuthorizationChecker|\PHPUnit_Framework_MockObject_MockObject */
    private $authorizationCheckerMock;

    /** @var TestStatusService|\PHPUnit_Framework_MockObject_MockObject */
    private $testStatusServiceMock;

    /** @var AppExtension */
    private $appExtension;

    public function setUp()
    {
        parent::setUp();

        $this->routerMock = $this->getMockBuilder(Router::class)->disableOriginalConstructor()->getMock();

        $this->securityTokenStorageMock = $this->getMockBuilder(TokenStorage::class)->disableOriginalConstructor()->getMock();
        $this->securityTokenStorageMock = new TokenStorage();

        $this->authorizationCheckerMock = $this->getMockBuilder(AuthorizationCheckerInterface::class)->disableOriginalConstructor()->getMock();

        $this->testStatusServiceMock = $this->getMockBuilder(TestStatusService::class)->disableOriginalConstructor()->getMock();

        $this->appExtension = new AppExtension($this->routerMock, $this->securityTokenStorageMock, $this->authorizationCheckerMock, $this->testStatusServiceMock);
    }

    public function testDurationFormatWithValidNumber()
    {
        $number = 50;

        $response = $this->appExtension->durationFormat($number);
        $this->assertEquals('50 seconds', $response);
    }

    public function testDurationFormatWithInvalidNumber()
    {
        $number = 'Something invalid';

        $response = $this->appExtension->durationFormat($number);
        $this->assertEquals('', $response);
    }

    public function testDurationFormatWithLargeNumber()
    {
        $number = 999999;
        $minutes = floor($number / 60);

        $response = $this->appExtension->durationFormat($number);
        $this->assertEquals($minutes . ' minutes ' . $number % 60  . ' seconds', $response);
    }

    public function testGetFilters()
    {
        $response = $this->appExtension->getFilters();

        $this->assertEquals(array(
            new \Twig_SimpleFilter('json', [$this->appExtension, 'jsonFilter']),
            new \Twig_SimpleFilter('id', [$this->appExtension, 'getEntityIdOrLiteralZero']),
            new \Twig_SimpleFilter('durationFormat', [$this->appExtension, 'durationFormat']),
            new \Twig_SimpleFilter('ucfirst', [$this->appExtension, 'ucfirst']),
        ), $response);
    }

    public function testGetFunction()
    {
        $response = $this->appExtension->getFunctions();

        $this->assertEquals([
            new \Twig_SimpleFunction('get_role_permissions', [ $this->appExtension, 'getRolePermissions'], ['is_safe' => array('html') ]),
            new \Twig_SimpleFunction('get_test_statuses', [ $this->appExtension, 'getTestStatuses' ], [ 'is_safe' => array('html') ])
        ], $response);
    }

    public function testUcFirstFilter()
    {
        $this->assertEquals('SOmE TExt', $this->appExtension->ucfirst('sOmE TExt'));
    }

    public function testJsonFilter()
    {
        $data = [ 'key' => 'val' ];
        $this->assertEquals(json_encode($data), $this->appExtension->jsonFilter($data));
    }

    public function testGetEntityIdOrLiteralZeroWithValidEntity()
    {
        $test = new Test();

        $reflectionProperty = new \ReflectionProperty($test, 'id');
        $reflectionProperty->setAccessible(true);
        $reflectionProperty->setValue($test, 1);

        $this->assertEquals(1, $this->appExtension->getEntityIdOrLiteralZero($test));
    }

    public function testGetEntityIdOrLiteralZeroWithInvalidEntity()
    {
        $test = new \stdClass();

        $this->assertEquals(0, $this->appExtension->getEntityIdOrLiteralZero($test));
    }

    public function testGetRolePermissions()
    {
        $routes = new RouteCollection();
        $routes->add('emag_core.codeception.application.testing_plan.index', new Route('/'));
        $routes->add('emag_core.codeception.application.testing_plan.edit', new Route('/1'));

        $this->routerMock
            ->expects($this->any())
            ->method('getRouteCollection')
            ->willReturn($routes);

        $this->authorizationCheckerMock
            ->expects($this->any())
            ->method('isGranted')
            ->willReturn(true);

        $userToken = new UsernamePasswordToken(new User(), '', 'Key');
        $this->securityTokenStorageMock->setToken($userToken);

        $this->assertEquals('<script>var rolePermissions = { "emag_core.codeception.application.testing_plan.index": true,"emag_core.codeception.application.testing_plan.edit": true};</script>', $this->appExtension->getRolePermissions());
    }

    public function testGetName()
    {
        $this->assertEquals('json_extension', $this->appExtension->getName());
    }

    public function statusesData()
    {
        return [[true], [false]];
    }

    /**
     * @param $yes
     * @dataProvider statusesData
     */
    public function testGetTestStatuses($yes)
    {
        $this->testStatusServiceMock
            ->expects($this->once())
            ->method('findBy')
            ->willReturn(new TestStatus());

        $this->appExtension->getTestStatuses($yes);
    }
}