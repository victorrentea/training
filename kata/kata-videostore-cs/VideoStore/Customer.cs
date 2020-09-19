using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;

namespace VideoStore
{
    public class Customer
    {
        public List<Rental> Rentals { get; } = new List<Rental>();
        public string Name { get; }

        public Customer(string name)
        {
            Name = name;
        }

        public void AddRental(Rental rental)
        {
            Rentals.Add(rental);
        }
      
    }

    public class StatementComposer
    {

        public string ComposeStatement(string customerName, List<Rental> rentals)
        {
            var data = PrepareData(rentals, customerName);

            return ComposeStatement(data);
        }

        private StatementData PrepareData(List<Rental> rentals, string customerName)
        {
            var frequentRenterPoints = rentals.Sum(rental => rental.CalculateFrequentRenterPoints());
            var totalOwed = rentals.Sum(rental => rental.CalculatePrice());

            StatementData data = new StatementData(customerName, rentals, totalOwed, frequentRenterPoints);
            return data;
        }

        private string ComposeStatement(StatementData data)
        {
            return ComposeHeader(data)
                   + ComposeBody(data.Rentals)
                   + ComposeFooter(data);
        }

        private static string ComposeHeader(StatementData data)
        {
            return $"Rental Record for {data.CustomerName}\n";
        }

        private string ComposeFooter(StatementData data)
        {
            return $"Amount owed is {data.TotalOwed:F1}\n"
                   + $"You earned {data.TotalFrequentRenterPoint} frequent renter points";
        }

        private string ComposeBody(List<Rental> list)
        {
            var result = "";
            foreach (var rental in list)
            {
                result += ComposeLine(rental);
            }
            return result;
        }

        private static string ComposeLine(Rental rental)
        {
            return $"\t{rental.Movie.Title}\t{rental.CalculatePrice():F1}\n";
        }
    }

    class StatementData
    {
        public string CustomerName { get; }
        public List<Rental> Rentals { get; }
        public decimal TotalOwed { get; }
        public int TotalFrequentRenterPoint { get; }

        public StatementData(string customerName, List<Rental> rentals, decimal totalOwed, int totalFrequentRenterPoint)
        {
            CustomerName = customerName;
            Rentals = rentals;
            TotalOwed = totalOwed;
            TotalFrequentRenterPoint = totalFrequentRenterPoint;
        }
    }
}