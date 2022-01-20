package de.phoenix.wgtest.model.management;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "Appointment")
public class Appointment {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @NotBlank
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @NotBlank
    @Size(max = 200)
    private String location;

    @OneToMany(mappedBy = "appointment")
    private Set<AppointmentParticipants> appointmentParticipants = new HashSet<>();

    public Appointment() {

    }

    public Appointment(Date startDate, Date endDate, String description, String location,
                       Set<AppointmentParticipants> appointmentParticipants) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.location = location;
        this.appointmentParticipants = appointmentParticipants;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<AppointmentParticipants> getAppointmentParticipants() {
        return appointmentParticipants;
    }

    public void setAppointmentParticipants(Set<AppointmentParticipants> appointmentParticipants) {
        this.appointmentParticipants = appointmentParticipants;
    }
}
