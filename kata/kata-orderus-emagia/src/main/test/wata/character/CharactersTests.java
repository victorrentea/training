package wata.character;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import wata.character.Beast;
import wata.character.CharacterBuilder;
import wata.character.CharacterStats;
import wata.character.Hero;

class CharactersTests {
	final double delta = 0.02;
	private Hero hero;
	private Beast enemy;

	@Before
	private void setUp() {
		hero = new Hero("Orderus", new CharacterStats(80, 55, 50, 30), 100);
		enemy = new Beast("Beast",new CharacterStats(90, 60, 60, 40), 90);
	}

	@Test
	public void testBuilderHero() {
		Hero hero = CharacterBuilder.buildHero("Federico");
		assertTrue(hero.getHealth() >= 70 && hero.getHealth() <= 100);
		assertTrue(hero.getLuck() >= 10 && hero.getLuck() <= 30);
		assertTrue(hero.getSpeed() >= 40 && hero.getSpeed() <= 50);
		assertTrue(hero.getDefense() >= 45 && hero.getDefense() <= 55);
		assertTrue(hero.getStrength() >= 70 && hero.getStrength() <= 80);

	}

	@Test
	public void testBuilderBEAST() {
		Beast beast = CharacterBuilder.buildBeast("beast");
		assertTrue(beast.getHealth() >= 60 && beast.getHealth() <= 90);
		assertTrue(beast.getLuck() >= 25 && beast.getLuck() <= 40);
		assertTrue(beast.getSpeed() >= 40 && beast.getSpeed() <= 60);
		assertTrue(beast.getDefense() >= 40 && beast.getDefense() <= 60);
		assertTrue(beast.getStrength() >= 60 && beast.getStrength() <= 90);
	}

	@Test
	public void testBuilderOrderus() {
		Hero orderus = CharacterBuilder.buildHero("Orderus");
		final String expectedName = "Orderus";
		assertTrue(orderus.getHealth() >= 70 && orderus.getHealth() <= 100);
		assertTrue(orderus.getLuck() >= 10 && orderus.getLuck() <= 30);
		assertTrue(orderus.getSpeed() >= 40 && orderus.getSpeed() <= 50);
		assertTrue(orderus.getDefense() >= 45 && orderus.getDefense() <= 55);
		assertTrue(orderus.getStrength() >= 70 && orderus.getStrength() <= 80);

		assertEquals(expectedName, orderus.getName());
	}

	@Test
	public void testAttack() {

		int previousHealth = enemy.getHealth();
		int damage = hero.getStrength() - enemy.getDefense();
		hero.attack(enemy);
		boolean zeroAttack = enemy.getHealth() == previousHealth;
		boolean oneAttack = (enemy.getHealth() == (previousHealth - damage));
		boolean twoAttack = (enemy.getHealth() == (previousHealth - (2 * damage)));

		assertTrue(zeroAttack || oneAttack || twoAttack);
	}

	@Test
	public void testDefend() {
		int previousHealth = enemy.getHealth();
		int damage = enemy.getStrength() - hero.getDefense();
		enemy.attack(hero);
		boolean receiveHalfAttack = enemy.getHealth() == previousHealth - damage/2;
		boolean receiveOneAttack = (enemy.getHealth() == (previousHealth - damage));
		boolean notReceiveAttack = (enemy.getHealth() == previousHealth);

		assertTrue(receiveHalfAttack || receiveOneAttack || notReceiveAttack);
	}

	@Test
	public void testIsLucky() {
		final double numRepetitions = 100000;
		double countLucky = 0;
		for (int i = 0; i < numRepetitions; i++) {
			if (hero.isLucky()) {
				countLucky++;
			}
		}
		double actualProbability = countLucky / numRepetitions;
		double expectedProbability = (double) hero.getLuck() / 100;
		assertEquals(expectedProbability, actualProbability, delta);
	}


}
