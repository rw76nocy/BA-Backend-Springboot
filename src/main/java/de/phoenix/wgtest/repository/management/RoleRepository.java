package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.ERole;
import de.phoenix.wgtest.model.management.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByTypeAndSpecification(ERole type, String specification);
}
