<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 07-Feb-18
 * Time: 02:42 PM
 */

namespace video;


class StatementIssuer
{
    public function composeRentalStatement(Customer $customer)
    {
        $totalPrice = 0;
        $frequentRenterPoints = 0;
        $result = $this->composeHeader($customer);

        foreach ($customer->getRentals() as $rental) {
            $frequentRenterPoints += $rental->calculateFrequentRenterPoints();
            $result .= $this->composeRentalLine($rental);
            $totalPrice += $rental->calculatePrice();
        }

        $result .= $this->composeFooter($totalPrice, $frequentRenterPoints);

        return $result;
    }

    private function composeRentalLine(Rental $rental): string
    {
        return "\t" . $rental->getMovie()->getTitle() . "\t"
            . $rental->calculatePrice() . "\n";
    }

    private function composeFooter(float $totalPrice, int $frequentRenterPoints): string
    {
        $footer = "You owed " . $totalPrice . "\n";
        $footer .= "You earned " . $frequentRenterPoints . " frequent renter points\n";

        return $footer;
    }

    private function composeHeader(Customer $customer): string
    {
        return "Rental Record for " . $customer->getName() . "\n";
    }
}