<?php
namespace video;


include 'FizzBuzz.php';
include '/Framework/Assert/Functions.php';

class FizzBuzzTest extends \PHPUnit\Framework\TestCase {

  public function test1() {
      self::assertEquals('1', \FizzBuzz::getStringForInt(1));
  }

  public function test2() {
    $this->assertEquals('2', \FizzBuzz::getStringForInt(2));
  }

  public function test3() {
    $this->assertEquals('Fizz', \FizzBuzz::getStringForInt(3));
  }

  public function test5()
  {
      $this->assertEquals('Buzz', \FizzBuzz::getStringForInt(5));
  }

    public function test6()
    {
        $this->assertEquals('Fizz', \FizzBuzz::getStringForInt(6));
    }

    public function test10()
    {
        $this->assertEquals('Buzz', \FizzBuzz::getStringForInt(10));
    }

    public function test15()
    {
        $this->assertEquals('Fizz Buzz', \FizzBuzz::getStringForInt(15));
    }

    /**
     * @expectedException \Exception
     */
    public function test0()
    {
        \FizzBuzz::getStringForInt(0);
    }

    public function test7()
    {
        $this->assertEquals('Wizz', \FizzBuzz::getStringForInt(7));
    }

    public function test21()
    {
        $this->assertEquals('Fizz Wizz', \FizzBuzz::getStringForInt(21));
    }

    public function test35()
    {
        $this->assertEquals('Fizz Buzz Wizz', \FizzBuzz::getStringForInt(35));
    }
}
