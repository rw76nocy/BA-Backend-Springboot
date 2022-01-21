package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
