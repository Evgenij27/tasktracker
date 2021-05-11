package com.tasktracker.app.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    private final DepartmentAssembler assembler;

    @Autowired
    public DepartmentController(DepartmentService departmentService, DepartmentAssembler assembler) {
        this.departmentService = departmentService;
        this.assembler = assembler;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Department department) {
        final Department newDepartment = departmentService.save(department);

        URI created = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .build(newDepartment.getId());

        return ResponseEntity.created(created).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        final Department department = departmentService.findById(id);
        if (department == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(department));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        final CollectionModel<EntityModel<Department>> cm = assembler.toCollectionModel(departmentService.findAll());
        return ResponseEntity.ok(cm);
    }
}

