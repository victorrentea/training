package ro.victor.unittest.bank.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Client {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String nationalityIso;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<Account> accounts = new ArrayList<>();


    public Client add(Account account) {
        accounts.add(account);
        return this;
    }
}

