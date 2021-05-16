package com.tasktracker.ratings.userrating;

public interface UserRatingRepository {

    UserRating findByUserId(Long userId);
}
