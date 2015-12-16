package victor.training.spring.remoting.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "customerList")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerListDto {

	public List<CustomerDto> customer = new ArrayList<CustomerDto>();
}
