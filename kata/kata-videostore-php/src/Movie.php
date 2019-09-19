<?php
namespace video;

class Movie
{
    const NEW_RELEASE = "NEW_RELEASE";
    const REGULAR = "REGULAR";
    const CHILDRENS = "CHILDRENS";
//    const ELDERS = "BABACI";

    private $title;

    private $priceCode;

    public function __construct(string $title, string $priceCode)
    {
        $this->title = $title;
        $this->priceCode = $priceCode;
    }

    public function getTitle(): string
    {
        return $this->title;
    }

    public function getPriceCode(): string
    {
        return $this->priceCode;
    }

    public function isNewRelease(): bool
    {
        return $this->priceCode === self::NEW_RELEASE;
    }
}