package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.embeddable.InstitutionRolePK;
import de.phoenix.wgtest.model.management.*;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public interface InstitutionRoleRepository extends JpaRepository<InstitutionRole, InstitutionRolePK> {

    Optional<InstitutionRole> findByChildAndInstitutionAndRole(Child child, Institution institution, Role role);

    List<InstitutionRole> findAllByChild(Child child);

    @Modifying
    @Query(value="delete from institution_role where child_id = ?1", nativeQuery = true)
    void deleteByChildId(Long id);

    @Modifying
    @Query(value ="delete from institution_role where child_id = ?1 and institution_id = ?2 and role_id = ?3", nativeQuery = true)
    void deleteByInstitutionRole(Long childId, Long institutionId, Long roleId);
}
