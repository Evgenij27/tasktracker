package com.tasktracker.tracker.department;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DepartmentAssembler implements RepresentationModelAssembler<Department, EntityModel<Department>> {

    @Override
    public EntityModel<Department> toModel(Department entity) {
        return EntityModel.of(entity,
            linkTo(methodOn(DepartmentController.class).findById(entity.getId())).withSelfRel(),
            linkTo(methodOn(DepartmentController.class).create(null)).withRel(IanaLinkRelations.COLLECTION));
    }
}
