package trivia;

public class Player {
	private final String name;
	private int place;
	private int coins;
	private boolean inPenaltyBox;
	
	public Player(String name) {
		this.name = name;
	}
	
	public boolean isInPenaltyBox() {
		return inPenaltyBox;
	}
	
	
	// TODO unde e moveOut?!!
	public void putInPenaltyBox() {
		this.inPenaltyBox = true;
	}

	public int getPlace() {
		return place;
	}
	
	public void move(int roll) {
//		place = (place + roll) % 12;
		place += roll;
		if (place >= 12) {
			place -= 12;
		}
	}
	
	
	public String getName() {
		return name;
	}
	
	public int getCoins() {
		return coins;
	}
	
	public void addCoin() {
		coins ++;
	}
	
	
}