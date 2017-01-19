<?php
namespace video;
include "FizzBuzz.php";

class FizzBuzzTest extends \PHPUnit_Framework_TestCase
{

    private $instance;
    private $arr;

    public function setUp()
    {
        $this->instance = new FizzBuzz( array(
            'Fizz' => function($n) {
                return ($n%3===0) || (false !== strpos($n, '3'));
            },
            'Buzz' => function($n) {
                return $n%5===0;
            },
            'Wizz' => function($n) {
                return $n%7===0;
            },
            'Gigel' => function($n) {
                return $n%11===0;
    },
        ));
        $this->arr = $this->instance->generate(105);
    }

    public function testGenerate100_has100Elements()
    {
        $arr = $this->instance->generate(100);
        $this->assertEquals(100, count($arr));
    }

    public function testGenerate10_has10Elements()
    {
        $arr = $this->instance->generate(10);
        $this->assertEquals(10, count($arr));
    }

    public function testFirstElementIsOne()
    {
        $this->assertEquals(1, $this->arr[0]);
    }

    public function testIf3rdElementFizz()
    {
        $this->assertEquals("Fizz", $this->arr[2]);
    }

    public function testIf5thElementBuzz()
    {
        $this->assertEquals("Buzz", $this->arr[4]);
    }

    public function testIf6thElementFizz()
    {
        $this->assertEquals("Fizz", $this->arr[5]);
    }

    public function testIf7thElementBuzz()
    {
        $this->assertEquals("Wizz", $this->arr[6]);
    }

    public function testIf10thElementBuzz()
    {
        $this->assertEquals("Buzz", $this->arr[9]);
    }

    public function testIf15thElementBuzz()
    {
        $this->assertEquals("FizzBuzz", $this->arr[14]);
    }

    public function testIf105thElementBuzz()
    {
        $this->assertEquals("FizzBuzzWizz", $this->arr[104]);
    }

    public function testIf11thElementIsGigel()
    {
        $this->assertEquals("Gigel", $this->arr[10]);
    }

    public function testIf35thElementIsFizzBuzzWizz()
    {
        $this->assertEquals("FizzBuzzWizz", $this->arr[34]);
    }

    public function testStrpos()
    {
        $this->assertTrue(0 === strpos('35', '3'));
    }
}
