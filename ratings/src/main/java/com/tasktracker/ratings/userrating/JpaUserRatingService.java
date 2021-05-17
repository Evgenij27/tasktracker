package com.tasktracker.ratings.userrating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JpaUserRatingService implements UserRatingService {

    private final UserRatingRepository repository;

    @Autowired
    public JpaUserRatingService(UserRatingRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserRating findById(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public UserRating create(UserRating rating) {
        return repository.create(rating);
    }
}
