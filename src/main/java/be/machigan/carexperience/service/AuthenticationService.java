package be.machigan.carexperience.service;

import be.machigan.carexperience.dto.request.AuthenticationRequest;
import be.machigan.carexperience.exception.TooManyAttemptsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    private final LoginBruteForcePreventService loginBruteForcePreventService;

    public void authenticate(
            AuthenticationRequest authenticationRequest,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword()
        );
        String remoteIpAddress = request.getRemoteAddr();
        this.loginBruteForcePreventService.addAttemptFor(remoteIpAddress);
        SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(this.authenticationManager.authenticate(token));
        this.securityContextHolderStrategy.setContext(context);
        this.securityContextRepository.saveContext(context, request, response);
        this.loginBruteForcePreventService.clearAttemptsFor(remoteIpAddress);
    }
}
