package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {
}
