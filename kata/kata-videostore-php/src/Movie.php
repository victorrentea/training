<?php
namespace video;


class RegularMovie extends Movie {
    public function calculatePrice(int $daysRented): float
    {
        $price = 2;
        if ($this->getDaysRented() > 2)
            $price += ($this->getDaysRented() - 2) * 1.5;
        return $price;
    }

}
//class NewReleaseMovie extends Movie {
//
//}
//class ChildrensMovie extends Movie {
//
//}
class BabaciMovie extends Movie {

    public function calculatePrice(int $daysRented): float
    {
        // TODO: Implement calculatePrice() method.
    }
}

abstract class Movie
{
    const NEW_RELEASE = "NEW_RELEASE";
    const REGULAR = "REGULAR";
    const CHILDRENS = "CHILDRENS";
    const ELDERS = "BABACI";

    public abstract function calculatePrice(int $daysRented): float;

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