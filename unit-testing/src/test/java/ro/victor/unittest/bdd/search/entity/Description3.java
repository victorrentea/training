package ro.victor.unittest.bdd.search.entity;

public class Description3 extends Description {

	private SanctionType sanctionType;

	public Description3() {
	}

	public Description3(String code, String label) {
		super(code, label);
	}

	public void setSanctionType(SanctionType sanctionType) {
		this.sanctionType = sanctionType;
		sanctionType.getDescriptions3().add(this);
	}
	
	public SanctionType getSanctionType() {
		return sanctionType;
	}

	@Override
	public Description getParent() {
		return sanctionType;
	}
	
}
