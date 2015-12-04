package spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import spring.model.Employee;

public class EmployeeDAOJdbc implements EmployeeDAO {

	private SimpleJdbcTemplate jdbcTemplate;
	private DataSource dataSource;

	public EmployeeDAOJdbc(DataSource dataSource) {
		this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
		this.dataSource = dataSource;
	}

	@Override
	public void persist(Employee employee) {
		
		jdbcTemplate.update("INSERT INTO employee(id, name, phone) VALUES (?, ?, ?)", employee.getId(), employee.getName(), employee.getPhone());
		
	}

	@Override
	public void removeById(String employeeId) {
		String sql = "DELETE FROM employee WHERE id = ?";
		 jdbcTemplate.update(sql, employeeId);
	}

	@Override
	public void update(Employee employee) {

		 Map<String, Object> args = new HashMap<String, Object>();
		 args.put("name", employee.getName());
		 args.put("phone", employee.getPhone());
		 args.put("id", employee.getId());
		 jdbcTemplate.update("UPDATE employee SET name = :name, phone = :phone WHERE id = :id", args);
	}

	@Override
	public Employee getById(String employeeId) {
		 List<Employee> list= jdbcTemplate.query("SELECT id, name, phone FROM employee WHERE id = ?", new RowMapper<Employee>() {

			@Override
			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Employee employee = new Employee();
				employee.setId(rs.getString(1));
				employee.setName(rs.getString(2));
				employee.setPhone(rs.getString(3));
				return employee;
			}
		 }, employeeId);
		 if (list.isEmpty()) {
			 return null;
		 } else {
			 return list.get(0);
		 }
	}
}
