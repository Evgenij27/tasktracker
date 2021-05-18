package com.tasktracker.tracker.task;

import com.tasktracker.tracker.taskcomment.TaskComment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.graph.RootGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", taskDetailsGrapth);
        final Task task = session.find(Task.class, id, properties);
        if (task == null) {
            throw new TaskNotFoundException(id);
        }
        return task;
    }

    @Override
    public List<Task> findAll(Long departmentId, String order) {
        final Session session = sessionFactory.getCurrentSession();
        final RootGraph<?> entityGraph = session.getEntityGraph("just-task");

        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<Task> cq = cb.createQuery(Task.class);
        final Root<Task> root = cq.from(Task.class);

        CriteriaQuery<Task> select = cq.select(root);
        if (departmentId != null) {
            select = select.where(cb.equal(root.get("author").get("department").get("id"), departmentId));
        }

        if (order.equalsIgnoreCase("asc")) {
            select = select.orderBy(cb.asc(root.get("createdAt")));
        } else {
            select = select.orderBy(cb.desc(root.get("createdAt")));
        }

        return session.createQuery(select)
            .setHint("javax.persistence.fetchgraph", entityGraph)
            .getResultList();
    }

    @Override
    public void update(Task newTask) {
        final Session session = sessionFactory.getCurrentSession();
        session.update(newTask);
    }

    @Override
    public void addComment(Long id, TaskComment comment) {
        Session session = sessionFactory.getCurrentSession();
        Task task = session.find(Task.class, id);
        comment.setTask(task);
        task.getComments().add(comment);
        session.update(task);
    }
}
