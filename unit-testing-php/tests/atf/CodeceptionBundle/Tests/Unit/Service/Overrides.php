<?php

namespace Emag\Core\CodeceptionBundle\Service;

function file_get_contents($filename)
{
    $classReflection =  new \ReflectionClass($GLOBALS["className"]);
    $class =  $classReflection->newInstanceArgs();

    return call_user_func_array([ $class, 'file_get_contents'
    ], [ $filename ]);
}