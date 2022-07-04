package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.embeddable.AppointmentChildParticipantPK;
import de.phoenix.wgtest.model.management.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentChildParticipantRepository extends JpaRepository<AppointmentChildParticipant, AppointmentChildParticipantPK> {

    Optional<AppointmentChildParticipant> findByChildAndAppointment(Child child, Appointment appointment);

    List<AppointmentChildParticipant> findAllByChild(Child child);

    @Modifying
    @Query(value="delete from appointment_child_participant where child_id = ?1", nativeQuery = true)
    void deleteByChildId(Long id);

    @Modifying
    @Query(value="delete from appointment_child_participant where appointment_id = ?1", nativeQuery = true)
    void deleteByAppointmentId(Long id);

    @Modifying
    @Query(value ="delete from appointment_child_participant where child_id = ?1 and appointment_id = ?2", nativeQuery = true)
    void deleteByAppointmentChildParticipant(Long childId, Long appointmentId);
}

