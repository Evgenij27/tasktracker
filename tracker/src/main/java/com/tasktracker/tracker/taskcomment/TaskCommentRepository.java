package com.tasktracker.tracker.taskcomment;

import java.util.List;

public interface TaskCommentRepository {

    TaskComment create(TaskComment comment);

    void delete(TaskComment comment);

    List<TaskComment> all(Long taskId);
}
