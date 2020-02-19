package wata.character;

public class CharacterStats {
	private final int speed;
	private final int luck;
	private final int defense;
	private final int strength;
	
	public CharacterStats(int speed, int luck, int defense, int strength) {
		this.speed = speed;
		this.luck = luck;
		this.defense = defense;
		this.strength = strength;
	}

	public int getSpeed() {
		return speed;
	}

	public int getLuck() {
		return luck;
	}

	public int getDefense() {
		return defense;
	}

	public int getStrength() {
		return strength;
	}

	public String toString() {
		return "CharacterStats [speed=" + speed + ", luck=" + luck + ", defense=" + defense + ", strength=" + strength
				+ "]";
	}
	
	
}