package com.tasktracker.tracker.task;

import com.tasktracker.tracker.user.User;
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

    private final TaskAssembler assembler;

    @Autowired
    public TaskController(TaskService taskService, TaskAssembler assembler) {
        this.taskService = taskService;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(value = "department", required = false) Long departmentId,
                                     @RequestParam(value = "order", required = false, defaultValue = "asc") String order) {
        return ResponseEntity.ok(assembler.toCollectionModel(taskService.findAll(departmentId, order)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        final Task task = taskService.findById(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(task));
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
}
