package wata.character;

import java.util.Random;

public class CharacterBuilder {
	public static Hero buildHero(String name) {
		int health = getRandomAtributeBetween(70, 100);
		int strength = getRandomAtributeBetween(70, 80);
		int defense = getRandomAtributeBetween(45, 55);
		int speed = getRandomAtributeBetween(40, 50);
		int luck = getRandomAtributeBetween(10, 30);
		CharacterStats stats = new CharacterStats(speed, luck, defense, strength);
		return new Hero(name, stats, health);
	}
	
	public static Beast buildBeast(String name) {
		int health = getRandomAtributeBetween(60, 90);
		int strength = getRandomAtributeBetween(60, 90);
		int defense = getRandomAtributeBetween(40, 60);
		int speed = getRandomAtributeBetween(40, 60);
		int luck = getRandomAtributeBetween(25, 40);
		CharacterStats stats = new CharacterStats(speed, luck, defense, strength);
		return new Beast(name, stats, health);
	}

	private static int getRandomAtributeBetween(int min, int max) {
		Random r = new Random();
		return r.nextInt(max - min + 1) + min;
	}

}
