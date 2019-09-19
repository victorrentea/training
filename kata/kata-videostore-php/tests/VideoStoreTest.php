<?php
namespace video;

class VideoStoreTest extends \PHPUnit\Framework\TestCase {

    public function testRentalStatementFormat() {
        $customer = new Customer("John");
        $customer->addRental(new Rental(new Movie("Star Wars", Movie::NEW_RELEASE), 6));
        $customer->addRental(new Rental(new Movie("Sofia", Movie::CHILDRENS), 7));
        $customer->addRental(new Rental(new Movie("Inception", Movie::REGULAR), 5));


        $this->assertEquals(
            "Rental Record for John\n" .
            "\tStar Wars\t18\n" .
            "\tSofia\t7.5\n" .
            "\tInception\t6.5\n" .
            "You owed 32\n" .
            "You earned 4 frequent renter points\n",
            $customer->statement());
    }
}
