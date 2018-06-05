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
	
    // SOLUTION (
    /* @var AppConfiguration */
    private static $INSTANCE;
	// SOLUTION )

    // public function __construct() // INITIAL 
    private function __construct() // SOLUTION 
	{
        printf("Creating singleton instance\n");
        $this->properties = $this->readConfiguration();
    }
	
	// SOLUTION (

    public static function getInstance(): AppConfiguration {
        if (static::$INSTANCE == null) {
            static::$INSTANCE = new AppConfiguration();
        }
        return static::$INSTANCE;
    }
    // SOLUTION )
    private $properties;

    private function readConfiguration(): array {
        printf("Fetching properties from Tahiti...\n");
        sleep(2);
        printf("Decrypting properties...\n");
        sleep(1);
        return parse_ini_file("props.ini");
    }

    public function getProperty(string $propertyName): string {
        return $this->properties[$propertyName];
    }
}