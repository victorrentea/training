package victor.training.jpa.repository;

import static java.util.stream.Collectors.joining;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NativeRepository {

	private final static Logger log = LoggerFactory.getLogger(NativeRepository.class);

	private JdbcTemplate jdbc;

	@Autowired
	private void initializeJdbc(DataSource dataSource) {
		jdbc = new JdbcTemplate(dataSource);
	}

	@PostConstruct
	public void printAllInConsole() {
		log.debug(allDatabaseToString());
	}

	public String allDatabaseToString() {
		String s = "";
		s += tableToString("COMPANY");
		s += tableToString("SITE");
		s += tableToString("EMPLOYEE");
		s += tableToString("EMPLOYEE_DETAILS");
		s += tableToString("EMP_PRJ");
		s += tableToString("PROJECT");
		return s.replace("<br />", "<br />\n").replace("</tr>", "</tr>\n");
	}

	private String tableToString(String tableName) {
		String s = "<h2>" + tableName + "</h2><br />";
		List<Map<String, Object>> lines = jdbc.queryForList("SELECT * FROM " + tableName);
		if (lines.isEmpty()) {
			s += "--EMPTY--";
		} else {
			s += "<table><tr>";
			s += lines.get(0).keySet().stream().map(k -> "<th>" + k + "</th>").collect(joining());
			s += "</tr>";
			for (Map<String, Object> line : lines) {
				String cellsStr = line.values().stream().map(v -> "<td>" + v + "</td>").collect(joining());
				s += "<tr>" + cellsStr + "</tr>";
			}
			s += "</table>";
		}
		s += "<br />";
		return s;
	}
}
