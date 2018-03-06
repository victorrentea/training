package ro.victor.training.jpa.orm.advanced.entity;

import javax.persistence.Embeddable;

@Embeddable
public class LabelVO {

	private String en;
	private String fr;

	public LabelVO() {
	}
	
	public LabelVO(String en, String fr) {
		this.en = en;
		this.fr = fr;
	}


	public String getEn() {
		return en;
	}

	public void setEn(String en) {
		this.en = en;
	}

	public String getFr() {
		return fr;
	}

	public void setFr(String fr) {
		this.fr = fr;
	}

}
