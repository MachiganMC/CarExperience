package be.machigan.carexperience.controller;

import be.machigan.carexperience.service.CarService;
import be.machigan.carexperience.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final CategoryService categoryService;

    @GetMapping("/")
    public String homePage(Model model, @RequestParam(required = false) Long categoryId) {
        if (categoryId == null) {
            model.addAttribute("categories", this.categoryService.getAll());
            return "home/home";
        }
        model.addAttribute("category", this.categoryService.findById(categoryId));
        return "home/category";
    }
}
