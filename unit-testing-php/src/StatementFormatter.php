<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 06-Jun-18
 * Time: 04:15 PM
 */

namespace PhpUnitWorkshop;

class StatementFormatter
{
    /**
     * @throws \Exception
     */
    public function generateStatement(array $rentals, string $customerName): string
    {
        $totalAmount = 0;
        $frequentRenterPoints = 0;
        $result = $this->formatHeader($customerName);

        foreach ($rentals as $rental) {
            $frequentRenterPoints += $rental->calculateFrequentPoints();
            $result .= $this->formatTitle($rental);
            $totalAmount += $rental->calculatePrice();
        }

        $result .= $this->formatFooter($totalAmount, $frequentRenterPoints);

        return $result;
    }

    /**
     * @throws \Exception
     */
    private function formatTitle(Rental $rental): string
    {
        return sprintf("\t%s\t%g\n", $rental->getMovie()->getTitle(), $rental->calculatePrice());
    }

    private function formatFooter(float $totalAmount, int $frequentRenterPoints): string
    {
        return 'You owed ' . $totalAmount . "\n" .
            'You earned ' . $frequentRenterPoints . " frequent renter points\n";
    }

    private function formatHeader(string $customerName): string
    {
        return sprintf("Rental Record for %s\n", $customerName);
    }

}


class DtoExistent {
    private $a, $b, $c;
}

class UnNouDto {

    /** @var DtoExistent
     @embedded */
    private $dtoExistent;
    private $d;
}