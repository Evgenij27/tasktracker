package com.tasktracker.tracker.taskcomment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class JpaTaskCommentService implements TaskCommentService {

    private final TaskCommentRepository repository;

    @Autowired
    public JpaTaskCommentService(TaskCommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public TaskComment create(TaskComment comment) {
        return repository.create(comment);
    }

    @Override
    public void delete(TaskComment comment) {
        repository.delete(comment);
    }

    @Override
    public List<TaskComment> all(Long taskId) {
        return repository.all(taskId);
    }
}
