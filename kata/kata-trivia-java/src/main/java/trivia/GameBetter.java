package trivia;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class GameBetter implements IGame {
	private List<Player> players = new ArrayList<>();
	
	private enum QuestionCategory {
		POP("Pop"), SCIENCE("Science"), SPORTS("Sports"), ROCK("Rock");
		public final String label;
		private QuestionCategory(String label) {
			this.label = label;
		}

	}
	
	private Map<QuestionCategory, List<String>> questions = new HashMap<>();

    private int currentPlayerIndex = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public  GameBetter(){
    	questions.put(QuestionCategory.POP, IntStream.range(0, 50).mapToObj(i -> "Pop Question " + i).collect(toList()));
    	questions.put(QuestionCategory.SCIENCE, IntStream.range(0, 50).mapToObj(i -> "Science Question " + i).collect(toList()));
    	questions.put(QuestionCategory.SPORTS, IntStream.range(0, 50).mapToObj(i -> "Sports Question " + i).collect(toList()));
    	questions.put(QuestionCategory.ROCK, IntStream.range(0, 50).mapToObj(i -> "Rock Question " + i).collect(toList()));
    }

	public boolean isPlayable() {
		return players.size() >= 2;
	}
	
	public boolean add(String playerName) {
	    players.add(new Player(playerName));
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}

	public void roll(int roll) {
		System.out.println(currentPlayer().getName() + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (currentPlayer().isInPenaltyBox()) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(currentPlayer().getName() + " is getting out of the penalty box");
				currentPlayer().move(roll);
				
				System.out.println(currentPlayer().getName() + "'s new location is " + currentPlayerPlace());
				System.out.println("The category is " + currentCategory().label);
				askQuestion();
			} else {
				System.out.println(currentPlayer().getName() + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
			}
			
		} else {
			currentPlayer().move(roll);
			
			System.out.println(currentPlayer().getName() 
					+ "'s new location is " 
					+ currentPlayerPlace());
			System.out.println("The category is " + currentCategory().label);
			askQuestion();
		}
		
	}

	private Player currentPlayer() {
		return players.get(currentPlayerIndex);
	}

	
	
	private int currentPlayerPlace() {
		return currentPlayer().getPlace();
	}
	
	
	
	

	private void askQuestion() {
		System.out.println(questions.get(currentCategory()).remove(0));
	}
	
	
	private QuestionCategory currentCategory() {
		switch (currentPlayerPlace() % 4) {
		case 0: return QuestionCategory.POP;
		case 1: return QuestionCategory.SCIENCE;
		case 2: return QuestionCategory.SPORTS;
		case 3: return QuestionCategory.ROCK;
		default:
			throw new RuntimeException("Impossible thanks to Arabia");
		}
	}


	public boolean wasCorrectlyAnswered() {
		if (currentPlayer().isInPenaltyBox()){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				currentPlayer().addCoin();
				System.out.println(currentPlayer().getName() + " now has " + currentPlayer().getCoins() + " Gold Coins.");
//				if (isGettingOutOfPenaltyBox) currentPlayer().iesiDinPenaly();
				boolean noWinner = playerDidNotWin();
				nextPlayer();
				return noWinner;
			} else {
				nextPlayer();
				return true;
			}
			
		} else {
			System.out.println("Answer was corrent!!!!");
			currentPlayer().addCoin();
			System.out.println(currentPlayer().getName() 
					+ " now has "
					+ currentPlayer().getCoins()
					+ " Gold Coins.");
			
			boolean noWinner = playerDidNotWin();
			nextPlayer();
			
			// tre sa intoarca true daca jocul mai poate continua
			return noWinner; 
		}
	}

	private void nextPlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(currentPlayer().getName()+ " was sent to the penalty box");
		currentPlayer().putInPenaltyBox();
		nextPlayer();
		return true;
	}


	private boolean playerDidNotWin() {
		return currentPlayer().getCoins() != 6;
	}
}
