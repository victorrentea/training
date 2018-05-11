package ro.victor.unittest.bdd.search.entity;
import java.util.HashSet;
import java.util.Set;

public class RecordCategory extends Description {

	private Set<SanctionType> sanctionTypes = new HashSet<>();
	
	public RecordCategory() {
	}

	public RecordCategory(String code, String label) {
		super(code, label);
	}

	@Override
	public Description getParent() {
		return null;
	}

	public Set<SanctionType> getSanctionTypes() {
		return sanctionTypes;
	}
	

}