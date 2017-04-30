package videostore.clean;
import videostore.clean.movie.Movie;

class Rental {
	private final Movie movie;
	private final int daysRented;

	public Rental(Movie movie, int daysRented) {
		this.movie = movie;
		this.daysRented = daysRented;
	}

	public int getDaysRented() {
		return daysRented;
	}

	public Movie getMovie() {
		return movie;
	}
	
	public int getFrequentRenterPoints() {
		return movie.getFrequentRenterPoints(daysRented);
	}
	
	public String getStatementLine() {
		return  "\t" + movie.getTitle() + 
				"\t" + getPrice() + "\n";
	}
	
	public double getPrice() {
		return movie.getPrice(daysRented);
	}
}