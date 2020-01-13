<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 01-Aug-18
 * Time: 10:26 AM
 */

namespace PhpUnitWorkshop;


use GuzzleHttp\Exception\StateException;
use PHPUnit\Framework\TestCase;

class ExpectingExceptionsTest extends TestCase
{
    /** @test */
    public function cevaSeIntampla() {
        self::assertEquals(1,1);
    }

    /**
     * @expectedException  \InvalidArgumentException
     * @expectedExceptionMessage address
     */
    public function testVerificOExceptieMesaj() {
        $this->expectException(\InvalidArgumentException::class);
        $this->expectExceptionMessage('address');
        $this->aruncaEx(true);
    }

    public function testVerificOExceptieCod() {
        $this->expectException(\InvalidArgumentException::class);
        $this->expectExceptionCode(Errors::CUST_NAME);
        $this->aruncaEx(false);
    }

    private function aruncaEx(bool $x) {
        if ($x)
            throw new \InvalidArgumentException('Invalid Customer address');
        else
            throw new \InvalidArgumentException('e gresit param', Errors::CUST_NAME);
    }

}
class Errors {
    const CUST_NAME = -7;
}