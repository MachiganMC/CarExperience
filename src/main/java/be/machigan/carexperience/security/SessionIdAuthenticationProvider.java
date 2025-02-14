package be.machigan.carexperience.security;

import be.machigan.carexperience.entity.User;
import be.machigan.carexperience.repository.UserRepository;
import be.machigan.carexperience.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SessionIdAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = this.userRepository
                .getByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Username : " + authentication.getName() + " not found"));
        String password = authentication.getCredentials().toString();
        if (!userService.checkPassword(user, password))
            throw new BadCredentialsException("Invalid password");
        return UsernamePasswordAuthenticationToken.authenticated(
                user,
                password,
                List.of()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
