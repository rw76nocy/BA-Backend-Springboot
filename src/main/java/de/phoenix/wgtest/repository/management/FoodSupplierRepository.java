package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.FoodSupplier;
import de.phoenix.wgtest.model.management.HealthInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodSupplierRepository extends JpaRepository<FoodSupplier, Long> {
    Optional<FoodSupplier> findByName(String name);
    Boolean existsByName(String name);
}