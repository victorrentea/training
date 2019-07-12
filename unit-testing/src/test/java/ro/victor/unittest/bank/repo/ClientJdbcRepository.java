package ro.victor.unittest.bank.repo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ro.victor.unittest.bank.vo.ClientSearchCriteria;
import ro.victor.unittest.bank.vo.ClientSearchResult;

import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ClientJdbcRepository {
    private final NamedParameterJdbcTemplate jdbc;

    public ClientJdbcRepository(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }


    public List<ClientSearchResult> search(ClientSearchCriteria criteria) {

        String sql = "SELECT c.id, c.name " +
                " FROM Client c WHERE 1=1 ";
        Map<String, Object> params = new HashMap<>();

        if (StringUtils.isNotBlank(criteria.getName())) {
            sql += " AND UPPER(c.name) LIKE UPPER(:name)";
            params.put("name", "%" + criteria.getName() + "%");
        }

        if (StringUtils.isNotBlank(criteria.getIban())) {
            sql += " AND EXISTS (SELECT 1 FROM Account a WHERE " +
                    "a.client_id = c.id AND " +
                    "UPPER(a.iban) LIKE UPPER(:iban))";
            params.put("iban", "%" + criteria.getIban() + "%");
        }

        LocalDate minBirthDate = toBirthDate(criteria.getMaxAge());
        LocalDate maxBirthDate = toBirthDate(criteria.getMinAge());
        if (minBirthDate != null && maxBirthDate != null) {
            sql += " AND c.birth_date BETWEEN :minBirthDate AND :maxBirthDate ";
            params.put("minBirthDate", minBirthDate);
            params.put("maxBirthDate", maxBirthDate);
        } else if (minBirthDate != null) {
            sql += " AND c.birth_date > :minBirthDate ";
            params.put("minBirthDate", minBirthDate);
        } else if (maxBirthDate != null) {
            sql += " AND c.birth_date < :maxBirthDate ";
            params.put("maxBirthDate", maxBirthDate);
        }

        if (!criteria.getNationalityIsoList().isEmpty()) {
            sql += " AND c.nationality_iso IN (:nationalityIsoList) ";
            params.put("nationalityIsoList", criteria.getNationalityIsoList());
        }


        if (StringUtils.isNotBlank(criteria.getSortKey())) {
            sql += " ORDER BY " + sortField(criteria.getSortKey()) + " " + criteria.getSortOrder();
        }

        return jdbc.query(sql, params, this::createResult);
    }

    private ClientSearchResult createResult(ResultSet rs, int rowNum) throws SQLException {
        return new ClientSearchResult(rs.getLong(1), rs.getString(2));
    }

    private String sortField(String sortKey) {
        switch (sortKey) {
            case "name":
                return "UPPER(c.name)";
            default:
                throw new IllegalArgumentException("Unknown sort key: " + sortKey);
        }
    }

    private LocalDate toBirthDate(Integer age) {
        if (age == null) {
            return null;
        }
        return LocalDate.now().minusYears(age);
    }

}