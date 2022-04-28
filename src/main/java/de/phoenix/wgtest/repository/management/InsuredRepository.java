package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.HealthInsurance;
import de.phoenix.wgtest.model.management.Insured;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InsuredRepository extends JpaRepository<Insured, Long> {
    Optional<Insured> findByHealthInsuranceAndHolderAndCustomerNumber(HealthInsurance healthInsurance, String holder, String customerNumber);
}
