package com.tasktracker.tracker.taskcomment;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaTaskCommentRepository implements TaskCommentRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public JpaTaskCommentRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public TaskComment create(TaskComment comment) {
        Session session = sessionFactory.getCurrentSession();
        Long id = (Long) session.save(comment);
        comment.setId(id);
        return comment;
    }

    @Override
    public void delete(TaskComment comment) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(comment);
    }

    @Override
    public List<TaskComment> all(Long taskId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
            "select tc from TaskComment tc where tc.task.id = :taskId", TaskComment.class)
            .setParameter("taskId", taskId)
            .getResultList();
    }
}
