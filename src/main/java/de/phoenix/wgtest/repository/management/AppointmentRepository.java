package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.Appointment;
import de.phoenix.wgtest.model.management.LivingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByLivingGroup(LivingGroup livingGroup);

    @Modifying
    @Query(value="delete from appointment where id = ?1", nativeQuery = true)
    void deleteById(Long id);
}
