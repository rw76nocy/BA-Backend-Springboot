package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.embeddable.PersonRolePK;
import de.phoenix.wgtest.model.management.Child;
import de.phoenix.wgtest.model.management.Person;
import de.phoenix.wgtest.model.management.PersonRole;
import de.phoenix.wgtest.model.management.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRoleRepository extends JpaRepository<PersonRole, PersonRolePK> {

    Optional<PersonRole> findByChildAndPersonAndRole(Child child, Person person, Role role);

    List<PersonRole> findAllByChild(Child child);

    @Modifying
    @Query(value="delete from person_role where child_id = ?1", nativeQuery = true)
    void deleteByChildId(Long id);

    @Modifying
    @Query(value ="delete from person_role where child_id = ?1 and person_id = ?2 and role_id = ?3", nativeQuery = true)
    void deleteByPersonRole(Long childId, Long personId, Long roleId);
}

