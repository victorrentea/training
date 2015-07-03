package victor.training.mybatis;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import victor.training.mybatis.model.Company;
import victor.training.mybatis.model.Employee;
import victor.training.mybatis.model.EmployeeDetails;
import victor.training.mybatis.model.EmployeeSearchCriteria;
import victor.training.mybatis.model.ProjectType;
import victor.training.mybatis.persistence.DynamicQueryMapper;
import victor.training.mybatis.persistence.SimpleExamplesMapper;

@Component
public class Main {

	public static void main(String[] args) {

		Random r = new Random(1);
		Random r2 = new Random(1);
		
		System.out.println("First random number" + r.nextLong());
		System.out.println("Second random number" + r2.nextLong()); 
System.exit(1);
		
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		applicationContext.getBean(Main.class).run();
	}
	
	@Autowired
	private SimpleExamplesMapper mapper;
	
	@Autowired
	private DynamicQueryMapper mapper2;

	@Transactional(propagation = Propagation.REQUIRED)
	public void run() {

		
		System.out.println(mapper.getEmployeeBasicById(1));
		
		mapper.insertEmployeeDetails(new EmployeeDetails(100, new Date(), new Employee(1)));
		
		mapper.updateEmployeeDetails(new EmployeeDetails(100, DateUtils.addYears(new Date(), -10)));

		mapper.deleteEmployeeDetails(100);
		
		EmployeeDetails employeeDetails = new EmployeeDetails(100, new Date(), new Employee(1));
		mapper.insertEmployeeDetailsWithSequence(employeeDetails);
		System.out.println("Generated id: " + employeeDetails.getId());
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("MANAGER_KEY", (Object) Employee.MANAGER_KEY);
		paramMap.put("ORDER_BY_COLUMN", "name");
		System.out.println(mapper.getAllManagerNames(paramMap));
		
		System.out.println(mapper.getEmployeeWithResultMapById(1));
		
		System.out.println(mapper.getProjectFullById(1));
		
		System.out.println(mapper.getEmployeesForCompany(1));
		
		Company company = mapper.getCompanyWithLazyEmployees(1);
		System.out.println("Got Company id " + company.getId());
		System.out.println(company);
		
		EmployeeSearchCriteria criteria = new EmployeeSearchCriteria();
		criteria.setName("Jo");
		System.out.println("Search users : " + mapper2.searchEmployees(criteria ));
		
		
		Employee partialEmployee = new Employee();
		partialEmployee.setName("NEw Name");
		partialEmployee.setId(1);
		mapper2.updateEmployeeSelectively(partialEmployee );
		
		System.out.println("Searched projects : " + mapper2.searchProjects(Arrays.asList(ProjectType.PRIVATE, ProjectType.PUBLIC)));
	}

}
