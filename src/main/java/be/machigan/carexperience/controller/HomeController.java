package be.machigan.carexperience.controller;

import be.machigan.carexperience.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final CategoryService categoryService;
    @Value("${application.book.phone-number}")
    private String phoneNumber;
    @Value("${application.book.message}")
    private String bookMessage;

    @ModelAttribute("phoneNumber")
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    @ModelAttribute("bookMessage")
    public String getBookMessage() {
        return this.bookMessage;
    }

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
