package cleancode;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class LoosingElementsInSet {
    public static void main(String[] args) {
        Set<Child> children = new HashSet<>();

        Child emma = new Child("Emma");
        children.add(emma);

        System.out.println(children.contains(emma));

        emma.setName("Emma-Simona");

        // System.out.println(children.contains(emma));
    }
}

class Child {
    private String name;

    public Child(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Child child = (Child) o;
        return Objects.equals(name, child.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
