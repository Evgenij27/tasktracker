package com.tasktracker.tracker.taskcomment;

import com.tasktracker.tracker.task.Task;

import java.util.List;

public interface TaskCommentService {

    TaskComment create(TaskComment comment);

    void delete(TaskComment comment);

    List<TaskComment> all(Long taskId);
}
