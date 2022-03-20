package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.Asd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AsdRepository extends JpaRepository<Asd, Long> {
    Optional<Asd> findByName(String name);
}

