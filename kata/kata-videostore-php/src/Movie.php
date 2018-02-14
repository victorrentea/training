<?php
namespace video;

class Movie
{
    const NEW_RELEASE = "NEW_RELEASE";
    const REGULAR = "REGULAR";
    const CHILDRENS = "CHILDRENS";

    private $title;

    private $type;

    public function __construct(string $title,string $priceCode)
    {
        $this->title = $title;
        $this->type = $priceCode;
    }

    public function getTitle(): string
    {
        return $this->title;
    }

    public function getType(): string
    {
        return $this->type;
    }

    public function hasPriceCode(string $priceCode)
    {
        return $this->type === $priceCode;
    }
}