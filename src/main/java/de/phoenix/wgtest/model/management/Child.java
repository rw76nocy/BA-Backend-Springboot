package de.phoenix.wgtest.model.management;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity( name = "child")
@Table( name = "CHILD")
public class Child {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EGender gender;

    @Lob
    @Column(columnDefinition="BLOB")
    private byte[] image;

    @NotBlank
    @Size( max = 100)
    private String firstName;

    @NotBlank
    @Size( max = 100)
    private String lastName;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Temporal(TemporalType.DATE)
    private Date entranceDate;

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @NotBlank
    @Size( max = 1000)
    private String reason;

    @Size( max = 1000)
    private String care;

    @Size( max = 1000)
    private String visit;

    @Size( max = 1000)
    private String diseases;

    @OneToMany( mappedBy = "child", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Record> records = new HashSet<>();

    @ManyToOne( fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "living_group_id", nullable = true)
    private LivingGroup livingGroup;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true, orphanRemoval = true)
    @JoinColumn(name = "teach_id", nullable = true)
    private Teach teach;

    /*@OneToMany(mappedBy = "child")
    private Set<Teach> teaches = new HashSet<>();*/

    @OneToMany(mappedBy = "child")
    private Set<Supply> supplies = new HashSet<>();

    @OneToMany(mappedBy = "child")
    private Set<Insured> insureds = new HashSet<>();

    @OneToMany(mappedBy = "child")
    private Set<AppointmentParticipants> appointmentParticipants = new HashSet<>();

    @OneToMany(mappedBy = "child")
    private Set<PersonRole> personRoles = new HashSet<>();

    @OneToMany(mappedBy = "child")
    private Set<InstitutionRole> institutionRoles = new HashSet<>();

    public Child() {

    }

    public Child(EGender gender, byte[] image, String firstName, String lastName, Date birthday, Date entranceDate,
                 Date releaseDate, String reason, String care, String visit, String diseases, LivingGroup livingGroup,
                 Set<Record> records, Teach teach, Set<Supply> supplies, Set<Insured> insureds,
                 Set<AppointmentParticipants> appointmentParticipants, Set<PersonRole> personRoles,
                 Set<InstitutionRole> institutionRoles) {
        this.gender = gender;
        this.image = image;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.entranceDate = entranceDate;
        this.releaseDate = releaseDate;
        this.reason = reason;
        this.care = care;
        this.visit = visit;
        this.diseases = diseases;
        this.livingGroup = livingGroup;
        this.records = records;
        this.teach = teach;
        //this.teaches = teaches;
        this.supplies = supplies;
        this.insureds = insureds;
        this.appointmentParticipants = appointmentParticipants;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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

    public Set<Record> getRecords() {
        return records;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
    }

    /*public Set<Teach> getTeaches() {
        return teaches;
    }

    public void setTeaches(Set<Teach> teaches) {
        this.teaches = teaches;
    }*/

    public Teach getTeach() {
        return teach;
    }

    public void setTeach(Teach teach) {
        this.teach = teach;
    }

    public Set<Supply> getSupplies() {
        return supplies;
    }

    public void setSupplies(Set<Supply> supplies) {
        this.supplies = supplies;
    }

    public Set<Insured> getInsureds() {
        return insureds;
    }

    public void setInsureds(Set<Insured> insureds) {
        this.insureds = insureds;
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

    public Set<InstitutionRole> getInstitutionRoles() {
        return institutionRoles;
    }

    public void setInstitutionRoles(Set<InstitutionRole> institutionRoles) {
        this.institutionRoles = institutionRoles;
    }
}
