import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.collection.mutable.ArrayBuffer

object Movie {
  val CHILDRENS = 2
  val REGULAR = 0
  val NEW_RELEASE = 1
}

class Movie(var title: String, var priceCode: Int) {

}

class Rental(var movie: Movie, var daysRented: Int) {
}


class Customer(_name: String) {
  private var name = _name
  private val rentals = new ArrayBuffer[Rental]()

  def addRental(arg: Rental): Unit = {
    rentals += arg
  }

  def statement(): String = {
    var totalAmount = 0d
    var frequentRenterPoints = 0
    val it = rentals.iterator
    var result = "Rental Record for " + name + "\n"
    while (it.hasNext) {
      var thisAmount:Double = 0
      val each = it.next
      // determine amounts for each line
      each.movie.priceCode match {
        case Movie.REGULAR =>
          thisAmount += 2
          if (each.daysRented > 2) thisAmount += (each.daysRented - 2) * 1.5
        case Movie.NEW_RELEASE =>
          thisAmount += each.daysRented * 3
        case Movie.CHILDRENS =>
          thisAmount += 1.5
          if (each.daysRented > 3) thisAmount += (each.daysRented - 3) * 1.5
      }
      // add frequent renter points
      frequentRenterPoints += 1
      // add bonus for a two day new release rental
      if ((each.movie.priceCode == Movie.NEW_RELEASE) && each.daysRented > 1) {
        frequentRenterPoints += 1; frequentRenterPoints - 1
      }
      // show figures for this rental
      result += "\t" + each.movie.title + "\t" + String.valueOf(thisAmount) + "\n"
      totalAmount += thisAmount
    }
    // add footer lines
    result += "Amount owed is " + String.valueOf(totalAmount) + "\n"
    result += "You earned " + String.valueOf(frequentRenterPoints) + " frequent renter points"
    result
  }
}


class CustomerTest extends FunSuite with BeforeAndAfter {

  test("this is called a 'characterization test' ") {
    val customer = new Customer("John Doe")
    customer.addRental(new Rental(new Movie("Star Wars", Movie.NEW_RELEASE), 6))
    customer.addRental(new Rental(new Movie("Sofia", Movie.CHILDRENS), 7))
    customer.addRental(new Rental(new Movie("Inception", Movie.REGULAR), 5))
    val expected ="Rental Record for John Doe\n" +
      "\tStar Wars\t18.0\n" +
      "\tSofia\t7.5\n" +
      "\tInception\t6.5\n" +
      "Amount owed is 32.0\n" +
      "You earned 4 frequent renter points"
    assert(expected == customer.statement)
  }
}