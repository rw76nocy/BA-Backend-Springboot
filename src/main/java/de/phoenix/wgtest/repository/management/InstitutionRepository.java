package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.FoodSupplier;
import de.phoenix.wgtest.model.management.Institution;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    Optional<Institution> findByName(String name);
    Boolean existsByName(String name);
}
