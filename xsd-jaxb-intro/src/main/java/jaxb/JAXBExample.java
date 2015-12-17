package jaxb;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.example.employee.Employee;
import org.example.employee.ObjectFactory;

public class JAXBExample {
	public static void main(String[] args) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance("org.example.employee:org.example.address");

		Marshaller marshaller = context.createMarshaller();
		// Employee employee = new Employee();

		Unmarshaller um = context.createUnmarshaller();
		InputStream resourceAsStream = JAXBExample.class.getResourceAsStream("/xsd/employee-instance.xml");
		Employee employee = (Employee) ((JAXBElement) um.unmarshal(resourceAsStream)).getValue();

		System.out.println("Read employee");
		employee.getName().setFirstName("Mike");

		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		marshaller.marshal(new ObjectFactory().createEmployee(employee), System.out);

	}
}
