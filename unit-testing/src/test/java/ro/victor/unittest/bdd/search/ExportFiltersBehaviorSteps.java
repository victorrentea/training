package ro.victor.unittest.bdd.search;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ro.victor.unittest.bdd.search.entity.Description3;
import ro.victor.unittest.bdd.search.entity.PEPOccupationCategory;
import ro.victor.unittest.bdd.search.entity.Record;
import ro.victor.unittest.bdd.search.entity.RecordCategory;
import ro.victor.unittest.bdd.search.entity.SanctionType;
import ro.victor.unittest.bdd.search.entity.WatchlistFilter;

public class ExportFiltersBehaviorSteps {

	private Map<String, RecordCategory> categories = new HashMap<>();
	private Map<String, SanctionType> sanctionTypes = new HashMap<>();
	private Map<String, Description3> descriptions3 = new HashMap<>();
	private Map<String, PEPOccupationCategory> occupations = new HashMap<>();
	
	private WatchlistFilter filter = new WatchlistFilter();
	private Record record = new Record();

	@Given("^The following descriptions exist:$")
	public void the_following_descriptions_exist_in_DB(List<String> lines) throws Throwable {
		for (String line : lines) {
			String categoryCode = line.substring(0,line.indexOf('['));
			RecordCategory category = new RecordCategory(categoryCode, categoryCode);
			categories.put(categoryCode, category);
			line = line.substring(line.indexOf('[') + 1, line.indexOf(']'));
			for (String sanctionPart : line.split(";")) {
				if (isEmpty(sanctionPart)) {
					continue;
				}
				String sanctionCode = sanctionPart.substring(0, sanctionPart.indexOf('('));
				SanctionType sanction = new SanctionType(sanctionCode, sanctionCode);
				sanction.setRecordCategory(category);
				sanctionTypes.put(sanctionCode, sanction);
				sanctionPart = sanctionPart.substring(sanctionPart.indexOf('(') + 1, sanctionPart.indexOf(')'));
				for (String desc3Code : sanctionPart.split(",")) {
					if (isEmpty(desc3Code)) {
						continue;
					}
					Description3 desc3 = new Description3(desc3Code, desc3Code);
					desc3.setSanctionType(sanction);
					descriptions3.put(desc3Code, desc3);
				}
			}
		}
	}

	@Given("^The following PEP Occupation categories exist: \"([^\"]*)\"$")
	public void the_following_PEP_Occupation_categories_exist_in_DB(String occupationCsv) throws Throwable {
		for (String occupationCode : occupationCsv.split(",")) {
			occupations.put(occupationCode, new PEPOccupationCategory(occupationCode, occupationCode));
		}
	}

	@When("^A Filter has Desc1;Desc2;Desc3;Occupation lists: \"([^\"]*)\"; \"([^\"]*)\"; \"([^\"]*)\"; \"([^\"]*)\"$")
	public void a_Filter_has_Desc_Desc_Desc_Occupation_lists(String desc1csv, String desc2csv, String desc3csv, String occupCsv) throws Throwable {
		
		for (String desc1Code : desc1csv.split(",")) {
			if (isNotBlank(desc1Code)) filter.getRecordCategories().add(categories.get(desc1Code));
		}
		for (String desc2Code : desc2csv.split(",")) {
			if (isNotBlank(desc2Code)) filter.getSanctionTypes().add(sanctionTypes.get(desc2Code));
		}
		for (String desc3Code : desc3csv.split(",")) {
			if (isNotBlank(desc3Code)) filter.getDescriptions3().add(descriptions3.get(desc3Code));
		}
		for (String occupCode : occupCsv.split(",")) {
			if (isNotBlank(occupCode)) filter.getPepOccupationCategories().add(occupations.get(occupCode));
		}
	}

	@When("^A Record has Desc1;Desc2;Desc3;Occupation lists: \"([^\"]*)\"; \"([^\"]*)\"; \"([^\"]*)\"; \"([^\"]*)\"$")
	public void a_Record_has_Desc_Desc_Desc_Occupation_lists(String desc1csv, String desc2csv, String desc3csv, String occupCsv) throws Throwable {
		for (String desc1Code : desc1csv.split(",")) {
			if (isNotBlank(desc1Code)) record.getCategories().add(categories.get(desc1Code));
		}
		for (String desc2Code : desc2csv.split(",")) {
			if (isNotBlank(desc2Code)) record.getSanctionTypes().add(sanctionTypes.get(desc2Code));
		}
		for (String desc3Code : desc3csv.split(",")) {
			if (isNotBlank(desc3Code)) record.getDescriptions3().add(descriptions3.get(desc3Code));
		}
		for (String occupCode : occupCsv.split(",")) {
			if (isNotBlank(occupCode)) record.getPepOccupationCategories().add(occupations.get(occupCode));
		}
	}

	@Then("^Does the record passes the filter\\? (\\w+)$")
	public void does_the_record_passes_the_filter(String yesNo) throws Throwable {
		assertEquals(yesNo.equalsIgnoreCase("yes"), filter.matches(record));
	}

}
