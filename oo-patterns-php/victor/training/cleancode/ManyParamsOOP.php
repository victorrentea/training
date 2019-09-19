<?php


class Bizniss
{
    private $validator;

    public function __construct(Validator $validator)
    {
        $this->validator = $validator;
    }

    function bizLogic()
    {
        $errors = [];
        $this->validator->m1("a", 1, $errors);
        $this->validator->m2("b", 1, $errors);
        $this->validator->m3("file.txt", 1, "ref", $errors);
        $this->validator->m4("a", 1, 5, "g", $errors);
        $this->validator->m5(1, $errors);
        if (!empty($errors)) {
            throw new \Exception($errors);
        }
    }
}

class Validator
{

    public function m1(String $a, int $b, array $errors)
    {
        // stuff
    }

    public function m2(String $s, int $c, array $errors)
    {
        // stuff
    }

    public function m3(String $fileName, int $versionId, string $reference, array $errors)
    {
        // stuff
    }

    public function m4(String $a, int $listId, int $recordId, string $g, array $errors)
    {
        // stuff
    }

    public function m5(int $b, array $errors)
    {
        // stuff
    }
}