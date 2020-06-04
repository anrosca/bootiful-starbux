package com.endava.starbux;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Drink implements Serializable {

    @Id
    @SequenceGenerator(name = "drink_sequence", sequenceName = "drink_sequence", allocationSize = 1)
    @GeneratedValue(generator = "drink_sequence")
    private Long id;

    private String name;

    @Column(name = "drink_type")
    @Enumerated(EnumType.STRING)
    private DrinkType drinkType;
}
