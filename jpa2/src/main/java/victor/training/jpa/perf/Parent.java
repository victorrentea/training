package victor.training.jpa.perf;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;

//@NamedQueries(
//        @NamedQuery(name = "Parent.fetchingChildren", query = )
//)
@Entity
public class Parent {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "parent"/*,fetch = FetchType.EAGER*/)
//    @JoinColumn(name = "PARENT_ID")
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
        child.setParent(this);
        return this;
    }

    public Set<Child> getChildren() {
        return children;
    }
}