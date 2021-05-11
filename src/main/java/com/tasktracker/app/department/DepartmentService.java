package com.tasktracker.app.department;

import java.util.List;

public interface DepartmentService {

    Department save(Department department);

    List<Department> findAll();

    Department findById(Long id);
}
