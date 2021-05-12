package com.tasktracker.tracker.task;

import com.tasktracker.tracker.user.User;

import java.util.List;

public interface TaskService {

    Task create(Task task);

    Task findById(Long taskId);

    List<Task> findAll();

    void update(Long taskId, Task task);

    void addComment(Long taskId, TaskComment comment);

    void deleteComment(Long taskId, TaskComment comment);

    void changeStatus(Long taskId, TaskStatus status);

    void addAssignee(Long taskId, User assignee);

}
