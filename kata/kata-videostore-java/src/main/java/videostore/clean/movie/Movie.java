package videostore.clean.movie;
public abstract class Movie {
	
	
	public static enum Type {
		REGULAR,
		NEW_RELEASE,
		CHILDRENS;
	}
	
	private final String title;

	public abstract double getPrice(int daysRented);

	public abstract int getFrequentRenterPoints(int daysRented);
	
	public Movie(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public boolean isNewRelease() {
		return this instanceof NewReleaseMovie;
	}
}