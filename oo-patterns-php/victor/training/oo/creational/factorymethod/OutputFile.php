<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/17/2017
 * Time: 8:02 PM
 */

namespace victor\training\oo\behavioral\factorymethod;


class OutputFile
{
    private $baseName;
    private $extension;
    private $separator;

    public function __construct(string $baseName, string $extension = "csv", string $separator = ";")
    {
        $this->baseName = $baseName;
        $this->extension = $extension;
        $this->separator = $separator;
    }

    public static function createCsvWithSemicolonSeparator(string $baseName): OutputFile {
        return new OutputFile($baseName);
    }

    public function getFileName(): string {
        return $this->baseName . "." . $this->extension;
    }

    public function getSeparator(): string {
        return $this->separator;
    }

}