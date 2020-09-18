using System;

namespace VideoStore
{

    public enum MovieCategory
    {
        Childrens,
        Regular,
        NewRelease
    }
    public class Movie
    {

        public string Title { get; }
        public MovieCategory Category { get; }

        public Movie(string title, MovieCategory category)
        {
            Title = title;
            Category = category;
        }
    }
}