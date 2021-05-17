package com.tasktracker.tracker.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tasktracker.tracker.department.Department;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
@Relation(collectionRelation = "users")
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(
        optional = true,
        cascade = CascadeType.PERSIST)
    @JoinColumn(name = "department_id")
    private Department department;

    @JsonProperty("rating")
    @Transient
    private Integer rating;

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
