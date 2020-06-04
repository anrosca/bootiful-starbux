package com.endava.starbux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class DrinkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DrinkService drinkService;

    @Test
    public void whenFindingADrinkWithUnknownId_ItReturnNotFound() throws Exception {
        when(drinkService.findById(1L)).thenThrow(new DrinkNotFoundException("Drink with id 1 not found"));

        mockMvc.perform(get("/drinks/1"))
                .andExpect(status().is(404))
                .andExpect(jsonPath("message").value("Drink with id 1 not found"))
                .andExpect(jsonPath("error").value("Drink not found"));
    }
}