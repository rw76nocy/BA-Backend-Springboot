package de.phoenix.wgtest.model.management;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "person")
public class Person {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private EGender gender;

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

    @OneToOne( fetch = FetchType.LAZY, optional = false)
    @JoinColumn( name = "address_id", nullable = true)
    private Address address;

    @ManyToOne( fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "living_group_id", nullable = true)
    private LivingGroup livingGroup;

    @OneToMany(mappedBy = "person")
    private Set<AppointmentParticipants> appointmentParticipants = new HashSet<>();

    @OneToMany(mappedBy = "person")
    private Set<PersonRole> personRoles = new HashSet<>();

    @OneToMany(mappedBy = "person")
    private Set<UserPerson> userPerson = new HashSet<>();

    public Person() {

    }

    public Person(EGender gender, String name, String phone, String fax, String email, Date birthday, Address address,
                  LivingGroup livingGroup, Set<AppointmentParticipants> appointmentParticipants,
                  Set<PersonRole> personRoles) {
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

    public EGender getGender() {
        return gender;
    }

    public void setGender(EGender gender) {
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

    public Set<AppointmentParticipants> getAppointmentParticipants() {
        return appointmentParticipants;
    }

    public void setAppointmentParticipants(Set<AppointmentParticipants> appointmentParticipants) {
        this.appointmentParticipants = appointmentParticipants;
    }

    public Set<PersonRole> getPersonRoles() {
        return personRoles;
    }

    public void setPersonRoles(Set<PersonRole> personRoles) {
        this.personRoles = personRoles;
    }
}
