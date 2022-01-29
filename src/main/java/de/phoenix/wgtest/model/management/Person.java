package de.phoenix.wgtest.model.management;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table( name = "person")
public class Person {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
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

    @OneToOne( fetch = FetchType.EAGER, optional = true, cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JoinColumn( name = "address_id", nullable = true)
    private Address address;

    @ManyToOne( fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "living_group_id", nullable = true)
    private LivingGroup livingGroup;

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    private List<AppointmentParticipants> appointmentParticipants = new ArrayList<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    private List<PersonRole> personRoles = new ArrayList<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    private List<UserPerson> userPerson = new ArrayList<>();

    public Person() {

    }

    public Person(String gender, String name, String phone, String fax, String email, Date birthday, Address address,
                  LivingGroup livingGroup, List<AppointmentParticipants> appointmentParticipants,
                  List<PersonRole> personRoles) {
        this.gender = gender;
        this.name = name;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
        this.birthday = birthday;
        this.address = address;
        this.livingGroup = livingGroup;
        this.appointmentParticipants = appointmentParticipants;
        this.personRoles = personRoles;
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

    public List<AppointmentParticipants> getAppointmentParticipants() {
        return appointmentParticipants;
    }

    public void setAppointmentParticipants(List<AppointmentParticipants> appointmentParticipants) {
        this.appointmentParticipants = appointmentParticipants;
    }

    public List<PersonRole> getPersonRoles() {
        return personRoles;
    }

    public void setPersonRoles(List<PersonRole> personRoles) {
        this.personRoles = personRoles;
    }

    public List<UserPerson> getUserPerson() {
        return userPerson;
    }

    public void setUserPerson(List<UserPerson> userPerson) {
        this.userPerson = userPerson;
    }
}
