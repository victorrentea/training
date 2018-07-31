<?php

namespace PhpUnitWorkshopTest;

use Hamcrest\Text\StringContains;
use PHPUnit\Framework\Constraint\TraversableContains;
use PHPUnit\Framework\TestCase;

class BaseTest extends TestCase {
    /** @before */
    public function initInSuper() {
        echo "init super\n";
    }
}

class BasicPhpUnit extends BaseTest
{

    /** @before */
    public function init1() {
        echo "init1\n";
    }

    /** @before */
    public function init2() {
        echo "init2\n";
    }

    protected function setUp()
    {
        echo "Before\n";
    }

    /**
     * @test
     * @doesNotPerformAssertions
     */
    public function first()
    {
        echo "Hello 1\n";
    }

    /** @test */
    public function second()
    {
        echo "Hello 2\n";
        $this->assertEquals(1, 1);
    }

    /** @test
     * @expectedException \InvalidArgumentException
     */
    public function exceptionClass(): void
    {
        $this->throwingLogic();
    }

    /** @test
     * @expectedException \InvalidArgumentException
     * @expectedExceptionCode 123
     */
    public function exceptionCode(): void
    {
        $this->throwingLogic();
    }

    private function throwingLogic(): void
    {
        throw new \InvalidArgumentException("Dummy", 123);
    }

    /** @test */
    public function playWithFire() {
        $t = $this->logicReturingTime();
        self::assertEquals(round(microtime(true) * 1000), $t);
    }

    private function logicReturingTime(): int
    {
        $time = round(microtime(true) * 1000);
//        for ($i = 0; $i < 700;$i++) echo sqrt(1314112323123);
        return $time;
    }

}
