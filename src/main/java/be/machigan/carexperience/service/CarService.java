package be.machigan.carexperience.service;

import be.machigan.carexperience.dto.request.CarRequest;
import be.machigan.carexperience.entity.Car;
import be.machigan.carexperience.exception.EntityNotFoundException;
import be.machigan.carexperience.repository.CarRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.InvalidPropertiesFormatException;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CategoryService categoryService;
    @Value("${spring.servlet.multipart.max-file-size}")
    @Getter
    private DataSize maxCarFileSize;
    @Value("${application.car-picture.folder}")
    @Getter
    private String carPicturesFolder;
    private final FileService fileService;

    public Car findById(Long id) {
        return this.carRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The car with id " + id + " hasn't been found"));
    }

    public void setCarFromRequest(Car car, CarRequest request) {
        car.setCategory(this.categoryService.findById(request.getCategory()));
        car.setName(request.getName());
        car.setEnginePower(request.getEnginePower());
        car.setHorsePower(request.getHorsePower());
    }

    public void edit(Long id, CarRequest request) {
        Car car = this.findById(id);
        this.setCarFromRequest(car, request);
        this.carRepository.save(car);
    }

    public void setCarPicture(Car car, MultipartFile picture) throws IOException {
        if (!this.fileService.isAnImage(picture))
            throw new InvalidPropertiesFormatException("The file isn't an image");
        this.fileService.saveTo(this.carPicturesFolder + "/", String.valueOf(car.getId()), picture);
    }

    public Resource getPicture(Long id) throws MalformedURLException, FileNotFoundException {
        if (!this.carRepository.existsById(id))
            throw new EntityNotFoundException("The car with id " + id + " doesn't exist");
        return this.fileService.getResource(this.carPicturesFolder, String.valueOf(id));
    }

    public void createCar(CarRequest request, MultipartFile picture) throws IOException {
        Car car = new Car();
        this.setCarFromRequest(car, request);
        car = this.carRepository.save(car);
        this.setCarPicture(car, picture);
    }

    public void remove(Long id) {
        this.carRepository.delete(this.findById(id));
    }
}
