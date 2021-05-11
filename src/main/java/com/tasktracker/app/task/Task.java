package com.tasktracker.app.task;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tasktracker.app.user.User;
import lombok.Data;

import javax.persistence.*;
import java.sql.Clob;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NamedEntityGraphs({
    @NamedEntityGraph(
        name = "just-task",
        attributeNodes = {
            @NamedAttributeNode("id"),
            @NamedAttributeNode("topic"),
            @NamedAttributeNode("description"),
            @NamedAttributeNode("createdAt"),
            @NamedAttributeNode("author"),
            @NamedAttributeNode("assignee"),
        }
    ),
    @NamedEntityGraph(
        name = "task-details",
        attributeNodes = {
            @NamedAttributeNode("id"),
            @NamedAttributeNode("topic"),
            @NamedAttributeNode("description"),
            @NamedAttributeNode("createdAt"),
            @NamedAttributeNode("author"),
            @NamedAttributeNode("assignee"),
            @NamedAttributeNode("comments"),

        }
    )
})
@Entity
@Table(name = "task")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "topic")
    private String topic;

    @Lob
    @Column(name = "description")
    private Clob description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.OPEN;

    @Column(name = "created_at")
    @org.hibernate.annotations.CreationTimestamp
    private Instant createdAt;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "author_id")
    private User author;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @JsonManagedReference
    @OneToMany(
        mappedBy = "task",
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL
    )
    private Set<TaskComment> comments = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
