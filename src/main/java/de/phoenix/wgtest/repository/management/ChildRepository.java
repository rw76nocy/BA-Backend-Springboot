package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.Child;
import de.phoenix.wgtest.model.management.LivingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {
    Optional<Child> findByFirstName(String firstName);
    List<Child> findByLivingGroup(LivingGroup livingGroup);
}
