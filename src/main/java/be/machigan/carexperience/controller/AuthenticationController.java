package be.machigan.carexperience.controller;

import be.machigan.carexperience.dto.request.AuthenticationRequest;
import be.machigan.carexperience.entity.User;
import be.machigan.carexperience.service.AuthenticationService;
import be.machigan.carexperience.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody AuthenticationRequest authenticationRequest, HttpServletRequest request, HttpServletResponse response) {
        this.authenticationService.authenticate(authenticationRequest, request, response);

    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAccount(@RequestBody AuthenticationRequest authenticationRequest) {
        this.userService.createNewUser(authenticationRequest);
    }

    private User getUser() {
        return this.userService.getCurrentUser();
    }
}
