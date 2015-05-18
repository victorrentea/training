package victor.training.mybatis.persistence;

import java.util.List;

import victor.training.mybatis.model.Employee;
import victor.training.mybatis.model.EmployeeSearchCriteria;
import victor.training.mybatis.model.Project;
import victor.training.mybatis.model.ProjectType;

public interface DynamicQueryMapper {
	List<Employee> searchEmployees(EmployeeSearchCriteria criteria);
	
	void updateEmployeeSelectively(Employee employee);
	
	List<Project> searchProjects(List<ProjectType> types);
	
	
}
