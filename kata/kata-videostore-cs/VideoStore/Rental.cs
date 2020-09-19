using System;

namespace VideoStore
{
    public class Rental
    {
        public Movie Movie { get; }
        public int DaysRented { get; }

        public Rental(Movie movie, int daysRented)
        {
            Movie = movie;
            DaysRented = daysRented;
        }

        public int CalculateFrequentRenterPoints()
        {
          
            bool isNewRelease = Movie.Category == MovieCategory.NewRelease;
           
            int frequentRenterPoints = 1;
            if (isNewRelease && DaysRented >= 2)
            {
                frequentRenterPoints++;
            }

            return frequentRenterPoints;
        }

        public decimal CalculatePrice()
        {
            switch (Movie.Category)
            {
                case MovieCategory.Regular:
                    return computeRegularMovie();
                case MovieCategory.NewRelease:
                    return computeNewReleasePrice;
                case MovieCategory.Childrens:
                    return computeChildrenPrice();
                default: throw new Exception("Unknown price code " + this.Movie.Category);
            }
        }

        private decimal computeChildrenPrice()
        {
            decimal price = 1.5m;
            if (this.DaysRented > 3)
            {
                price += (this.DaysRented - 3) * 1.5m;
            }

            return price;
        }

        private int computeNewReleasePrice => this.DaysRented * 3;

        private decimal computeRegularMovie()
        {
            decimal price = 2;
            if (this.DaysRented > 2)
            {
                price += (this.DaysRented - 2) * 1.5m;
            }

            return price;
        }
    }
}