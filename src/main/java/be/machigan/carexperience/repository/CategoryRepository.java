package be.machigan.carexperience.repository;

import be.machigan.carexperience.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    @Query(value = """
        SELECT * FROM category;
    """, nativeQuery = true)
    List<Category> findAllAndDeleted();
}
