package be.machigan.carexperience.repository;

import be.machigan.carexperience.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
