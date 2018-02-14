<?php
namespace video;

class Rental
{
    private $movie;
    private $daysRented;

    public function __construct(Movie $movie, int $daysRented)
    {
        $this->movie = $movie;
        $this->daysRented = $daysRented;
    }

    public function getDaysRented(): int
    {
        return $this->daysRented;
    }

    public function getMovie(): Movie
    {
        return $this->movie;
    }

    public function calculateFrequentRenterPoints(): int
    {
        if ($this->movie->hasPriceCode(Movie::NEW_RELEASE)
            && $this->daysRented > 1) {
            return 2;
        }

        return 1;
    }

    public function calculatePrice(): float
    {
        switch ($this->movie->getType()) {
            case Movie::REGULAR:
                return PriceCalculators::calculateRegularPrice($this->daysRented);
            case Movie::NEW_RELEASE:
                return PriceCalculators::calculateNewReleasePrice($this->daysRented);
            case Movie::CHILDRENS:
                return PriceCalculators::calculateChildrensPrice($this->daysRented);
            default:
                throw new \Exception();
        }
    }


}