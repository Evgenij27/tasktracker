package com.tasktracker.tracker.task;

import com.tasktracker.tracker.taskcomment.TaskComment;
import com.tasktracker.tracker.user.User;

import java.util.List;
import java.util.Set;

public interface TaskService {

    Task create(Task task);

    Task findById(Long taskId);

    List<Task> findAll(Long departmentId, String order);

    void update(Long taskId, Task task);

    void addComment(Long taskId, TaskComment comment);

    void deleteComment(Long taskId, TaskComment comment);

    Set<TaskComment> getTaskComments(Long taskId);

    void changeStatus(Long taskId, TaskStatus status);

    void addAssignee(Long taskId, User assignee);

}
