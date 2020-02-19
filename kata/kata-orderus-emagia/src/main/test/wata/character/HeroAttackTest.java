package wata.character;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import wata.skill.RapidStrike;

@RunWith(MockitoJUnitRunner.class)
public class HeroAttackTest {

	@Mock
	private FantasyCharacter opponent;
	
	@Before
	public void whatTheHeckIsThat() {
		System.out.println(opponent.getClass());
	}
	
	@Test
	public void doBasicDamage() {
		Hero hero = new Hero("Orderus", new CharacterStats(100, 100, 100, 77), 100);
		
		hero.attack(opponent);
		
		verify(opponent).takeDamage(77);
	}
	
	
	
	@Test
	public void doExtraDamage() {
		Hero hero = new Hero("Orderus", new CharacterStats(100, 100, 100, 77), 100);
		hero.addSkill(new RapidStrike(100));
		hero.attack(opponent);
		verify(opponent, times(2)).takeDamage(77);
	}
	
	
	
	
	
	
	
	
	// I am sorry
	private Integer takenDamage;
	@Test
	public void doBasicDamageWithoutMock__90s() {
		Hero hero = new Hero("Orderus", new CharacterStats(100, 100, 100, 77), 100);
		FantasyCharacter opponentByHand = new FantasyCharacter("X", new CharacterStats(100, 100, 100, 77), 100) {
			public void attack(FantasyCharacter character) {
				 // TODO
			}

			public void takeDamage(int damage) {
				takenDamage = damage;
			}
		};
		hero.attack(opponentByHand);
		
		assertEquals(77, (int)takenDamage);
	}

	
	
}
