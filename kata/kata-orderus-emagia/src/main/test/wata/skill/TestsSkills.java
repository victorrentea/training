package wata.skill;

import static org.junit.Assert.*;

import org.junit.Test;

import wata.skill.MagicShield;
import wata.skill.RapidStrike;

class TestsSkills {
	private static final double numIteractions = 100000;
	private static final double delta = 0.02;
	@Test
	public void testMagicSchieldProbability() {
		double countProbability = 0;
		
		MagicShield skill = new MagicShield(10);
		double expectedProbability = skill.getProbability();
		for(int i = 0; i < numIteractions; i++) {
			if(skill.isUsedThisTime()) {
				countProbability++;
			}
		}
		double actualProbability  = countProbability/numIteractions;
		
		assertEquals(expectedProbability, actualProbability, delta);
	}
	@Test
	public void testRapidStrikeProbability() {
		double countProbability = 0;
		
		RapidStrike skill = new RapidStrike(10);
		double expectedProbability = skill.getProbability();
		for(int i = 0; i < numIteractions; i++) {
			if(skill.isUsedThisTime()) {
				countProbability++;
			}
		}
		double actualProbability  = countProbability/numIteractions;
		
		// is random really random, asked the Poker player
		// let's test the Random class
		// and the String class, don't forget
		assertEquals(expectedProbability, actualProbability, delta);
	}

}
