using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;

namespace VideoStore
{
    public class Customer
    {
        private readonly List<Rental> rentals = new List<Rental>();
        public string Name { get; }

        public Customer(string name)
        {
            Name = name;
        }

        public void AddRental(Rental rental)
        {
            rentals.Add(rental);
        }

        public string ComposeStatement()
        {
            return ComposeHeader()
                   + ComposeBody()
                   + ComposeFooter();
        }

        private string ComposeBody()
        {
            var result = "";
            foreach (var rental in rentals)
            {
                // show figures for this rental
                result += $"\t{rental.Movie.Title}\t" + $"{CalculatePrice(rental):F1}\n";
            }
            return result;
        }

        private string ComposeHeader()
        {
            return $"Rental Record for {Name}\n";
        }

        private string ComposeFooter()
        {
            int frequentRenterPoints = ComputeTotalFrequentRenterPoints();
            decimal totalOwed = ComputeTotalOwed();
            return $"Amount owed is {totalOwed:F1}\n"
                   + $"You earned {frequentRenterPoints} frequent renter points";
        }

        private decimal ComputeTotalOwed()
        {
            return rentals.Sum(rental => CalculatePrice(rental));
        }

        private int ComputeTotalFrequentRenterPoints()
        {
            var frequentRenterPoints = 0;
            foreach (var rental in rentals)
            {
                frequentRenterPoints = CalculateFrequentRenterPoints(frequentRenterPoints, rental);
            }

            return frequentRenterPoints;
        }

        private static int CalculateFrequentRenterPoints(int frequentRenterPoints, Rental rental)
        {
            // add frequent renter points
            frequentRenterPoints++;
            // add bonus for a two day new release rental
            if (rental.Movie.Category == MovieCategory.NewRelease &&
                rental.DaysRented > 1)
            {
                frequentRenterPoints++;
            }

            return frequentRenterPoints;
        }

        private static decimal CalculatePrice(Rental rental)
        {
            switch (rental.Movie.Category)
            {
                case MovieCategory.Regular:
                    {
                        decimal price = 2;
                        if (rental.DaysRented > 2)
                        {
                            price += (rental.DaysRented - 2) * 1.5m;
                        }

                        return price;
                    }
                case MovieCategory.NewRelease:
                    return rental.DaysRented * 3;
                case MovieCategory.Childrens:
                    {
                        decimal price = 1.5m;
                        if (rental.DaysRented > 3)
                        {
                            price += (rental.DaysRented - 3) * 1.5m;
                        }
                        return price;
                    }

                default: throw new Exception("Unknown price code " + rental.Movie.Category);
            }
        }
    }
}