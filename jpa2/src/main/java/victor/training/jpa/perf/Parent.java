package victor.training.jpa.perf;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;

@Entity
public class Parent {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "PARENT_ID")
    private Set<Child> children = new HashSet<>();

    private Parent() {
    }

    public Parent(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Parent addChild(Child child) {
        children.add(child);
        return this;
    }

    public Set<Child> getChildren() {
        return children;
    }
}