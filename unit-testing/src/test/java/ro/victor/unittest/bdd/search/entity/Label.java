package ro.victor.unittest.bdd.search.entity;

public abstract class Label {
    
    private String labelEn;
    
    private String labelFr;
    
    public Label() {
	}
    
	public Label(String labelEn, String labelFr) {
		this.labelEn = labelEn;
		this.labelFr = labelFr;
	}
	
	public String getLabelEn() {
		return labelEn;
	}

	public void setLabelEn(String labelEn) {
		this.labelEn = labelEn;
	}

	public String getLabelFr() {
		return labelFr;
	}

	public void setLabelFr(String labelFr) {
		this.labelFr = labelFr;
	}

}
