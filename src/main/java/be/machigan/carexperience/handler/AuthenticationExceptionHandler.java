package be.machigan.carexperience.handler;

import be.machigan.carexperience.controller.AuthenticationController;
import be.machigan.carexperience.exception.TooManyAttemptsException;
import be.machigan.carexperience.exception.UsernameAlreadyTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = AuthenticationController.class)
public class AuthenticationExceptionHandler {

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity<String> handleLoginException() {
        return new ResponseEntity<>("Username or password incorrect", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public ResponseEntity<String> handleRegisterException() {
        return new ResponseEntity<>("This username is already taken", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TooManyAttemptsException.class)
    public ResponseEntity<String> handleToManyAttemptsException(TooManyAttemptsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.TOO_MANY_REQUESTS);
    }
}
