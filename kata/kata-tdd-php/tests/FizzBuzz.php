<?php
namespace video;

class FizzBuzz
{

    private $wordNumberMap;

    public function __construct($wordNumberMap)
    {

        $this->wordNumberMap = $wordNumberMap;
    }

    public function generate(int $n) {


        $arr = range(1,$n);
        foreach ($arr as $key => $value)
        {
            $found = '';
            foreach($this->wordNumberMap as $label => $func)
            {
                if($func($key+1))
                {
                    $found.= $label;
                }
            }
            $arr[$key] = $found ?: $value;
        }
        return $arr;
    }
}