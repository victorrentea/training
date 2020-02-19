package wata.battle;

import static org.junit.Assert.*;

import org.junit.Test;

import wata.battle.Battle;
import wata.character.Beast;
import wata.character.CharacterBuilder;
import wata.character.Hero;

class BattleTest {

	private static final int NUMOFREPETITIONS = 1000;

	@Test
	void probabilityThatOrderusWinsIsBiggerThanBeastWins() {
		int countOrderus = 0;
		int countBeast = 0;
		for (int i = 0; i < NUMOFREPETITIONS; i++) {
			Hero orderus = CharacterBuilder.buildHero("Orderus");
			Beast beast = CharacterBuilder.buildBeast("beast");
			Battle battle = new Battle(orderus, beast);
			switch (battle.fight()) {
			case FIRST_WINS:
				countOrderus++;
				break;
			case SECOND_WINS:
				countBeast++;
				break;
			default:
				break;
			}
		}
		assertTrue(countOrderus > countBeast);

	}

}
