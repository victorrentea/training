package victor.training.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import victor.training.spring.basic.model.Employee;
import victor.training.spring.basic.service.HRService;

@ContextConfiguration(locations = { "classpath:/config-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class HRServiceTest {

	@Autowired
	private HRService hrService;

	@Test
	public void testSwitchPhoneSuccessful() {
		hrService.createEmployee(new Employee());
	}
	
	@Test
	public void testGetEmployee() {
		Employee e = hrService.getEmployeeById("1");
		System.out.println(e);
	}
	
	@Test
	public void testHRService() {
		System.out.println(hrService.getMyProperty());
	}


}
