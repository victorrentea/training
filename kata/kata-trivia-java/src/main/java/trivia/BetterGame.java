package trivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BetterGame implements IGame {
	
	public static class Player{
		private final String name;
		private int place = 0;
		private int purse = 0;
		
		public final int getPurse() {
			return purse;
		}

		public Player(String name) {
			this.name = name;
		}
		
		public int getPlace() {
			return place;
		}
		
		public String getName() {
			return name;
		}
		
		public void moveForward(int roll) {
			place += roll;

			if (place > 11) {
				place -= 12;				
			}
		}
		
		public void addCoinToPurse(){
			purse++;
		}
		
		public boolean isWinner() {
			return purse == 6;
		}
	}
	
    List<Player> players = new ArrayList<Player>();
    boolean[] inPenaltyBox  = new boolean[6];
    
    List<String> popQuestions = new LinkedList<String>();
    List<String> scienceQuestions = new LinkedList<String>();
    List<String> sportsQuestions = new LinkedList<String>();
    List<String> rockQuestions = new LinkedList<String>();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public  BetterGame(){
    	for (int i = 0; i < 50; i++) {
			popQuestions.add("Pop Question " + i);
			scienceQuestions.add(("Science Question " + i));
			sportsQuestions.add(("Sports Question " + i));
			rockQuestions.add(createRockQuestion(i));
    	}
    }

	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}
	
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {
		
		
	    players.add(new Player(playerName));
	    inPenaltyBox[howManyPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer).getName() + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(players.get(currentPlayer).getName() + " is getting out of the penalty box");
				players.get(currentPlayer).moveForward(roll);
				
				System.out.println(players.get(currentPlayer).getName() 
						+ "'s new location is " 
						+ players.get(currentPlayer).getPlace());
				System.out.println("The category is " + currentCategory());
				askQuestion();
			} else {
				System.out.println(players.get(currentPlayer).getName() + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {
		
			players.get(currentPlayer).moveForward(roll);
			
			System.out.println(players.get(currentPlayer).getName() 
					+ "'s new location is " 
					+ players.get(currentPlayer).getPlace());
			System.out.println("The category is " + currentCategory());
			askQuestion();
		}
		
	}

	private void askQuestion() {
		if (currentCategory().equals("Pop"))
			System.out.println(popQuestions.remove(0));
		if (currentCategory() == "Science")
			System.out.println(scienceQuestions.remove(0));
		if (currentCategory() == "Sports")
			System.out.println(sportsQuestions.remove(0));
		if (currentCategory() == "Rock")
			System.out.println(rockQuestions.remove(0));		
	}
	
	
	private String currentCategory() {
		return CATEGORIES[players.get(currentPlayer).getPlace() % 4];
	}
	
	private final static String[] CATEGORIES = {"Pop", "Science", "Sports", "Rock"};

	public boolean wasCorrectlyAnswered() {
		Player player = players.get(currentPlayer);
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				player.addCoinToPurse();
				System.out.println(getNameAndCoinStatus(player));
				
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				
				return player.isWinner();
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return false;
			}
			
			
			
		} else {
		
			System.out.println("Answer was corrent!!!!");
			player.addCoinToPurse();
			System.out.println(getNameAndCoinStatus(player));
			
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return player.isWinner();
		}
	}

	private String getNameAndCoinStatus(Player player) {
		return player.getName() 
				+ " now has "
				+ player.getPurse()
				+ " Gold Coins.";
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer).getName()+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return !true;
	}


	
}
