<?php

namespace PhpUnitWorkshopTest;

use Hamcrest\Text\StringContains;
use PHPUnit\Framework\Constraint\TraversableContains;
use PHPUnit\Framework\TestCase;


class BaseTest extends TestCase {

    static $dateGlobale;
    protected $dateDeTest;

//    public function __construct($name = null, array $data = [], $dataName = '')
//    {
//        printf("Uaaaaa, Uaaa!\n");
//    }

    public function setUp()
    {
        echo "Mai intai a fost cuvantul\n";
        $this->login("test");
    }

    /** @test */
    public function dummy4()
    {
        $this->dateDeTest="a";
        self::$dateGlobale = "b";
        self::assertEquals("a", $this->dateDeTest);
    }

    /** @test */
    public function dummy2()
    {

        self::assertEquals(null, $this->dateDeTest);
        self::assertEquals("b", self::$dateGlobale);
    }

    //cod de prod:
    public function login($username)
    {

    }


}



class SubTest extends BaseTest {
    /** @before */
    public function initCopil()
    {
        echo "Apoi a fost lumina\n";
    }

    /** @test */
    function subtest() {
        echo "subTest";
        self::assertTrue(true);
    }
}