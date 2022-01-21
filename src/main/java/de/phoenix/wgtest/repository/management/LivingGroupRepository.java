package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.LivingGroup;
import de.phoenix.wgtest.model.management.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivingGroupRepository extends JpaRepository<LivingGroup, Long> {
    Optional<LivingGroup> findByName(String name);
}
