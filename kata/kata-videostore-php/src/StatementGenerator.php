<?php


namespace video;


class StatementGenerator
{

    /**
     * @param $rentals Rental[]
     */
    public function generateStatement(string $customerName, $rentals): string
    {
        $frequentRenterPoints = 0;
        foreach ($rentals as $rental) {
            $frequentRenterPoints += $rental->calculateFrequentRenterPoints();
        }

        $result = $this->generateHeader($customerName);
        foreach ($rentals as $rental) {
            $result .= $this->generateLine($rental);
        }

        $totalPrice = 0;
        foreach ($rentals as $rental) {
            $totalPrice += $rental->calculatePrice();
        }

        return $result . $this->generateFooter($totalPrice, $frequentRenterPoints);
    }


    private function generateFooter(float $totalPrice, int $frequentRenterPoints): string
    {
        return "You owed " . $totalPrice . "\n"
            . "You earned " . $frequentRenterPoints . " frequent renter points\n";
    }

    private function generateHeader(string $customerName): string
    {
        return sprintf("Rental Record for %s\n", $customerName);
    }

    private function generateLine(Rental $rental): string
    {
        return "\t" . $rental->getMovie()->getTitle() . "\t" . $rental->calculatePrice() . "\n";
    }
}