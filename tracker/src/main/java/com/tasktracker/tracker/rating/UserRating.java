package com.tasktracker.tracker.rating;

import lombok.Data;

import javax.persistence.*;

@Data
public class UserRating {

    private Long userId;

    private Integer rating;
}
