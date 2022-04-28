package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.FoodSupplier;
import de.phoenix.wgtest.model.management.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
    Optional<Supply> findByFoodSupplierAndCustomerNumberAndPin(FoodSupplier foodSupplier, String customerNumber, String pin);
}
