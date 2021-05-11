package com.tasktracker.app.user;

import com.tasktracker.app.department.Department;
import com.tasktracker.app.task.TaskComment;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "user")
@Table(name = "users")
@Data
@Relation(collectionRelation = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(
        fetch = FetchType.EAGER,
        optional = true,
        cascade = CascadeType.PERSIST)
    @JoinColumn(name = "department_id")
    private Department department;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
