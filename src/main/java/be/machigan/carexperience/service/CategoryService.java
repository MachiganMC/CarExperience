package be.machigan.carexperience.service;

import be.machigan.carexperience.entity.Category;
import be.machigan.carexperience.exception.EntityNotFoundException;
import be.machigan.carexperience.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return this.categoryRepository.findAll();
    }

    public Category findById(long id) {
        return this.categoryRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category " + id + " doesn't exist"));
    }
}
