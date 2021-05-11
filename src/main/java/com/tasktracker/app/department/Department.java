package com.tasktracker.app.department;

import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "department")
@Data
@Relation(collectionRelation = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "department_name")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
