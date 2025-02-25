package be.machigan.carexperience.repository;

import be.machigan.carexperience.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
