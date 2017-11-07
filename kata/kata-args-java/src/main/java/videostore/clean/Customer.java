package videostore.clean;
import java.util.ArrayList;
import java.util.List;

class Customer {
	private final String name;
	private List<Rental> rentals = new ArrayList<>();

	public Customer(String name) {
		this.name = name;
	}

	public void addRental(Rental rental) {
		rentals.add(rental);
	}

	public String getName() {
		return name;
	}

	public String getStatement() {
		double totalPrice = 0;
		int totalPoints = 0;
		String statement = getStatementHeader();
		for (Rental rental : rentals) {
			totalPoints += rental.getFrequentRenterPoints();
			totalPrice += rental.getPrice();
			statement += rental.getStatementLine();
		}
		statement += getStatementFooter(totalPrice, totalPoints);
		return statement;
	}

	private String getStatementHeader() {
		return "Rental Record for " + name + "\n";
	}


	private String getStatementFooter(double totalPrice, int totalFrequentRenterPoints) {
		return "Amount owed is " + totalPrice + "\n" 
				+ "You earned " + totalFrequentRenterPoints
				+ " frequent renter points";
	}

}