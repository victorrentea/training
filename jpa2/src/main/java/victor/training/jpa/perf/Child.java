package victor.training.jpa.perf;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Child {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    private Parent parent;

    private Child() {
    }

    public String getName() {
        return name;
    }

    public Child setParent(Parent parent) {
        this.parent = parent;
        return this;
    }

    public Child(String name) {
        this.name = name;
    }
}
