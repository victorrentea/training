using NBehave.Spec.NUnit;
using NUnit.Framework;
using Rhino.Mocks.Impl.Invocation.Actions;
using VideoStore;

namespace VideoStoreSpec
{
    [TestFixture]
    public class VideoStoreSpec 
    {
       
        [Test]
        public void CharacterizationTest()
        {
            Customer customer = new Customer("John Doe");
            customer.AddRental(new Rental(new Movie("Star Wars", Movie.NewRelease), 6));
            customer.AddRental(new Rental(new Movie("Sofia", Movie.Childrens), 7));
            customer.AddRental(new Rental(new Movie("Inception", Movie.Regular), 5));

            string expected = "Rental Record for John Doe\n"
                    + "	Star Wars	18.0\n"
                    + "	Sofia	7.5\n"
                    + "	Inception	6.5\n"
                    + "Amount owed is 32.0\n"
                    + "You earned 4 frequent renter points";

            customer.Statement().ShouldEqual(expected);
        }
    }
}
