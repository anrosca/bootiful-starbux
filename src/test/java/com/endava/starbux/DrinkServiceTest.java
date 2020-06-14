package com.endava.starbux;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DrinkServiceTest {

    private DrinkRepository drinkRepository = mock(DrinkRepository.class);
    private DrinkService drinkService;

    @BeforeEach
    void setUp() {
//        drinkService = new DrinkService(drinkRepository);
    }

    @Test
    public void whenDrinkNotFound_ItThrowsDrinkNotFoundException() {
        when(drinkRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DrinkNotFoundException.class, () -> drinkService.findById(1L));
    }
}