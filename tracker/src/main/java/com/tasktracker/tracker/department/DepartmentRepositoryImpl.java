package com.tasktracker.app.department;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public DepartmentRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Department save(Department department) {
        Session session = sessionFactory.getCurrentSession();
        Long id = (Long) session.save(department);
        department.setId(id);
        return department;
    }

    @Override
    public Department findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Department.class, id);
    }

    @Override
    public List<Department> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select d from Department d", Department.class)
            .getResultList();
    }
}
