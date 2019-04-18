package ro.victor.tennis;

import static org.junit.Assert.assertEquals;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TennisGameCucumberSteps {

	private TennisGame tennisGame;
	@Given("^An empty game$")
	public void an_empty_game() throws Throwable {
		tennisGame = new TennisGame();
	}
	
	@Then("^The score is \"([^\"]*)\"$")
	public void the_score_is(String scoreStr) throws Throwable {
		assertEquals(scoreStr, tennisGame.score());
	}
	
	@When("^Player(\\d+) scores a point$")
	public void player_scores_a_point(int playerNo) throws Throwable {
	   tennisGame.point(playerNo);
	}
	

@When("^Player(\\d+) scores (\\d+) points$")
public void player_scores_points(int playerNo, int points) throws Throwable {
	for (int i = 0; i < points; i++) {
		tennisGame.point(playerNo);
	}
}


}
