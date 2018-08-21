<?php

namespace PhpUnitWorkshopTest;

use Hamcrest\Text\StringContains;
use PHPUnit\Framework\Constraint\TraversableContains;
use PHPUnit\Framework\TestCase;

class BaseTest extends TestCase {
    /** @var string */
    protected $currentUserName;

    /** @before */
    public function initUsername() {
        echo "Inainte din super-clasa testuli\n";
        $this->currentUserName = "jdoe";
    }
}

class BasicPhpUnit extends BaseTest
{
    public function __construct(?string $name = null, array $data = [], string $dataName = '')
    {
        parent::__construct($name, $data, $dataName);
        echo "Uaaa, A new test instance is born!\n";
    }

    private $f = 1;

    /** @before */
    public function initBasic() {
        echo "Inainte de test\n";
    }
    /** @before */
    public function initBasic2() {
        echo "Inainte2 de test\n";
    }

    protected function setUp()/* The :void return type declaration that should be here would cause a BC issue */
    {
        //am inghitit super::setUp
        echo "setup \n";
    }


    public function test1() {
        $this->f++;
        echo "Test1 {$this->f}\n";
        self::assertEquals(1,1);
    }
    public function test2() {
        echo "Test2 {$this->f}\n";
        self::assertEquals(1,1);
    }


}
