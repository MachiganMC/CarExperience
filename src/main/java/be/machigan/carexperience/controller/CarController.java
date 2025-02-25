package be.machigan.carexperience.controller;

import be.machigan.carexperience.dto.request.CarRequest;
import be.machigan.carexperience.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/admin/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void edit(@PathVariable Long id, @RequestBody @Valid CarRequest request) {
        this.carService.edit(id, request);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editPicture(@PathVariable Long id, @RequestParam MultipartFile picture) throws IOException {
        this.carService.setCarPicture(this.carService.findById(id), picture);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestPart("car") CarRequest request, @RequestParam MultipartFile picture) throws IOException {
        this.carService.createCar(request, picture);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable Long id) {
        this.carService.remove(id);
    }
}
