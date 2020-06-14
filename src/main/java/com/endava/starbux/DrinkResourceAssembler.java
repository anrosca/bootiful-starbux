package com.endava.starbux;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DrinkResourceAssembler implements RepresentationModelAssembler<Drink, EntityModel<Drink>> {
    @Override
    public EntityModel<Drink> toModel(Drink entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(DrinkController.class).findAll(null)).withRel("all_drinks"),
                linkTo(methodOn(DrinkController.class).findById(entity.getId())).withSelfRel());
    }
}
