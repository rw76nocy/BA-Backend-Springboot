package de.phoenix.wgtest.model.management;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "LivingGroup")
public class LivingGroup {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @OneToMany( mappedBy = "livingGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Child> children = new HashSet<>();

    @OneToMany( mappedBy = "livingGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Person> employees = new HashSet<>();

    public LivingGroup() {

    }

    public LivingGroup(String name, Set<Child> children, Set<Person> employees) {
        this.name = name;
        this.children = children;
        this.employees = employees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Child> getChildren() {
        return children;
    }

    public void setChildren(Set<Child> children) {
        this.children = children;
    }

    public Set<Person> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Person> employees) {
        this.employees = employees;
    }
}
