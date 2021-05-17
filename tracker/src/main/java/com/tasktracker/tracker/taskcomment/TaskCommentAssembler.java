package com.tasktracker.tracker.taskcomment;

import com.tasktracker.tracker.task.TaskController;
import com.tasktracker.tracker.taskcomment.TaskComment;
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
            WebMvcLinkBuilder.linkTo(methodOn(TaskController.class).addComment(entity.getTask().getId(), null))
                .withRel(IanaLinkRelations.COLLECTION),
            linkTo(methodOn(TaskController.class).removeComment(entity.getTask().getId(), entity.getId()))
                .withSelfRel()
            );
    }
}
