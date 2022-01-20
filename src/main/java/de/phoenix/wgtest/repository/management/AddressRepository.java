package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
