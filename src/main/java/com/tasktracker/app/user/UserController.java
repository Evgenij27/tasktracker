package com.tasktracker.app.user;

import com.tasktracker.app.department.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UserAssembler assembler;

    @Autowired
    public UserController(UserService userService, UserAssembler assembler) {
        this.userService = userService;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<?> all() {
        final CollectionModel<EntityModel<User>> cm = assembler.toCollectionModel(userService.findAll());
        return ResponseEntity.ok(cm);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> one(@PathVariable("id") Long id) {
        final User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        EntityModel<User> model = assembler.toModel(user);
        return ResponseEntity.ok(model);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) {
        user = userService.save(user);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/users/{id}")
            .build(user.getId());

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        final User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody User newUser) {
        final User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setName(newUser.getName());
        userService.update(id, user);
        return ResponseEntity.ok(assembler.toModel(user));
    }

    @PostMapping("/{id}/department")
    public ResponseEntity<?> addDepartment(@PathVariable("id") Long id, @RequestBody Department department) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setDepartment(department);
        userService.update(id, user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .replacePath("/users/{id}")
            .build(user.getId());

        return ResponseEntity
            .status(HttpStatus.SEE_OTHER)
            .location(location)
            .build();
    }

}
