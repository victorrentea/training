package ro.victor.unittest.bdd.tennis;

import static org.junit.Assert.assertEquals;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TennisScoreBehaviorSteps {
	
	// SOLUTION(
	private TennisScore score;
	
	@Given("^An empty game$")
	public void an_empty_game() throws Throwable {
		score = new TennisScore();
	}
	
	@Then("^The score is \"([^\"]*)\"$")
	public void the_score_is(String arg1) throws Throwable {
	    assertEquals(arg1, score.getScore());
	}

	@When("^Player(\\d+) scores a point$")
	public void player_scores_a_point(int playerNo) throws Throwable {
		score.point("Player"+playerNo);
	}

	@When("^Player(\\d+) scores (\\d+) points$")
	public void player_scores_points(int playerNo, int numberOfPoints) throws Throwable {
		for (int i =0; i< numberOfPoints;i++) {
			score.point("Player"+playerNo);
		}
	}


	// SOLUTION)
	
}
