package be.machigan.carexperience.handler;

import be.machigan.carexperience.controller.CarController;
import be.machigan.carexperience.controller.CategoryController;
import be.machigan.carexperience.exception.EntityNotFoundException;
import be.machigan.carexperience.exception.InvalidFileTypeException;
import be.machigan.carexperience.exception.NameAlreadyInUseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {CategoryController.class, CarController.class})
public class EntityControllerHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException() {
        return new ResponseEntity<>("The entity hasn't been found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NameAlreadyInUseException.class)
    public ResponseEntity<String> handleNameAlreadyUsedException() {
        return new ResponseEntity<>("This name is already in use", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidFileTypeException.class)
    public ResponseEntity<String> handleInvalidFileTypeException(InvalidFileTypeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
}
