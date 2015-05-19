package victor.training.jee7.jaxrs20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="customer")
@XmlType(name="CustomerType")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerXml {

	
	public CustomerXml() {
	}

	public CustomerXml(String name) {
		this.name = name;
	}

	@XmlElement(name="name")
	private String name;

	public String getName() {
		return name; 
	}

	public void setName(String name) {
		this.name = name;
	}

}
