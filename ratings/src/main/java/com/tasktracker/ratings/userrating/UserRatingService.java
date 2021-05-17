package com.tasktracker.ratings.userrating;

public interface UserRatingService {

    UserRating findById(Long userId);

    UserRating create(UserRating rating);
}
