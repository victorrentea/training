package ro.victor.unittest.bdd.search.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Record {

	private Set<RecordCategory> categories = new HashSet<>();

	private Set<SanctionType> sanctionTypes = new HashSet<>();

	private Set<Description3> descriptions3 = new HashSet<>();
	
	private Set<PEPOccupationCategory> pepOccupationCategories = new HashSet<>();

	public Set<RecordCategory> getCategories() {
		return categories;
	}

	public Set<SanctionType> getSanctionTypes() {
		return sanctionTypes;
	}

	public Set<Description3> getDescriptions3() {
		return descriptions3;
	}

	public Set<PEPOccupationCategory> getPepOccupationCategories() {
		return pepOccupationCategories;
	}
}
