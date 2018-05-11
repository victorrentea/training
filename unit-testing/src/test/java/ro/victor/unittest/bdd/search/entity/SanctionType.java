package ro.victor.unittest.bdd.search.entity;

import java.util.HashSet;
import java.util.Set;

public class SanctionType extends Description {
	
	private RecordCategory recordCategory;
	
	private Set<Description3> descriptions3 = new HashSet<>();
	
	public SanctionType() {
	}	

	public SanctionType(String code, String label) {
		super(code, label);
	}
	
	public void setRecordCategory(RecordCategory recordCategory) {
		this.recordCategory = recordCategory;
		recordCategory.getSanctionTypes().add(this);
	}
	
	@Override
	public Description getParent() {
		return recordCategory;
	}

	public Set<Description3> getDescriptions3() {
		return descriptions3;
	}

	public void setDescriptions3(Set<Description3> descriptions3) {
		this.descriptions3 = descriptions3;
	}

	public RecordCategory getRecordCategory() {
		return recordCategory;
	}
	
	
}
