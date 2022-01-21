package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.Insured;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuredRepository extends JpaRepository<Insured, Long> {
}
