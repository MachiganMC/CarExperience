package be.machigan.carexperience.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category extends LivingEntity {
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Car> cars = new HashSet<>();
}
