package com.tasktracker.tracker.user;

import com.tasktracker.tracker.department.Department;

import java.util.List;

public interface UserService {

    User save(User user);

    void update(Long id, User newUser);

    User findById(Long id);

    List<User> findAll();

    void deleteById(Long id);

    void addDepartment(Long id, Department department);

}
