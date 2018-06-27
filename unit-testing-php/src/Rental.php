<?php
namespace PhpUnitWorkshop;

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

    private function calculateRegularMoviePrice():float {
        $price = 2;
        if ($this->daysRented > 2)
            $price += ($this->daysRented - 2) * 1.5;
        return $price;
    }

    private function calculateNewReleasePrice(): float {
        return $this->daysRented * 3;
    }

    private function computeChildrenPrice(): float {
        $price = 1.5;
        if ($this->daysRented > 3)
            $price += ($this->daysRented - 3) * 1.5;
        return $price;
    }


    public function calculatePrice(): float
    {
        switch ($this->movie->getType()) {
            case Movie::REGULAR: return $this->calculateRegularMoviePrice();
            case Movie::NEW_RELEASE: return $this->calculateNewReleasePrice();
            case Movie::CHILDREN: return $this->computeChildrenPrice();
            default: throw new \Exception('Invalid movie type');
        }
    }

    public function calculateFrequentPoints(): int
    {
        $frequentRenterPoints = 1;

        if ($this->movie->isNewRelease() && $this->daysRented > 1) {
            $frequentRenterPoints++;
        }

        return $frequentRenterPoints;
    }



}
