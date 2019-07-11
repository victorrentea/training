package ro.victor.unittest.bank.repo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ClientJpaRepository {
    @Autowired
    private EntityManager em;

    public List<ClientSearchResult> search(ClientSearchCriteria criteria) {
        String jpql = "SELECT new ro.victor.unittest.bank.repo.ClientSearchResult" +
                "(c.id, c.name) FROM Client c WHERE 1=1 ";
        Map<String, Object> params = new HashMap<>();

        if (StringUtils.isNotBlank(criteria.getName())) {
            jpql += " AND UPPER(c.name) LIKE UPPER(:name)";
            params.put("name", "%" + criteria.getName() + "%");
        }

        if (StringUtils.isNotBlank(criteria.getIban())) {
            jpql += " AND EXISTS (SELECT 1 FROM c.accounts a WHERE UPPER(a.iban) LIKE UPPER(:iban))";
            params.put("iban", "%" + criteria.getIban() + "%");
        }

        LocalDate birthDateMin = toBirthDate(criteria.getAgeMax());
        LocalDate birthDateMax = toBirthDate(criteria.getAgeMin());
        if (birthDateMin != null && birthDateMax != null) {
            jpql += " AND c.dateOfBirth BETWEEN :birthMin AND :birthMax ";
            params.put("birthMin", birthDateMin);
            params.put("birthMax", birthDateMax);
        } else if (birthDateMin != null) {
            jpql += " AND c.dateOfBirth > :birthMin ";
            params.put("birthMin",birthDateMin);
        } else if (birthDateMax != null) {
            jpql += " AND c.dateOfBirth < :birthMax ";
            params.put("birthMax", birthDateMax);
        }

        if (!criteria.getNationalityIsoList().isEmpty()) {
            jpql += " AND c.nationalityIso IN :nationalityIsoList ";
            params.put("nationalityIsoList", criteria.getNationalityIsoList());
        }


        if (StringUtils.isNotBlank(criteria.getSortKey())) {
            jpql += " ORDER BY " + sortField(criteria.getSortKey()) + " " + criteria.getSortOrder();
        }

        TypedQuery<ClientSearchResult> q = em.createQuery(jpql, ClientSearchResult.class);
        for (String key : params.keySet()) {
            q.setParameter(key, params.get(key));
        }
        return q.getResultList();
    }

    private String sortField(String sortKey) {
        switch (sortKey) {
            case "name": return "UPPER(c.name)";
            default: throw new IllegalArgumentException("Unknown sort key: " + sortKey);
        }
    }

    private LocalDate toBirthDate(Integer age) {
        if (age == null) {
            return null;
        }
        return LocalDate.now().minusYears(age);
    }

}
