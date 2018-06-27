<?php
namespace PhpUnitWorkshop;

class Movie
{
    public const NEW_RELEASE = 'NEW_RELEASE';
    public const REGULAR = 'REGULAR';
    public const CHILDREN = 'CHILDRENS';

    private $title;

    private $type;

    public function __construct(string $title, string $type)
    {
        $this->title = $title;
        $this->type = $type;
    }

    public function getTitle(): string
    {
        return $this->title;
    }

    public function getType(): string
    {
        return $this->type;
    }

    public function isNewRelease(): bool
    {
        return $this->type === static::NEW_RELEASE;
    }
}


