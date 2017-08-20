package trivia;

public interface IGame {

	boolean wrongAnswer();

	boolean wasCorrectlyAnswered();

	void roll(int roll);

	int howManyPlayers();

	boolean add(String playerName);

	boolean isPlayable();

	String createRockQuestion(int index);

}
