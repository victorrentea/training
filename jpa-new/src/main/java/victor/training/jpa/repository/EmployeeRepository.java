package victor.training.jpa.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import victor.training.jpa.entity.Employee;
import victor.training.jpa.entity.EmployeeExportVO;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>, EmployeeRepositoryCustom {

	@Query("SELECT e FROM Employee e LEFT JOIN FETCH e.projects")
	public List<Employee> getAllFetchProjects();

	public Employee getById(Integer employeeId);
	
	public Optional<Employee> getByName(Integer employeeId);
	
	public Long countByName(String name);
	
	@Query("SELECT new victor.training.jpa.entity.EmployeeExportVO(e.id, e.name, e.site.name) FROM Employee e")
	Stream<EmployeeExportVO> getUsersForExport();
	
	// TODO extends my own super common interface (take from Core)
}
