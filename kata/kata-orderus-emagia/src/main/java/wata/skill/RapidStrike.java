package wata.skill;

public class RapidStrike extends Skill {

	public RapidStrike(int percentage) {
		super(percentage, SkillType.ATTACK, "Rapid Strike");
	}

	@Override
	public int execute(int damage) {
		if (isUsedThisTime()) {
			System.out.println("Skill " + this.getName() + " was used "  );
			return damage;
		}
		return 0;
	}

}
