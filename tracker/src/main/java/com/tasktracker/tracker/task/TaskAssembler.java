package com.tasktracker.tracker.task;

import com.tasktracker.tracker.taskcomment.TaskCommentController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TaskAssembler implements RepresentationModelAssembler<Task, EntityModel<Task>> {

    @Override
    public EntityModel<Task> toModel(Task task) {
        return EntityModel.of(task,
            linkTo(methodOn(TaskController.class).findAll()).withRel(IanaLinkRelations.COLLECTION),
            linkTo(methodOn(TaskController.class).findById(task.getId())).withRel(IanaLinkRelations.SELF),
            linkTo(methodOn(TaskController.class).changeStatus(task.getId(), null)).withRel("status"),
            linkTo(methodOn(TaskCommentController.class).addComment(task.getId(), null)).withRel("comments"),
            linkTo(methodOn(TaskController.class).addAssignee(task.getId(), null)).withRel("assignee"));
    }
}
