package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.LivingGroup;
import de.phoenix.wgtest.model.management.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findAllByLivingGroup(LivingGroup livingGroup);
    HashSet<Person> findAllByNameIn(List<String> names);
    Optional<Person> findByName(String name);
    Person getByName(String name);

    @Modifying
    @Query(value="delete from person where id = ?1", nativeQuery = true)
    void deleteById(Long id);
}

