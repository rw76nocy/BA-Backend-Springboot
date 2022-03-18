package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.embeddable.PersonRolePK;
import de.phoenix.wgtest.model.management.PersonRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRoleRepository extends JpaRepository<PersonRole, PersonRolePK> {
}

