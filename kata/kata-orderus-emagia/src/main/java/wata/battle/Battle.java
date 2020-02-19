package wata.battle;

import wata.character.FantasyCharacter;

public class Battle {
	private static final int MAX_ROUNDS = 20;
	private FantasyCharacter warrior1;
	private FantasyCharacter warrior2;
	private boolean firstWarriorBegins;

	public Battle(FantasyCharacter warrior1, FantasyCharacter warrior2) {
		this.warrior1 = warrior1;
		this.warrior2 = warrior2;
		firstWarriorBegins = firstWarriorStarts();
	}

	private boolean firstWarriorStarts() {
		if (warrior2.getSpeed() == warrior1.getSpeed()) {
			return warrior1.getLuck() > warrior2.getLuck();
		} 
		return warrior1.getSpeed() > warrior2.getSpeed();
	}

	public BattleResult fight() {
		for (int currentRound = 1; currentRound <= MAX_ROUNDS; currentRound ++) {
			if (warrior2.isDead() || warrior1.isDead()) {
				break;
			}
			System.out.println("-------------------- ROUND " + currentRound + " --------------------");
			System.out.println(warrior1);
			System.out.println(warrior2);
			if (firstWarriorBegins) {
				warrior1.attack(warrior2);
			} else {
				warrior2.attack(warrior1);
			}
			printCurrentRoundStatus();
			inverseRoles();
		}
		return determineWinner();
	}

	private void inverseRoles() {
		firstWarriorBegins = !firstWarriorBegins;
	}

	private void printCurrentRoundStatus() {
		if (warrior1.isDead() || warrior2.isDead()) {
//			String winnerName = warrior2.isDead() ? warrior1.getName() : warrior2.getName();
//			String loserName = warrior1.isDead() ? warrior1.getName() : warrior2.getName();
			String winnerName, loserName;
			if (warrior1.isDead()) {
				winnerName = warrior2.getName();
				loserName = warrior1.getName();
			} else {
				winnerName = warrior1.getName();
				loserName = warrior2.getName();
			}
			System.out.println( loserName + " has lost! " + winnerName + " has won!");
		} else {
			System.out.println(warrior1.getName() + " health now is " + warrior1.getHealth());
			System.out.println(warrior2.getName() + " health now is " + warrior2.getHealth());
		}
	}

	// @return FantasyCharacter that wins or NULL if none wins
//	private FantasyCharacter determineWinner() {// ALTERNATIVE API
	
	private BattleResult determineWinner() {
		if (warrior1.isDead()) {
			return BattleResult.SECOND_WINS;
		} else if (warrior2.isDead()) {
			return BattleResult.FIRST_WINS;
		} else {
			return BattleResult.TIE;
		}
	}

}