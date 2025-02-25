package be.machigan.carexperience.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class CarRequest {
    @NotEmpty(message = "The name of the car cannot be empty")
    protected String name;

    @Positive(message = "The horse power must be positive")
    protected int horsePower;

    @Positive(message = "The engine power must be positive")
    protected int enginePower;

    protected int category;
}
