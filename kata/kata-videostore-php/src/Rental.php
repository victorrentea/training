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

    public function calculatePrice(): float
    {
        switch ($this->movie->getPriceCode()) {
            case Movie::REGULAR: return $this->calculateRegularPrice();
            case Movie::NEW_RELEASE: return $this->calculateNewReleasePrice();
            case Movie::CHILDRENS: return $this->calculateChildrensPrice();
            default: throw new \Exception("Dau bere dac-o prinzi: " . $this->movie->getTitle());
        }
    }
    public function computeMaxRentalDays(): float
    {
        switch ($this->movie->getPriceCode()) {
            case Movie::REGULAR: return $this->calculateRegularPrice();
            case Movie::NEW_RELEASE: return $this->calculateNewReleasePrice();
            case Movie::CHILDRENS: return $this->calculateChildrensPrice();
            default: throw new \Exception("Dau bere dac-o prinzi: " . $this->movie->getTitle());
        }
    }
    public function computeMinAge(): float
    {
        switch ($this->movie->getPriceCode()) {
            case Movie::REGULAR: return $this->calculateRegularPrice();
            case Movie::NEW_RELEASE: return $this->calculateNewReleasePrice();
            case Movie::CHILDRENS: return $this->calculateChildrensPrice();
            default: throw new \Exception("Dau bere dac-o prinzi: " . $this->movie->getTitle());
        }
    }

    public function calculateFrequentRenterPoints(): int
    {
        $frequentRenterPoints = 1;

        if ($this->movie->isNewRelease() && $this->getDaysRented() >= 2) {
            $frequentRenterPoints++;
        }
        return $frequentRenterPoints;
    }

    private function calculateRegularPrice(): float
    {
        $price = 2;
        if ($this->getDaysRented() > 2)
            $price += ($this->getDaysRented() - 2) * 1.5;
        return $price;
    }

    private function calculateNewReleasePrice(): float
    {
        return $this->getDaysRented() * 3;
    }

    private function calculateChildrensPrice(): float
    {
        $price = 1.5;
        if ($this->getDaysRented() > 3)
            $price += ($this->getDaysRented() - 3) * 1.5;
        return $price;
    }
}