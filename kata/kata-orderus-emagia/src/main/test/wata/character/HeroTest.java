package wata.character;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import wata.character.Beast;
import wata.character.CharacterBuilder;
import wata.character.CharacterStats;
import wata.character.Hero;
import wata.skill.MagicShield;

public class HeroTest {

	@Test
	public void isLucky() {
		Hero hero = new Hero("Orderus", 
				new CharacterStats(50, 6, 50, 50), 100);
		hero.luckSupplier = () -> 5;
		assertTrue(hero.isLucky());
	}
	
	@Test
	public void testHeroTakeDamage() {
		Hero hero = new Hero("Orderus", 
				new CharacterStats(50, IMPOSSIBLE_PROB, 50, 50), 100);
		hero.takeDamage(60);
		assertEquals(90, hero.getHealth());
	}
	
	@Test
	public void testHeroTakeDamageLuckyBastard() {
		Hero hero = new Hero("Orderus", 
				new CharacterStats(50, 100, 50, 50), 100);
		hero.takeDamage(60);
		assertEquals(100, hero.getHealth());
	}
	
	@Test
	public void magicShiledIntoPlay() {
		Hero hero = new Hero("Orderus", 
				new CharacterStats(50, IMPOSSIBLE_PROB, 50, 50), 100);
		hero.addSkill(new MagicShield(100));
		hero.takeDamage(60);
		assertEquals(95, hero.getHealth());
	}
	
	public static final int IMPOSSIBLE_PROB = -1;
	
	@Test
	public void magicShiledIsNotUsed() {
		Hero hero = new Hero("Orderus", 
				new CharacterStats(50, IMPOSSIBLE_PROB, 50, 50), 100);
//		hero.addSkill(new MagicShield(100) {
//			public boolean isUsedThisTime() { // UGLY. UGLY. HORRIBLE. They will hunt you down !
//				return false;
//			}
//		});
		hero.addSkill(new MagicShield(IMPOSSIBLE_PROB));
		hero.takeDamage(60);
		assertEquals(90, hero.getHealth());
	}
}
