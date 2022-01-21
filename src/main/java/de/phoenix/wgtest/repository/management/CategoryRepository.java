package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
