package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.AppointmentType;
import de.phoenix.wgtest.model.management.LivingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentTypeRepository extends JpaRepository<AppointmentType, Long> {
    List<AppointmentType> findAllByLivingGroup(LivingGroup livingGroup);
    Optional<AppointmentType> findByLivingGroupAndName(LivingGroup livingGroup, String name);

    @Modifying
    @Query(value="delete from appointment_type where id = ?1", nativeQuery = true)
    void deleteById(Long id);
}
