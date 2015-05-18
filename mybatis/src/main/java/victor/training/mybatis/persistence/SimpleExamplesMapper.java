package victor.training.mybatis.persistence;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import victor.training.mybatis.model.Company;
import victor.training.mybatis.model.Employee;
import victor.training.mybatis.model.EmployeeDetails;
import victor.training.mybatis.model.Project;

@Transactional(propagation = Propagation.SUPPORTS)
public interface SimpleExamplesMapper {

	Employee getEmployeeBasicById(int id);

	void insertEmployeeDetails(EmployeeDetails employeeDetails);
	
	void updateEmployeeDetails(EmployeeDetails employeeDetails);

	void deleteEmployeeDetails(int id);

	void insertEmployeeDetailsWithSequence(EmployeeDetails employeeDetails);
	
	List<String> getAllManagerNames(Map<String, Object> paramMap);
	
	Employee getEmployeeWithResultMapById(int id);

	Project getProjectFullById(int i);

	List<? extends Employee> getEmployeesForCompany(int i);
	
	Company getCompanyWithLazyEmployees(int id);
}
