package de.phoenix.wgtest.model.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AppointmentPersonParticipantPK implements Serializable {

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "appointment_id")
    private Long appointmentId;

    public AppointmentPersonParticipantPK() {
    }

    public AppointmentPersonParticipantPK(Long personId, Long appointmentId) {
        this.personId = personId;
        this.appointmentId = appointmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof AppointmentPersonParticipantPK))
            return false;

        AppointmentPersonParticipantPK other = (AppointmentPersonParticipantPK) o;

        return personId != null &&
                personId.equals(other.getPersonId())&&
                appointmentId != null &&
                appointmentId.equals(other.getAppointmentId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
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
