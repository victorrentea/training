<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/17/2017
 * Time: 7:42 PM
 */

namespace victor\training\oo\behavioral\singleton;


class AppConfiguration
{
    /* @var AppConfiguration */
//    private static $INSTANCE;

    public function __construct()
    {
        printf("Creating singleton instance\n");
        $this->properties = $this->readConfiguration();
    }

    private $properties;

    private function readConfiguration() {
        printf("Fetching properties from Tahiti...\n");
        sleep(2);
        printf("Decrypting properties...\n");
        sleeep(1);
        return parse_ini_file("props.ini");
    }

    public function getProperty(string $propertyName): string {
        return "s";
    }
}