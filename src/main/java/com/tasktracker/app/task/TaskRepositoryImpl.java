package com.tasktracker.app.task;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.graph.RootGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public TaskRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Task create(Task task) {
        Session session = sessionFactory.getCurrentSession();
        Long id = (Long) session.save(task);
        task.setId(id);
        return task;
    }

    @Override
    public Task findById(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        final RootGraph<?> taskDetailsGrapth = session.getEntityGraph("task-details");
       /* Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", taskDetailsGrapth);*/
        final Task task = session.find(Task.class, id);
        if (task == null) {
            throw new TaskNotFoundException(id);
        }
        return task;
    }

    @Override
    public List<Task> findAll() {
        final Session session = sessionFactory.getCurrentSession();
        final RootGraph<?> entityGraph = session.getEntityGraph("just-task");
        return session
            .createQuery("select t from Task t", Task.class)
            .setHint("javax.persistence.fetchgraph", entityGraph)
            .getResultList();
    }

    @Override
    public void update(Long id, Task newTask) {
        final Session session = sessionFactory.getCurrentSession();
        Task task = session.find(Task.class, id);
        if (task == null) {
            throw new TaskNotFoundException(id);
        }
        task.setDescription(newTask.getDescription());
        task.setTopic(newTask.getTopic());
        session.update(task);
    }

    @Override
    public void addComment(Long id, TaskComment comment) {
        final Session session = sessionFactory.getCurrentSession();
        Task task = session.find(Task.class, id);
        comment.setTask(task);
        task.getComments().add(comment);
        session.update(task);
    }
}
