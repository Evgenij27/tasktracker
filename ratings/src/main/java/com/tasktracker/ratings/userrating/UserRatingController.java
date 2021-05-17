package com.tasktracker.ratings.userrating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratings")
public class UserRatingController {

    private final UserRatingService service;

    private final UserRatingAssembler assembler;

    @Autowired
    public UserRatingController(UserRatingService service, UserRatingAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRatingByUserId(@PathVariable("id") Long id) {
        final UserRating userRating = service.findById(id);
        if (userRating == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(userRating));
    }
}
