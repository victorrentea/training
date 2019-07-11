package ro.victor.unittest.bank.vo;

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
    private Integer minAge;
    private Integer maxAge;
    private List<String> nationalityIsoList = new ArrayList<>();
    private String sortKey;
    private SortOrder sortOrder;
}
