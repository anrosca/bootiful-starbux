package com.endava.starbux;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/drinks")
public class DrinkController {
    private final DrinkService drinkService;
    private final DrinkResourceAssembler drinkResourceAssembler;
    private final PagedResourcesAssembler pagedResourcesAssembler;

    @GetMapping
    public CollectionModel<Drink> findAll(@PageableDefault Pageable pageable) {
        Page<Drink> all = drinkService.findAll(pageable);
        return pagedResourcesAssembler.toModel(all, drinkResourceAssembler);
    }

    @GetMapping("{id}")
    public EntityModel<Drink> findById(@PathVariable long id) {
        Drink drink = drinkService.findById(id);
        return drinkResourceAssembler.toModel(drink);
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
