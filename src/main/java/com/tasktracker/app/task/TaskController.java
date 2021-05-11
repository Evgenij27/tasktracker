package com.tasktracker.app.task;

import com.tasktracker.app.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(taskService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        final Task task = taskService.findById(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Task task) {
        task = taskService.create(task);
        URI created = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .build(task.getId());
        return ResponseEntity.created(created).build();
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Long id, @RequestBody TaskStatusChange statusChange) {
        taskService.changeStatus(id, statusChange.getStatus());

        URI see = ServletUriComponentsBuilder.fromCurrentRequest()
            .replacePath("/{id}")
            .build(id);

        return ResponseEntity.status(HttpStatus.SEE_OTHER)
            .location(see)
            .build();
    }

    @PostMapping("/{id}/assignee")
    public ResponseEntity<?> addAssignee(@PathVariable("id") Long id, @RequestBody User user) {
        taskService.addAssignee(id, user);

        URI see = ServletUriComponentsBuilder.fromCurrentRequest()
            .replacePath("/{id}")
            .build(id);

        return ResponseEntity.status(HttpStatus.SEE_OTHER)
            .location(see)
            .build();

    }
    @PostMapping("/{id}/comments")
    public ResponseEntity<?> addComment(@PathVariable("id") Long id, @RequestBody TaskComment comment) {
        taskService.addComment(id, comment);

        URI see = ServletUriComponentsBuilder.fromCurrentRequest()
            .replacePath("/{id}")
            .build(id);

        return ResponseEntity.status(HttpStatus.SEE_OTHER)
            .location(see)
            .build();
    }

    @DeleteMapping("/{id}/comments/{cid}")
    public ResponseEntity<?> removeComment(@PathVariable("id") Long id, @PathVariable("cid") Long commentId) {
        return ResponseEntity.ok().build();
    }

}
