<?php

namespace PhpUnitWorkshopTest;

use PHPUnit\Framework\MockObject\MockObject;
use PHPUnit\Framework\TestCase;

class MockuriTata {
    private $dep;

    public function __construct(Dep $dep)
    {
        $this->dep = $dep;
    }

    public function realBizLogicMethodToTest () : string
    {
        return strtoupper($this->dep->toBeOrNotToBe());
//        return "TOBE";
    }

}

class Dep {

    public function toBeOrNotToBe():string {
        if (true) {
            throw new \Exception("N-am baza tata!");
        }
        return "not to be";
    }
}
class BowlingScoreShould extends TestCase
{

    /** @test */
    public function returnTOBEWhenDepSaysToBe()
    {
        /** @var Dep|MockObject $dep */
        $dep = self::getMockBuilder(Dep::class)->getMock();
        $dep/*->expects(self::once())*/ // NECESAR daca metoda mockuita produce side effects INSERT sau e o scumpa.
            ->method('toBeOrNotToBe')
            ->willReturn('Tataie')
        ;
        $tata = new MockuriTata($dep);
        self::assertEquals("TATAIE", $tata->realBizLogicMethodToTest());

        
    }
}

