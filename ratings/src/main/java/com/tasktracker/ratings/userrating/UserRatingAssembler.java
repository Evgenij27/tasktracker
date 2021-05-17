package com.tasktracker.ratings.userrating;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserRatingAssembler implements RepresentationModelAssembler<UserRating, EntityModel<UserRating>> {

    @Override
    public EntityModel<UserRating> toModel(UserRating entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(UserRatingController.class).getRatingByUserId(entity.getUserId())).withSelfRel()
            );
    }
}
