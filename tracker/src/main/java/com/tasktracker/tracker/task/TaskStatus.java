package com.tasktracker.app.task;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskStatus {

    OPEN("open"), IN_PROGRESS("in_porgress"), DONE("done");

    private final String status;

    TaskStatus(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }
}
