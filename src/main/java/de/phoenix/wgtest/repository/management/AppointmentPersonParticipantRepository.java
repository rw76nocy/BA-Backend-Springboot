package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.embeddable.AppointmentPersonParticipantPK;
import de.phoenix.wgtest.model.management.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentPersonParticipantRepository extends JpaRepository<AppointmentPersonParticipant, AppointmentPersonParticipantPK> {

    Optional<AppointmentPersonParticipant> findByPersonAndAppointment(Person person, Appointment appointment);

    List<AppointmentPersonParticipant> findAllByPerson(Person person);

    @Modifying
    @Query(value="delete from appointment_person_participant where person_id = ?1", nativeQuery = true)
    void deleteByPersonId(Long id);

    @Modifying
    @Query(value="delete from appointment_person_participant where appointment_id = ?1", nativeQuery = true)
    void deleteByAppointmentId(Long id);

    @Modifying
    @Query(value ="delete from appointment_person_participant where person_id = ?1 and appointment_id = ?2", nativeQuery = true)
    void deleteByAppointmentPersonParticipant(Long personId, Long appointmentId);
}

