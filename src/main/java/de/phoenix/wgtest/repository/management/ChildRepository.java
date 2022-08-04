package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.Child;
import de.phoenix.wgtest.model.management.LivingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {
    Optional<Child> findByFirstName(String firstName);
    Optional<Child> findByFullName(String fullName);
    List<Child> findByLivingGroup(LivingGroup livingGroup);
    HashSet<Child> findAllByFullNameIn(List<String> names);

    @Modifying
    @Query(value="delete from child where id = ?1", nativeQuery = true)
    void deleteById(Long id);
}
