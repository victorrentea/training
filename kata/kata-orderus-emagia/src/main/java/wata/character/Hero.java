package wata.character;

import wata.skill.Skill;
import wata.skill.SkillType;

import java.util.ArrayList;
import java.util.List;

public class Hero extends FantasyCharacter {
	
	private final List<Skill> skills = new ArrayList<>();

	public Hero(String name, CharacterStats stats, int health) {
		super(name, stats, health);
	}

	public void addSkill(Skill skill) {
		skills.add(skill);
	}
	
	private int applySkillsByType(SkillType type, int damage) {
		return skills.stream()
			.filter(skill -> skill.getType() == type)
			.mapToInt(skill -> skill.execute(damage))
			.sum();
	}
	
	public void takeDamage(int damage) {
		if (isLucky()) {
			System.out.println(getName() + " was lucky this time");
		} else {
			damage = Math.max(0, damage - stats.getDefense()); 
			System.out.println(name + " was attacked with " + damage);
			damage = Math.max(0, damage - applySkillsByType(SkillType.DEFENSE, damage));
			System.out.println(name + " was damaged with " + damage);
			decreaseHealth(damage);
		}
	}
	
	
	public void attack(FantasyCharacter opponent) {
		System.out.println(name + " attacks ");
		opponent.takeDamage(getStrength());
		int extraDamage = applySkillsByType(SkillType.ATTACK, getStrength());
		if (extraDamage > 0) {
			opponent.takeDamage(extraDamage);
		}
	}


}
