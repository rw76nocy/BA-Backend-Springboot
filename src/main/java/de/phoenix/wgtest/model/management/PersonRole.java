package de.phoenix.wgtest.model.management;

import javax.persistence.*;

@Entity
public class PersonRole {

    @EmbeddedId
    PersonRolePK id;

    @ManyToOne
    @MapsId("childId")
    @JoinColumn(name = "child_id")
    Child child;

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    Person person;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    Role role;

    public PersonRole() {

    }

    public PersonRole(Child child, Person person, Role role) {
        this.child = child;
        this.person = person;
        this.role = role;
    }

    public PersonRolePK getId() {
        return id;
    }

    public void setId(PersonRolePK id) {
        this.id = id;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
