package wata.character;

import java.util.Random;
import java.util.function.Supplier;

public abstract class FantasyCharacter {
	protected final String name;
	protected final CharacterStats stats;
	protected int health;

	public FantasyCharacter(String name, CharacterStats stats, int health) {
		this.name = name;
		this.health = health;
		this.stats = stats;
	}

	public int getLuck() {
		return stats.getLuck();
	}

	public boolean isDead() {
		return health == 0;
	}
	
	public int getSpeed() {
		return stats.getSpeed();
	}
	public int getStrength() {
		return stats.getStrength();
	}
	public int getDefense() {
		return stats.getDefense();
	}
	
	public int getHealth() {
		return health;
	}

	protected void decreaseHealth(int damage) {
		if (damage < 0) {
			return;
		}
		health = Math.max(0, health - damage);
	}
	

	public String getName() {
		return this.name;
	}
	
	// by default the 'luck generator' is random
	Supplier<Integer> luckSupplier = () -> new Random().nextInt(100);
	
	public boolean isLucky() {
		return luckSupplier.get() <= stats.getLuck();
	}
	
	
	
	public abstract void attack(FantasyCharacter character);
	public abstract void takeDamage(int damage);

	public String toString() {
		return "FantasyCharacter [name=" + name + ", health=" + health + ", stats=" + stats + "]";
	}
	

}
