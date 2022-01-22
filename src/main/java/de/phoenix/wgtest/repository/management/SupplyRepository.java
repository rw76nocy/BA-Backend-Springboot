package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
}
