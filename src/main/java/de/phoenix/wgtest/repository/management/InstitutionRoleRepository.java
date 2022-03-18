package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.embeddable.InstitutionRolePK;
import de.phoenix.wgtest.model.management.InstitutionRole;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface InstitutionRoleRepository extends JpaRepository<InstitutionRole, InstitutionRolePK> {
}
