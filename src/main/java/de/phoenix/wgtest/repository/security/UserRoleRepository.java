package de.phoenix.wgtest.repository.security;

import de.phoenix.wgtest.model.security.EUserRole;
import de.phoenix.wgtest.model.security.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByName(EUserRole name);
}
