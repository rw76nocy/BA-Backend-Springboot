package de.phoenix.wgtest.model.management;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.phoenix.wgtest.model.embeddable.AppointmentChildParticipantPK;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table( name = "appointment_child_participant")
public class AppointmentChildParticipant {

    @EmbeddedId
    @JsonIgnore
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    AppointmentChildParticipantPK id = new AppointmentChildParticipantPK();

    @ManyToOne
    @MapsId("childId")
    @JoinColumn(name = "child_id")
    Child child;

    @ManyToOne
    @MapsId("appointmentId")
    @JoinColumn(name = "appointment_id")
    @JsonIgnore
    Appointment appointment;

    public AppointmentChildParticipant() {

    }

    public AppointmentChildParticipant(Child child, Appointment appointment) {
        this.child = child;
        this.appointment = appointment;
    }

    public AppointmentChildParticipantPK getId() {
        return id;
    }

    public void setId(AppointmentChildParticipantPK id) {
        this.id = id;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
