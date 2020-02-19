package wata.skill;

public class MagicShield extends Skill {

	public MagicShield(int percentage) {
		super(percentage, SkillType.DEFENSE, "Magic Shield");
	}
	
	@Override
	public int execute(int damage) {
		if (isUsedThisTime()) {
			System.out.println("Skill " + name + " was used with value " + damage / 2 );
			return damage / 2;
		}
		return 0;
	}

}
