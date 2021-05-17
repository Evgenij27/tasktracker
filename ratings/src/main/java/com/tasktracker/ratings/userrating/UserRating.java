package com.tasktracker.ratings.userrating;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_rating")
public class UserRating {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "rating")
    private Integer rating;
}
