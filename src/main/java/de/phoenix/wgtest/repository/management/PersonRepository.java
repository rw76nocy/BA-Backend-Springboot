package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.Person;
import de.phoenix.wgtest.model.management.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByName(String name);
}

