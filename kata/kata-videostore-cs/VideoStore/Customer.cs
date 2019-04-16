using System.Collections;
using System.Linq;

namespace VideoStore
{
    public class Customer
    {
        private readonly ArrayList _rentals = new ArrayList();
        public string Name { get; }

        public Customer(string name)
        {
            Name = name;
        }

        public void AddRental(Rental rental)
        {
            _rentals.Add(rental);
        }

        public string Statement()
        {
            decimal totalAmount = 0;
            var frequentRenterPoints = 0;
            var result = "Rental Record for " + Name + "\n";
            foreach (var each in _rentals.Cast<Rental>())
            {
                decimal thisAmount = 0;

                switch (each.Movie.PriceCode)
                {
                    case Movie.Regular:
                        thisAmount += 2;
                        if (each.DaysRented > 2)
                            thisAmount += (each.DaysRented - 2)*1.5m;
                        break;
                    case Movie.NewRelease:
                        thisAmount += each.DaysRented*3;
                        break;
                    case Movie.Childrens:
                        thisAmount += 1.5m;
                        if (each.DaysRented > 3)
                            thisAmount += (each.DaysRented - 3)*1.5m;
                        break;
                    default:
                        break;
                }
                // add frequent renter points
                frequentRenterPoints++;
                // add bonus for a two day new release rental
                if ((each.Movie.PriceCode == Movie.NewRelease) &&
                    each.DaysRented > 1)
                    frequentRenterPoints++;

                // show figures for this rental
                result += $"\t{each.Movie.Title}\t"+ $"{thisAmount:F1}\n";
                totalAmount += thisAmount;
            }

            // add footer lines
            result += $"Amount owed is "+ $"{totalAmount:F1}\n";
            result += $"You earned {frequentRenterPoints} frequent renter points";
            return result;
        }
    }
}