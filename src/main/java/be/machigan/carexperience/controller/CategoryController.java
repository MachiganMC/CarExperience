package be.machigan.carexperience.controller;

import be.machigan.carexperience.service.CategoryService;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void edit(@PathVariable Long id, @RequestBody @NotEmpty(message = "The new name of the category cannot be empty") String name) {
        this.categoryService.rename(id, name);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        this.categoryService.delete(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody @NotEmpty(message = "The name of a category cannot be empty") String name) {
        this.categoryService.create(name);
    }
}
