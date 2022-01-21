package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.Supply;
import de.phoenix.wgtest.model.management.SupplyPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
}
