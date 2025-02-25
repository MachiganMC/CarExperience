package be.machigan.carexperience.service;

import be.machigan.carexperience.entity.Category;
import be.machigan.carexperience.exception.EntityNotFoundException;
import be.machigan.carexperience.exception.NameAlreadyInUseException;
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

    public List<Category> getAllAndDeleted() {
        return this.categoryRepository.findAllAndDeleted();
    }

    public Category findById(long id) {
        return this.categoryRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category " + id + " doesn't exist"));
    }

    public void rename(Long id, String newName) {
        Category category = this.findById(id);
        if (this.categoryRepository.existsByName(newName))
            throw new NameAlreadyInUseException("The name " + newName + " already exists for a category");
        category.setName(newName);
        this.categoryRepository.save(category);
    }

    public void delete(Long id) {
        this.categoryRepository.delete(this.findById(id));
    }

    public void create(String name) {
        if (this.categoryRepository.existsByName(name))
            throw new NameAlreadyInUseException("The name " + name + " already exists for a category");
        this.categoryRepository.save(Category
                .builder()
                .name(name)
                .build()
        );
    }
}
