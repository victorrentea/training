package videostore.clean.movie;

public class NewReleaseMovie extends Movie {
	public NewReleaseMovie(String title) {
		super(title);
	}
	
	@Override
	public double getPrice(int daysRented) {
		return daysRented * 3;
	}
	
	@Override
	public int getFrequentRenterPoints(int daysRented) {
		return daysRented > 1 ? 2 :1;
	}
}