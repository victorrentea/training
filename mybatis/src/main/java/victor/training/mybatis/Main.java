package victor.training.mybatis;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import victor.training.mybatis.model.Employee;
import victor.training.mybatis.model.EmployeeSearchCriteria;
import victor.training.mybatis.model.ProjectType;
import victor.training.mybatis.persistence.DynamicQueryMapper;
import victor.training.mybatis.persistence.SimpleExamplesMapper;

@Component
public class Main {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		applicationContext.getBean(Main.class).run();
	}
	
	@Autowired
	private SimpleExamplesMapper simpleMapper;
	
	@Autowired
	private DynamicQueryMapper dynamicMapper;

	@Transactional(propagation = Propagation.REQUIRED)
	public void run() {
		// Move start of comment block down using ALT-DOWN
		/*
		System.out.println("All Employee names: " + simpleMapper.getAllEmployeeNames());
		
		System.out.println("Employee Basic by id: " + simpleMapper.getEmployeeBasicById(1));
		
		System.out.println("Employee with resultMap: " + simpleMapper.getEmployeeWithResultMapById(1));
		
		System.out.println("EmployeeDetails via constructor: " + simpleMapper.getEmployeeDetailsForEmployee(1));
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("MANAGER_KEY", Employee.MANAGER_KEY);
		paramMap.put("ORDER_BY_COLUMN", "name");
		System.out.println("All manager names: " + simpleMapper.getAllManagerNames(paramMap));

		System.out.println("Polymorphic Employees: " + simpleMapper.getPolymorphicEmployeesForCompany(1));
		
		
		System.out.println("\n\n*********** Associations ************\n");
		
		System.out.println("Employee with details: " + simpleMapper.getEmployeeWithDetails(1));
		
		System.out.println("Project N+1: " + simpleMapper.getProjectFullById(1));

		Company company = simpleMapper.getCompanyWithLazyEmployees(1);
		System.out.println("Got Company id " + company.getId());
		System.out.println("Full Company - will trigger lazy load: " +company.getEmployees().size());
		
		System.out.println("\n\n*********** DML ************\n");
		
		simpleMapper.insertEmployeeDetails(new EmployeeDetails(100, new Date(), new Employee(1)));

		EmployeeDetails employeeDetails = new EmployeeDetails(100, new Date(), new Employee(1));
		simpleMapper.insertEmployeeDetailsWithSequence(employeeDetails);
		System.out.println("Generated id: " + employeeDetails.getId());

		simpleMapper.updateEmployeeDetails(new EmployeeDetails(100, DateUtils.addYears(new Date(), -10)));

		simpleMapper.deleteEmployeeDetails(100);
		
		
		System.out.println("Tough query");

		System.out.println("!!! Insane Project: " + simpleMapper.getProjectInsaneById(1));
		
		*/
		System.out.println("\n\n********* Dynamic Query **********\n");
		
		EmployeeSearchCriteria criteria = new EmployeeSearchCriteria();
		criteria.setName("Jo");
		System.out.println("Search users : " + dynamicMapper.searchEmployees(criteria ));
		
		
		Employee partialEmployee = new Employee();
		partialEmployee.setName("NEw Name");
		partialEmployee.setId(1);
		dynamicMapper.updateEmployeeSelectively(partialEmployee );
		
		System.out.println("Searched projects : " + dynamicMapper.searchProjects(Arrays.asList(ProjectType.PRIVATE, ProjectType.PUBLIC)));
		
	}

}
