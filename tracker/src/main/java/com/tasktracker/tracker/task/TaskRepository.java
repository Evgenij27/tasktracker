package com.tasktracker.tracker.task;

import com.tasktracker.tracker.taskcomment.TaskComment;

import java.util.List;

public interface TaskRepository {

    Task create(Task task);

    Task findById(Long id);

    List<Task> findAll(Long departmentId, String order);

    void update(Task task);

    void addComment(Long id, TaskComment comment);
}
