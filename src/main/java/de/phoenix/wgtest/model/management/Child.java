package de.phoenix.wgtest.model.management;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Entity( name = "child")
@Table( name = "CHILD")
public class Child {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EGender gender;

    @NotBlank
    @Size( max = 100)
    private String firstName;

    @NotBlank
    @Size( max = 100)
    private String lastName;

    @NotBlank
    @Size( max = 200)
    private String fullName;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Temporal(TemporalType.DATE)
    private Date entranceDate;

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @Size( max = 1000)
    private String reason;

    @Size( max = 1000)
    private String care;

    @Size( max = 1000)
    private String visit;

    @Size( max = 1000)
    private String diseases;

    @OneToMany( mappedBy = "child", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, orphanRemoval = true)
    private List<Record> records = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "living_group_id")
    private LivingGroup livingGroup;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true, orphanRemoval = true)
    @JoinColumn(name = "teach_id", nullable = true)
    private Teach teach;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true, orphanRemoval = true)
    @JoinColumn(name = "supply_id", nullable = true)
    private Supply supply;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true, orphanRemoval = true)
    @JoinColumn(name = "insured_id", nullable = true)
    private Insured insured;

    @OneToMany(mappedBy = "child", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private List<AppointmentChildParticipant> appointmentChildParticipants = new ArrayList<>();

    @OneToMany(mappedBy = "child", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<PersonRole> personRoles = new ArrayList<>();

    @OneToMany(mappedBy = "child", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<InstitutionRole> institutionRoles = new ArrayList<>();

    public Child() {

    }

    public Child(EGender gender, String firstName, String lastName, String fullName, Date birthday, Date entranceDate,
                 Date releaseDate, String reason, String care, String visit, String diseases, LivingGroup livingGroup) {
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.birthday = birthday;
        this.entranceDate = entranceDate;
        this.releaseDate = releaseDate;
        this.reason = reason;
        this.care = care;
        this.visit = visit;
        this.diseases = diseases;
        this.livingGroup = livingGroup;
    }

    public Child(EGender gender,  String firstName, String lastName, String fullName, Date birthday, Date entranceDate,
                 Date releaseDate, String reason, String care, String visit, String diseases, LivingGroup livingGroup,
                 List<Record> records, Teach teach, Supply supply, Insured insured,
                 List<AppointmentChildParticipant> appointmentChildParticipants, List<PersonRole> personRoles,
                 List<InstitutionRole> institutionRoles) {
        this(gender, firstName, lastName, fullName, birthday, entranceDate, releaseDate, reason, care, visit, diseases, livingGroup);
        this.records = records;
        this.teach = teach;
        this.supply = supply;
        this.insured = insured;
        this.appointmentChildParticipants = appointmentChildParticipants;
        this.personRoles = personRoles;
        this.institutionRoles = institutionRoles;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getEntranceDate() {
        return entranceDate;
    }

    public void setEntranceDate(Date entranceDate) {
        this.entranceDate = entranceDate;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCare() {
        return care;
    }

    public void setCare(String care) {
        this.care = care;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public LivingGroup getLivingGroup() {
        return livingGroup;
    }

    public void setLivingGroup(LivingGroup livingGroup) {
        this.livingGroup = livingGroup;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public Teach getTeach() {
        return teach;
    }

    public void setTeach(Teach teach) {
        this.teach = teach;
    }

    public Supply getSupply() {
        return supply;
    }

    public void setSupply(Supply supply) {
        this.supply = supply;
    }

    public Insured getInsured() {
        return insured;
    }

    public void setInsured(Insured insured) {
        this.insured = insured;
    }

    public List<AppointmentChildParticipant> getAppointmentChildParticipants() {
        return appointmentChildParticipants;
    }

    public void setAppointmentChildParticipants(List<AppointmentChildParticipant> appointmentChildParticipants) {
        this.appointmentChildParticipants = appointmentChildParticipants;
    }

    public List<PersonRole> getPersonRoles() {
        return personRoles;
    }

    public void setPersonRoles(List<PersonRole> personRoles) {
        this.personRoles = personRoles;
    }

    public List<InstitutionRole> getInstitutionRoles() {
        return institutionRoles;
    }

    public void setInstitutionRoles(List<InstitutionRole> institutionRoles) {
        this.institutionRoles = institutionRoles;
    }
}
