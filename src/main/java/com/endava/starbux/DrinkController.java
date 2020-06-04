package com.endava.starbux;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/drinks")
public class DrinkController {
    private final DrinkService drinkService;

    @GetMapping
    public List<Drink> findAll() {
        return drinkService.findAll();
    }

    @GetMapping("{id}")
    public Drink findById(@PathVariable long id) {
        return drinkService.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid DrinkDto drink) {
        Drink createdDrink = drinkService.create(drink.toDrink());
        URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}")
                .build(createdDrink.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", uri.toString())
                .build();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) {
        drinkService.deleteById(id);
    }
}
