package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.DayCare;
import de.phoenix.wgtest.model.management.HealthInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthInsuranceRepository extends JpaRepository<HealthInsurance, Long> {
    Optional<HealthInsurance> findByName(String name);
    Boolean existsByName(String name);
}
