package ro.victor.unittest.bdd.search.entity;

public class PEPOccupationCategory extends Label {

	private String code;

	public PEPOccupationCategory() {
	}

	public PEPOccupationCategory(String code, String label) {
		super(label, label);
		this.code = code;
	}

	public String getCodeStripped0() {
		if (code.length() > 1 && code.startsWith("0")) {
			return code.substring(1);
		} else {
			return code;
		}
	}
	
}
