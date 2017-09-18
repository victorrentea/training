<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/17/2017
 * Time: 8:02 PM
 */

namespace victor\training\oo\behavioral\factorymethod;
include "OutputFile.php";

class FileGenerator
{
    public static function createAnafFile()
    {
        $outputFile = new OutputFile("anaf");
        $file = fopen($outputFile->getFileName(), "w");
        printf("writing to file " . $outputFile->getFileName(). "\n");
        // write to file...
        fclose($file);
    }

    public static function createRCFile()
    {
        $outputFile = new OutputFile("rc", "txt", "|");
        $file = fopen($outputFile->getFileName(), "w");
        printf("writing to file " . $outputFile->getFileName() .  "\n");
        // write to file...
        fclose($file);
    }
}

FileGenerator::createAnafFile();
FileGenerator::createRCFile();