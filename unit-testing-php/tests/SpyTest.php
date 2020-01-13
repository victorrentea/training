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
class ClasaMare
{
    public function metodaPublica()
    {

    }
}

