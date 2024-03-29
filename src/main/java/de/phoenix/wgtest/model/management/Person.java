package de.phoenix.wgtest.model.management;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.phoenix.wgtest.model.security.User;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table( name = "person")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Person implements ReferenceObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String gender;

    @NotBlank
    @Size(max = 200)
    private String name;

    @Size(max = 100)
    private String phone;

    @Size(max = 100)
    private String fax;

    @Size(max = 100)
    private String email;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    @ManyToOne( fetch = FetchType.EAGER, optional = true)
    @JoinColumn( name = "address_id", nullable = true)
    private Address address;

    @ManyToOne( fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "living_group_id", nullable = true)
    private LivingGroup livingGroup;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private List<AppointmentPersonParticipant> appointmentPersonParticipants = new ArrayList<>();

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private List<PersonRole> personRoles = new ArrayList<>();

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;

    public Person() {

    }

    public Person(String name, String phone, String fax, String email, Date birthday, Address address) {
        this.name = name;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
        this.birthday = birthday;
        this.address = address;
    }

    public Person(String gender, String name, String phone, String fax, String email, Date birthday, Address address,
                  LivingGroup livingGroup, List<AppointmentPersonParticipant> appointmentPersonParticipants,
                  User user) {
        this(name, phone, fax, email, birthday, address);
        this.gender = gender;
        this.livingGroup = livingGroup;
        this.appointmentPersonParticipants = appointmentPersonParticipants;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LivingGroup getLivingGroup() {
        return livingGroup;
    }

    public void setLivingGroup(LivingGroup livingGroup) {
        this.livingGroup = livingGroup;
    }

    public List<AppointmentPersonParticipant> getAppointmentPersonParticipants() {
        return appointmentPersonParticipants;
    }

    public void setAppointmentPersonParticipants(List<AppointmentPersonParticipant> appointmentPersonParticipants) {
        this.appointmentPersonParticipants = appointmentPersonParticipants;
    }

    public List<PersonRole> getPersonRoles() {
        return personRoles;
    }

    public void setPersonRoles(List<PersonRole> personRoles) {
        this.personRoles = personRoles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void removePersonParticipant(AppointmentPersonParticipant app) {
        appointmentPersonParticipants.remove(app);
    }
}
