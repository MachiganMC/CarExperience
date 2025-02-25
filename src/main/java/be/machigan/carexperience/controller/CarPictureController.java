package be.machigan.carexperience.controller;

import be.machigan.carexperience.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
public class CarPictureController {
    private final CarService carService;

    @GetMapping("/cars/{id}/picture")
    @ResponseStatus(HttpStatus.OK)
    public Resource getPicture(@PathVariable Long id) throws MalformedURLException, FileNotFoundException {
        return this.carService.getPicture(id);
    }
}
