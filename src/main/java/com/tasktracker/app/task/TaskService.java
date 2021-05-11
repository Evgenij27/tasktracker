package com.tasktracker.app.task;

import com.tasktracker.app.user.User;

import java.util.List;

public interface TaskService {

    Task create(Task task);

    Task findById(Long id);

    List<Task> findAll();

    void update(Task task);

    void addComment(Long id, TaskComment comment);

    void changeStatus(Long id, TaskStatus status);

    void addAssignee(Long id, User user);
}
