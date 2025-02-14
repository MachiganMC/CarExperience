package be.machigan.carexperience.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Car extends LivingEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int horsePower;

    @Column(nullable = false)
    private int enginePower;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
