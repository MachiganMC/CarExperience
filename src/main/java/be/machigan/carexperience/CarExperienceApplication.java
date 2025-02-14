package be.machigan.carexperience;

import be.machigan.carexperience.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CarExperienceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarExperienceApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(UserService userService) {
        return args -> userService.createDefaultUserIfNoUser();
    }
}
