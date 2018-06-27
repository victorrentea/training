<?php
namespace PhpUnitWorkshopTest;

use PHPUnit\Framework\TestCase;
use PhpUnitWorkshop\Movie;
use PhpUnitWorkshop\Rental;
use PhpUnitWorkshop\StatementFormatter;

class VideoStoreTest extends TestCase
{

    public function testRentalStatementFormat()
    {
        $statementFormatter = new StatementFormatter();
        $rentals[] = new Rental(new Movie("Star Wars", Movie::NEW_RELEASE), 6);
        $rentals[] = new Rental(new Movie("Sofia", Movie::CHILDREN), 7);
        $rentals[] = new Rental(new Movie("Inception", Movie::REGULAR), 5);

        $this->assertEquals(
            "Rental Record for John\n" .
            "\tStar Wars\t18\n" .
            "\tSofia\t7.5\n" .
            "\tInception\t6.5\n" .
            "You owed 32\n" .
            "You earned 4 frequent renter points\n",
            $statementFormatter->generateStatement($rentals, 'John'));
    }
}