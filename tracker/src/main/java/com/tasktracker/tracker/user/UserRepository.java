package com.tasktracker.app.user;

import java.util.List;

public interface UserRepository {

    User save(User user);

    void update(Long id, User newUser);

    User findById(Long id);

    List<User> findAll();

    void deleteById(Long id);
}
