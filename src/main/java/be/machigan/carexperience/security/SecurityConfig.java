package be.machigan.carexperience.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final SessionIdAuthenticationProvider provider;
    @Value("${application.session.max}")
    private int maxSessions;
    @Value("${server.servlet.session.cookie.name}")
    private String cookieName;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> {
                    session.maximumSessions(this.maxSessions);
                    session.sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession);
                    session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
                })
                .authenticationProvider(this.provider)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/admin/login").anonymous()
                        .requestMatchers("/admin/**").authenticated()
                        .anyRequest().permitAll()
                )
                .securityContext(context -> context.securityContextRepository(new HttpSessionSecurityContextRepository()))
                .logout(logout -> {
                    logout.logoutUrl("/admin/logout");
                    logout.logoutSuccessUrl("/");
                    logout.deleteCookies(this.cookieName);
                })
                .build()
                ;
    }
}
