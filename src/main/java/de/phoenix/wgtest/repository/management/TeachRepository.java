package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.DayCare;
import de.phoenix.wgtest.model.management.Teach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeachRepository extends JpaRepository<Teach, Long> {
    Optional<Teach> findByDayCareAndDayCareGroupAndDayCareTeacher(DayCare dayCare, String dayCareGroup, String dayCareTeacher);
}
