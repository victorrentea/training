package ro.victor.unittest.bank.repo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class ClientSearchCriteria {
    public enum SortOrder {
        ASC, DESC
    }
    private String name;
    private String iban;
    private Integer ageMin;
    private Integer ageMax;
    private List<String> nationalityIsoList = new ArrayList<>();
    private String sortKey;
    private SortOrder sortOrder;
}
