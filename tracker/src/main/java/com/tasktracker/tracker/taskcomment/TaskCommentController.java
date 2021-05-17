package com.tasktracker.tracker.taskcomment;

import com.tasktracker.tracker.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/tasks/{taskId}/comments")
public class TaskCommentController {

    private final TaskCommentService commentService;

    private final TaskCommentAssembler assembler;

    @Autowired
    public TaskCommentController(TaskCommentService commentService, TaskCommentAssembler assembler) {
        this.commentService = commentService;
        this.assembler = assembler;
    }

    @PostMapping
    public ResponseEntity<?> addComment(@PathVariable("taskId") Long id, @RequestBody TaskComment comment) {
        Task task = new Task();
        task.setId(id);
        comment.setTask(task);

        comment = commentService.create(comment);

        URI see = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .build(comment.getId());

        return ResponseEntity.status(HttpStatus.CREATED)
            .location(see)
            .build();
    }

    @GetMapping
    public ResponseEntity<?> getComments(@PathVariable("taskId") Long id) {
        return ResponseEntity.ok(assembler.toCollectionModel(commentService.all(id)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> removeComment(@PathVariable("taskId") Long taskId, @PathVariable("id") Long commentId) {
        TaskComment comment = new TaskComment();
        comment.setId(commentId);

        Task task = new Task();
        task.setId(taskId);
        comment.setTask(task);

        commentService.delete(comment);
        return ResponseEntity.ok().build();
    }
}
