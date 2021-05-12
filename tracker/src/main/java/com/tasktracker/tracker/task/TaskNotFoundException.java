package com.tasktracker.tracker.task;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Task")
public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(Long id) {
        super(String.format("Task with id: %d not found", id));
    }
}
