package com.tasktracker.tracker.taskcomment;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TaskCommentAssembler implements RepresentationModelAssembler<TaskComment, EntityModel<TaskComment>> {

    @Override
    public EntityModel<TaskComment> toModel(TaskComment entity) {
        return EntityModel.of(entity,
            WebMvcLinkBuilder.linkTo(methodOn(TaskCommentController.class).addComment(entity.getTask().getId(), null))
                .withRel(IanaLinkRelations.COLLECTION),
            linkTo(methodOn(TaskCommentController.class).removeComment(entity.getTask().getId(), entity.getId()))
                .withSelfRel()
            );
    }
}
