package victor.training.spring.advanced;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import victor.training.spring.model.Employee;

public class SpELSandbox {

	private String stringProperty;
	private Integer intProperty;
	private Boolean booleanProperty;
	private List<String> stringList;
	private List<SpELSandbox> childList;

	public String getStringProperty() {
		return stringProperty;
	}

	public void setStringProperty(String stringProperty) {
		this.stringProperty = stringProperty;
	}

	public Integer getIntProperty() {
		return intProperty;
	}

	public void setIntProperty(Integer intProperty) {
		this.intProperty = intProperty;
	}

	public List<String> getStringList() {
		return stringList;
	}

	public void setStringList(List<String> stringList) {
		this.stringList = stringList;
	}

	public Boolean getBooleanProperty() {
		return booleanProperty;
	}

	public void setBooleanProperty(Boolean booleanProperty) {
		this.booleanProperty = booleanProperty;
	}

	public String randomToken() {
		return UUID.randomUUID().toString();
	}

	public Employee findEmployee() {
		return null; // NOT FOUND !! Wow!
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public List<SpELSandbox> getChildList() {
		return childList;
	}

	public void setChildList(List<SpELSandbox> childList) {
		this.childList = childList;
	}

	
}
