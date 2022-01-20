package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.DayCare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DayCareRepository extends JpaRepository<DayCare, Long> {
    Optional<DayCare> findByName(String name);
    Boolean existsByName(String name);
}
