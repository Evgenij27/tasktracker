package com.tasktracker.app.task;

import com.tasktracker.app.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    @Autowired
    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Task create(Task task) {
        return repository.create(task);
    }

    @Override
    public Task findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Task> findAll() {
        return repository.findAll();
    }

    @Override
    public void update(Task task) {
        repository.update(task.getId(), task);
    }

    @Override
    public void addComment(Long id, TaskComment comment) {
        repository.addComment(id, comment);
        /*Task task = findById(id);
        task.getComments().add(comment);
        update(task);*/
    }

    @Override
    public void changeStatus(Long id, TaskStatus status) {
        Task task = findById(id);
        task.setStatus(status);
        update(task);
    }

    @Override
    public void addAssignee(Long id, User user) {
        Task task = findById(id);
        task.setAssignee(user);
        update(task);
    }
}
