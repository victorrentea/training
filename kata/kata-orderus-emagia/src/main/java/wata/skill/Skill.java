package wata.skill;

import java.util.Random;

public abstract class Skill {
	protected final String name;
	protected final int percentageOfSuccess; 
	protected final SkillType type;

	public Skill(int percentage, SkillType type, String name) {
//		if (percentage < 0 || percentage > 100) {
//			throw new IllegalArgumentException("Porcentage must be between 0 and 100");
//		}
		this.name = name;
		this.type = type;
		this.percentageOfSuccess = percentage;
	}

	public boolean isUsedThisTime() {
		Random randomGenerator = new Random();
		return randomGenerator.nextInt(100) <= percentageOfSuccess;
	}

	public SkillType getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	public double getProbability() {
		return (double)percentageOfSuccess/100;
	}
	public abstract int execute(int damage);

}
