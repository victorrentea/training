<?php
namespace video;

class Movie
{
    const NEW_RELEASE = "NEW_RELEASE";
    const REGULAR = "REGULAR";
    const CHILDRENS = "CHILDRENS";

    private $title;

    private $priceCode;

    /**
     * Movie constructor.
     * @param string $title
     * @param string $priceCode
     */
    public function __construct($title, $priceCode)
    {
        $this->title = $title;
        $this->priceCode = $priceCode;
    }

    /**
     * @return mixed
     */
    public function getTitle()
    {
        return $this->title;
    }

    /**
     * @param mixed $title
     */
    public function setTitle($title)
    {
        $this->title = $title;
    }

    /**
     * @return mixed
     */
    public function getPriceCode()
    {
        return $this->priceCode;
    }

    /**
     * @param mixed $priceCode
     */
    public function setPriceCode($priceCode)
    {
        $this->priceCode = $priceCode;
    }

}