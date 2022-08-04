package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.Appointment;
import de.phoenix.wgtest.model.management.LivingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByLivingGroup(LivingGroup livingGroup);

    /**
     *
     * @param livingGroup the living group which is concerned
     * @param start start date of the new appointment
     * @param end end date of the new appointment
     * @param start1 start date of the new appointment
     * @param end1 end date of the new appointment
     * @param start2 start date of the new appointment
     * @param end2 end date of the new appointment
     * @return All appointments that are between the start and end time (not equal) or whose start is before and whose end is after
     */
    HashSet<Appointment> findAllByLivingGroupAndStartDateAfterAndStartDateBeforeOrEndDateAfterAndEndDateBeforeOrStartDateLessThanEqualAndEndDateGreaterThanEqual(LivingGroup livingGroup, Date start, Date end, Date start1, Date end1, Date start2, Date end2);

    @Modifying
    @Query(value="delete from appointment where id = ?1", nativeQuery = true)
    void deleteById(Long id);
}
