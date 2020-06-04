package com.endava.starbux;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrinkDto {
    @NotNull
    private String name;

    @ValidDrinkType
    private String drinkType;

    public Drink toDrink() {
        return Drink.builder()
                .name(name)
                .drinkType(DrinkType.valueOf(drinkType))
                .build();
    }
}
