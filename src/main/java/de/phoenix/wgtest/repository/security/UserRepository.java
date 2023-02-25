package de.phoenix.wgtest.repository.security;

import de.phoenix.wgtest.model.management.Person;
import de.phoenix.wgtest.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByPerson(Person person);

    @Modifying
    @Query(value="delete from user where id = ?1", nativeQuery = true)
    void deleteById(Long id);

    @Modifying
    @Query(value="delete from user where person_id = ?1", nativeQuery = true)
    void deleteByPersonId(Long id);

    @Modifying
    @Query(value="delete from user_user_roles where user_id = ?1", nativeQuery = true)
    void deleteUserRolesById(Long id);

}
