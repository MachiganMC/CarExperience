package be.machigan.carexperience.service;

import be.machigan.carexperience.dto.request.AuthenticationRequest;
import be.machigan.carexperience.entity.User;
import be.machigan.carexperience.exception.UsernameAlreadyTakenException;
import be.machigan.carexperience.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    @Value("${application.default-user.name}")
    private String defaultUserName;
    @Value("${application.default-user.password}")
    private String defaultUserPassword;
    private final UserRepository userRepository;

    public boolean checkPassword(User user, String plainPassword) {
        return this.passwordEncoder.matches(plainPassword, user.getPassword());
    }

    public void createDefaultUserIfNoUser() {
        if (this.userRepository.existsOne()) return;
        this.userRepository.save(User
                .builder()
                .username(this.defaultUserName)
                .password(this.passwordEncoder.encode(this.defaultUserPassword))
                .build()
        );
    }

    public void createNewUser(AuthenticationRequest request) {
        if (this.userRepository.existsByUsername(request.getUsername()))
            throw new UsernameAlreadyTakenException();
        this.userRepository.save(User
                .builder()
                .username(request.getUsername())
                .password(this.passwordEncoder.encode(request.getPassword()))
                .build()
        );
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        return this.userRepository.getByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
