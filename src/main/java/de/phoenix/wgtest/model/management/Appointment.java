package de.phoenix.wgtest.model.management;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
public class Appointment {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 1000)
    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @NotBlank
    @Size(max = 200)
    private String location;

    @ManyToOne( fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "appointment_type_id", nullable = false)
    private AppointmentType appointmentType;

    @NotBlank
    private String hasInterval;

    // interval is reserved in mysql -.-
    @Enumerated(EnumType.STRING)
    private EInterval intervall;

    @Temporal(TemporalType.TIMESTAMP)
    private Date intervalEnd;

    @ManyToOne( fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "living_group_id", nullable = false)
    @JsonIgnore
    private LivingGroup livingGroup;

    @OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private List<AppointmentPersonParticipant> appointmentPersonParticipants = new ArrayList<>();

    @OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private List<AppointmentChildParticipant> appointmentChildParticipants = new ArrayList<>();

    public Appointment() {

    }

    public Appointment(String title, Date startDate, Date endDate, String location, AppointmentType appointmentType,
                       String hasInterval, EInterval intervall, Date intervalEnd, LivingGroup livingGroup,
                       List<AppointmentPersonParticipant> appointmentPersonParticipants,
                       List<AppointmentChildParticipant> appointmentChildParticipants) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.appointmentType = appointmentType;
        this.hasInterval = hasInterval;
        this.intervall = intervall;
        this.intervalEnd = intervalEnd;
        this.livingGroup = livingGroup;
        this.appointmentPersonParticipants = appointmentPersonParticipants;
        this.appointmentChildParticipants = appointmentChildParticipants;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String hasInterval() {
        return hasInterval;
    }

    public void setHasInterval(String hasInterval) {
        this.hasInterval = hasInterval;
    }

    public EInterval getIntervall() {
        return intervall;
    }

    public void setIntervall(EInterval intervall) {
        this.intervall = intervall;
    }

    public Date getIntervalEnd() {
        return intervalEnd;
    }

    public void setIntervalEnd(Date intervalEnd) {
        this.intervalEnd = intervalEnd;
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

    public List<AppointmentChildParticipant> getAppointmentChildParticipants() {
        return appointmentChildParticipants;
    }

    public void setAppointmentChildParticipants(List<AppointmentChildParticipant> appointmentChildParticipants) {
        this.appointmentChildParticipants = appointmentChildParticipants;
    }

    public void removePersonParticipant(AppointmentPersonParticipant app) {
        appointmentPersonParticipants.remove(app);
    }

    public void removeChildParticipant(AppointmentChildParticipant acp) {
        appointmentChildParticipants.remove(acp);
    }
}

