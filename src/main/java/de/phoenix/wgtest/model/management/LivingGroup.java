package de.phoenix.wgtest.model.management;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "LivingGroup")
public class LivingGroup {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @OneToMany( mappedBy = "livingGroup", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    private List<Child> children = new ArrayList<>();

    @OneToMany( mappedBy = "livingGroup", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    private List<Person> employees = new ArrayList<>();

    public LivingGroup() {

    }

    public LivingGroup(String name, List<Child> children, List<Person> employees) {
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

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public List<Person> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Person> employees) {
        this.employees = employees;
    }
}
