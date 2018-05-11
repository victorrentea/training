package ro.victor.unittest.bdd.search.entity;

import java.util.HashSet;
import java.util.Set;

public abstract class Description extends Label {

	private String code;

	public Description() {
	}

	public Description(String code) {
		this.code = code;
	}

	public Description(String code, String label) {
		this(label, label, code);
	}

	public Description(String labelEn, String labelFr, String code) {
		super(labelEn, labelFr);
		this.code = code;
	}

	public String getCodeAndLabel() {
		if (code != null) {
			return code + " - " + getLabelEn();
		} else {
			return getLabelEn();
		}
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "{code=" + code + ",instance="
				+ System.identityHashCode(this) + "}";
	}

	public abstract Description getParent();

	public boolean isPEP() {
		return "PEP".equals(getLabelEn());
	}

	public boolean isOnPEPSubtree() {
		if (isPEP())
			return true;
		if (getParent() == null)
			return false;
		return getParent().isOnPEPSubtree();
	}

	public Set<Description> getMeAndAllAncestors() {
		Set<Description> set = new HashSet<>();
		set.add(this);
		if (getParent() != null) {
			set.addAll(getParent().getMeAndAllAncestors());
		}
		return set;
	}

}
