package de.phoenix.wgtest.model.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AppointmentChildParticipantPK implements Serializable {

    @Column(name = "child_id")
    private Long childId;

    @Column(name = "appointment_id")
    private Long appointmentId;

    public AppointmentChildParticipantPK() {
    }

    public AppointmentChildParticipantPK(Long childId, Long appointmentId) {
        this.childId = childId;
        this.appointmentId = appointmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof AppointmentChildParticipantPK))
            return false;

        AppointmentChildParticipantPK other = (AppointmentChildParticipantPK) o;

        return childId != null &&
                childId.equals(other.getChildId()) &&
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

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }
}
