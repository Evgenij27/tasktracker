package com.tasktracker.tracker.department;

import java.util.List;

public interface DepartmentRepository {

    Department save(Department department);

    Department findById(Long id);

    List<Department> findAll();
}
