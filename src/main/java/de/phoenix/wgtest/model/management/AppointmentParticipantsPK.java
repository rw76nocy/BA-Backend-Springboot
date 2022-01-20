package de.phoenix.wgtest.model.management;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AppointmentParticipantsPK implements Serializable {

    @Column(name = "child_id")
    private Long childId;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "appointment_id")
    private Long appointmentId;

    public AppointmentParticipantsPK() {
    }

    public AppointmentParticipantsPK(Long childId, Long personId, Long appointmentId) {
        this.childId = childId;
        this.personId = personId;
        this.appointmentId = appointmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof AppointmentParticipantsPK))
            return false;

        AppointmentParticipantsPK other = (AppointmentParticipantsPK) o;

        return childId != null &&
                childId.equals(other.getChildId()) &&
                personId != null &&
                personId.equals(other.getPersonId())&&
                appointmentId != null &&
                appointmentId.equals(other.getAppointmentId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }
}
