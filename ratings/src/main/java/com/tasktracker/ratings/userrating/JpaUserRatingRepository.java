package com.tasktracker.ratings.userrating;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class JpaUserRatingRepository implements UserRatingRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public JpaUserRatingRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public UserRating findByUserId(Long userId) {
        return sessionFactory.getCurrentSession()
            .find(UserRating.class, userId);
    }

    @Override
    public UserRating create(UserRating rating) {
        sessionFactory.getCurrentSession()
            .save(rating);
        return rating;
    }
}
