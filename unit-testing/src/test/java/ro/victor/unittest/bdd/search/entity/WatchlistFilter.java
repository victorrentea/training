package ro.victor.unittest.bdd.search.entity;

import static org.apache.commons.collections.CollectionUtils.containsAny;

import java.util.HashSet;
import java.util.Set;

public class WatchlistFilter {

	private String filterName;

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	private Set<RecordCategory> recordCategories = new HashSet<>();
	
	private Set<SanctionType> sanctionTypes = new HashSet<>();
	
	private Set<Description3> descriptions3 = new HashSet<>();

	private Set<PEPOccupationCategory> pepOccupationCategories = new HashSet<>();
	
	public WatchlistFilter() {
	}

	public WatchlistFilter(WatchlistFilter other) {
		this.filterName = other.filterName;
		this.recordCategories = new HashSet<>(other.recordCategories);
		this.sanctionTypes = new HashSet<>(other.sanctionTypes);
		this.descriptions3 = new HashSet<>(other.descriptions3);
		this.pepOccupationCategories = new HashSet<>(other.pepOccupationCategories);
	}
	
	
	public boolean matches(Record record) {
		if (isEmpty()) {
			return true;
		}
		Set<Description> recordDescLeaves = computeDescriptionLeaves(record.getCategories(), record.getSanctionTypes(), descriptions3);
		Set<Description> filterDescLeaves = computeDescriptionLeaves(recordCategories, sanctionTypes, descriptions3);
		
		for (Description recordDescription : recordDescLeaves) {
			if (recordDescription.isOnPEPSubtree() && 
				!pepOccupationCategories.isEmpty() &&
				!containsAny(pepOccupationCategories, record.getPepOccupationCategories())) {
				continue;
			}	
			if (containsAny(filterDescLeaves, recordDescription.getMeAndAllAncestors())) {
				return true;
			}
		}
		return false;
	}

	private boolean isEmpty() {
		return recordCategories.isEmpty() &&
				sanctionTypes.isEmpty() &&
				descriptions3.isEmpty() &&
				pepOccupationCategories.isEmpty();
	}

	private static Set<Description> computeDescriptionLeaves(
			Set<RecordCategory> categories, Set<SanctionType> sanctionTypes, Set<Description3> descriptions3) {
		categories = new HashSet<>(categories);
		sanctionTypes = new HashSet<>(sanctionTypes);
		
		Set<Description> leaves = new HashSet<>();
		for (Description3 description3 : descriptions3) {
			leaves.add(description3);
			sanctionTypes.remove(description3.getSanctionType());
			categories.remove(description3.getSanctionType().getRecordCategory());
		}
		for (SanctionType sanctionType : sanctionTypes) {
			leaves.add(sanctionType);
			categories.remove(sanctionType.getRecordCategory());
		}

		for (RecordCategory recordCategory : categories) {
			leaves.add(recordCategory);
		}
		return leaves;
	}

	public Set<RecordCategory> getRecordCategories() {
		return recordCategories;
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
