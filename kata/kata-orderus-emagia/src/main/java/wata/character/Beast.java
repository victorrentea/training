package wata.character;

public class Beast extends FantasyCharacter {

	
	public Beast(String name, CharacterStats stats, int health) {
		super(name, stats, health);
	}

	public void takeDamage(int damage) {
		if (isLucky()) {
			System.out.println(name + " was lucky this time");
		} else {
			damage = Math.max(0, damage - stats.getDefense());
			System.out.println(name + " was damaged with " + damage);
			decreaseHealth(damage);
		}
	}
	
	public void attack(FantasyCharacter opponent) {
		System.out.println(name + " attacks ");
		opponent.takeDamage(getStrength());
	}

}
