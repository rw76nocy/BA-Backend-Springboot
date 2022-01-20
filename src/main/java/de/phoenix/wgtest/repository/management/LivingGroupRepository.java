package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.LivingGroup;
import de.phoenix.wgtest.model.management.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivingGroupRepository extends JpaRepository<LivingGroup, Long> {
}
