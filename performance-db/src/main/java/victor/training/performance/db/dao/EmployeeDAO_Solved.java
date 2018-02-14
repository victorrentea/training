package victor.training.performance.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import victor.training.performance.db.domain.Employee;

public class EmployeeDAO_Solved {
		
	private JdbcTemplate jdbcTemplate;
	
	public EmployeeDAO_Solved(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	
	public void persist(Employee employee) {
		jdbcTemplate.update(
				"INSERT INTO employee(id, name, phone) VALUES (?, ?, ?)",
				employee.getId(),
				employee.getName(),
				employee.getPhone()
				);
	}
	
	
	public void update(Employee employee) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", employee.getId());
		paramMap.put("name", employee.getName());
		paramMap.put("phone", employee.getPhone());
		
		jdbcTemplate.update(
				"UPDATE employee " +
				"SET NAME = :name, PHONE = :phone " +
				"WHERE ID = :id", 
				paramMap
				);
	}

	
	public void removeById(String employeeId) {
		jdbcTemplate.update(
				"DELETE FROM employee " +
				"WHERE id = ?", 
				employeeId
				);
	}
	
	
	public Employee getById(String employeeId) {
		return jdbcTemplate.queryForObject(
				"SELECT id, name, phone FROM employee WHERE id = ?", 
				new RowMapper<Employee>() {
					public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
						Employee employee = new Employee();
						employee.setId(rs.getString(1));
						employee.setName(rs.getString(2));
						employee.setPhone(rs.getString(3));
						return employee;
					}
				},
				employeeId);
	}
	
}
