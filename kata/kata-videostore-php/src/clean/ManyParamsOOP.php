<?php


namespace video\clean;


(new ManyParamsOOP(new Validator(new OtherDependency())))->bizLogic([
    'a'=>'a',
    'b'=>1,
    's'=>'s',
    'c'=>4,
    'fileName'=>'file.txt',
    'versionId'=>1,
    'reference'=>'ref',
    'listId'=>5,
    'recordId'=>4,
    'g'=>'g'

]);

class ManyParamsOOP
{
    private $validator;

    public function __construct(Validator $validator)
    {
        $this->validator = $validator;
    }

    public function bizLogic(array $x)
    {
        $errors = [];
        $errors = array_merge($errors, $this->validator->m1($x['a'], $x['b']));
        $errors = array_merge($errors, $this->validator->m2($x['a'], $x['s'], $x['c']));
        $errors = array_merge($errors, $this->validator->m3($x['a'], $x['fileName'], $x['versionId'], $x['reference']));
        $errors = array_merge($errors, $this->validator->m4($x['a'], $x['listId'], $x['recordId'], $x['g']));
        $errors = array_merge($errors, $this->validator->m5($x['b']));
        if (!empty($errors)) {
            throw new \Exception($errors);
        }
    }
}

class Validator
{
    private $dep;

    public function __construct(OtherDependency $dep)
    {
        $this->dep = $dep;
    }

    public function m1(string $a, int $b): array
    {
        if ($a === '') {
            return ['a must not be null'];
        }
        // stuff
        return [];
    }

    public function m2(string $a, string $s, int $c): array
    {
        if ($c < 0) {
            return ["negative c"];
        }
        // stuff
        return [];

    }

    public function m3(string $a, string $fileName, int $versionId, string $reference): array
    {
        // stuff
        return [];

    }

    public function m4(string $a, int $listId, int $recordId, string $g): array
    {
        // stuff
        return [];

    }

    public function m5(int $b): array
    {
        // stuff
        return [];
    }
}

class OtherDependency
{

}
