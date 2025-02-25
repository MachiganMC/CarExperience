package be.machigan.carexperience.controller;

import be.machigan.carexperience.dto.template.AdminLink;
import be.machigan.carexperience.service.CarService;
import be.machigan.carexperience.service.CategoryService;
import be.machigan.carexperience.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final CategoryService categoryService;
    private final UserService userService;
    private final CarService carService;

    @ModelAttribute(name = "links")
    public List<AdminLink> addLinks() {
        return AdminLink.LINKS;
    }

    @ModelAttribute("maxFileSize")
    public DataSize addMaxFileSize() {
        return this.carService.getMaxCarFileSize();
    }

    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("categories", this.categoryService.getAll());
        return "admin/home";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", this.userService.getAll());
        return "admin/users";
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categories", this.categoryService.getAll());
        return "admin/categories";
    }

    @GetMapping("/cars")
    public String cars(Model model) {
        model.addAttribute("categories", this.categoryService.getAllAndDeleted());
        model.addAttribute("notDeletedCategories", this.categoryService.getAll());
        return "admin/cars";
    }
}
