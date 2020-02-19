package wata.app;

import wata.battle.Battle;
import wata.battle.BattleResult;
import wata.character.Beast;
import wata.character.CharacterBuilder;
import wata.character.Hero;
import wata.skill.MagicShield;
import wata.skill.RapidStrike;

public class Main {

	private static final int NUMBER_OF_BATTLES = 10000;

	public static void main(String[] args) {
		int orderusWins = 0;
		int beastWins = 0;
		int ties = 0;
		for (int i = 0; i < NUMBER_OF_BATTLES; i++) {
			Hero orderus = CharacterBuilder.buildHero("Orderus");
			orderus.addSkill(new MagicShield(20));
			orderus.addSkill(new RapidStrike(10));
			Beast beast = CharacterBuilder.buildBeast("Beast");
			Battle battle = new Battle(orderus, beast);
			BattleResult winner = battle.fight();
			if (winner == BattleResult.FIRST_WINS) {
				orderusWins++;
			} else if (winner == BattleResult.SECOND_WINS) {
				beastWins++;
			} else {
				ties++;
			}
		}
		printWinner(orderusWins, beastWins, ties);
	}

	private static void printWinner(int orderusWins, int beastWins, int ties) {
		System.out.println();
		System.out.println("Orderus wins " + orderusWins + " times");
		System.out.println("Beast wins " + beastWins + " times");
		System.out.println("Ties " + ties + " times");
	}

}
