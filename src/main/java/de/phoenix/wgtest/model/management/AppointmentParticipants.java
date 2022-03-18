package de.phoenix.wgtest.model.management;

import de.phoenix.wgtest.model.embeddable.AppointmentParticipantsPK;

import javax.persistence.*;

@Entity
public class AppointmentParticipants {

    @EmbeddedId
    AppointmentParticipantsPK id = new AppointmentParticipantsPK();

    @ManyToOne
    @MapsId("childId")
    @JoinColumn(name = "child_id")
    Child child;

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    Person person;

    @ManyToOne
    @MapsId("appointmentId")
    @JoinColumn(name = "appointment_id")
    Appointment appointment;

    public AppointmentParticipants() {

    }

    public AppointmentParticipants(Child child, Person person, Appointment appointment) {
        this.child = child;
        this.person = person;
        this.appointment = appointment;
    }

    public AppointmentParticipantsPK getId() {
        return id;
    }

    public void setId(AppointmentParticipantsPK id) {
        this.id = id;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
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
