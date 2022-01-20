package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.Person;
import de.phoenix.wgtest.model.management.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}

