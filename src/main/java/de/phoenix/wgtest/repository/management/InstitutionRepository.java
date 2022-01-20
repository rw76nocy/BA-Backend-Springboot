package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.Institution;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
}
