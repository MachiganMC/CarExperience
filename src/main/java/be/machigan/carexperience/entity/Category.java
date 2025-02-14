package be.machigan.carexperience.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category extends LivingEntity {
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Car> cars = new HashSet<>();
}
