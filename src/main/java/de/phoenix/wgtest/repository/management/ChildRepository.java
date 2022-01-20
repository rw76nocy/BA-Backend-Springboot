package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.Child;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Long> {
}
