<?php
namespace video;

class Rental
{
    /**
     * @var Movie
     */
    private $movie;
    private $daysRented;

    /**
     * Rental constructor.
     * @param Movie $movie
     * @param $daysRented
     */
    public function __construct(Movie $movie, $daysRented)
    {
        $this->movie = $movie;
        $this->daysRented = $daysRented;
    }


    /**
     * @return int
     */
    public function getDaysRented()
    {
        return $this->daysRented;
    }

    /**
     * @param int $daysRented
     */
    public function setDaysRented($daysRented)
    {
        $this->daysRented = $daysRented;
    }

    /**
     * @return Movie
     */
    public function getMovie()
    {
        return $this->movie;
    }




}