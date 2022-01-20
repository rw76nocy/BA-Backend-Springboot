package de.phoenix.wgtest.model.management;

import de.phoenix.wgtest.model.security.User;

import javax.persistence.*;

@Entity
public class UserPerson {

    @EmbeddedId
    UserPersonPK id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    Person person;

    public UserPerson() {
    }

    public UserPerson(User user, Person person) {
        this.user = user;
        this.person = person;
    }

    public UserPersonPK getId() {
        return id;
    }

    public void setId(UserPersonPK id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
