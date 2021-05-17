package com.tasktracker.tracker.task;

import com.tasktracker.tracker.rating.UserRating;
import com.tasktracker.tracker.rating.UserRatingService;
import com.tasktracker.tracker.taskcomment.TaskComment;
import com.tasktracker.tracker.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    private final UserRatingService ratingService;

    @Autowired
    public TaskServiceImpl(TaskRepository repository,
                           UserRatingService ratingService) {
        this.repository = repository;
        this.ratingService = ratingService;
    }

    @Override
    public Task create(Task task) {
        return repository.create(task);
    }

    @Override
    public Task findById(Long id) {
        Task task = repository.findById(id);
        User assignee = task.getAssignee();
        UserRating assigneeRating = ratingService.getUserRatingByUserId(assignee.getId());
        assignee.setRating(assigneeRating.getRating());

        User author = task.getAuthor();
        UserRating authorRating = ratingService.getUserRatingByUserId(author.getId());
        author.setRating(authorRating.getRating());
        return task;
    }

    @Override
    public List<Task> findAll() {
        return repository.findAll();
    }

    @Override
    public void update(Long id, Task task) {
        repository.update(task);
    }

    @Override
    public void addComment(Long id, TaskComment comment) {
        Task task = findById(id);
        task.addComment(comment);
        update(task.getId(), task);
    }

    @Override
    public void changeStatus(Long id, TaskStatus status) {
        Task task = findById(id);
        task.setStatus(status);
        update(task.getId(), task);
    }

    @Override
    public void addAssignee(Long id, User user) {
        Task task = findById(id);
        task.setAssignee(user);
        update(task.getId(), task);
    }

    @Override
    public void deleteComment(Long taskId, TaskComment comment) {
        Task task = findById(taskId);
        task.getComments().remove(comment);
        update(task.getId(), task);
    }

    @Override
    public Set<TaskComment> getTaskComments(Long taskId) {
        return findById(taskId)
            .getComments();
    }
}
