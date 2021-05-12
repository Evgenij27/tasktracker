package com.tasktracker.tracker.task;

import java.util.List;

public interface TaskRepository {

    Task create(Task task);

    Task findById(Long id);

    List<Task> findAll();

    void update(Task task);

    void addComment(Long id, TaskComment comment);
}
