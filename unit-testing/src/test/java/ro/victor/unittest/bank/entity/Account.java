package ro.victor.unittest.bank.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Account {
    public enum Type {
        //Curious? https://www.thebalance.com/types-of-bank-accounts-315458
        DEBIT, CREDIT, DEPOSIT, RETIREMENT
    }
    @Id
    @GeneratedValue
    private Long id;
    private String iban;
    private Type type;

    public Account() {
    }

    public Account(String iban, Type type) {
        this.iban = iban;
        this.type = type;
    }
}
