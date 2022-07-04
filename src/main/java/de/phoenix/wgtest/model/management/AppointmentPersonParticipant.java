package de.phoenix.wgtest.model.management;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.phoenix.wgtest.model.embeddable.AppointmentPersonParticipantPK;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table( name = "appointment_person_participant")
public class AppointmentPersonParticipant {

    @EmbeddedId
    @JsonIgnore
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    AppointmentPersonParticipantPK id = new AppointmentPersonParticipantPK();

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    Person person;

    @ManyToOne
    @MapsId("appointmentId")
    @JoinColumn(name = "appointment_id")
    @JsonIgnore
    Appointment appointment;

    public AppointmentPersonParticipant() {

    }

    public AppointmentPersonParticipant(Person person, Appointment appointment) {
        this.person = person;
        this.appointment = appointment;
    }

    public AppointmentPersonParticipantPK getId() {
        return id;
    }

    public void setId(AppointmentPersonParticipantPK id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
