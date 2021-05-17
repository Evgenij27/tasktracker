package com.tasktracker.tracker.task;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tasktracker.tracker.taskcomment.TaskComment;
import com.tasktracker.tracker.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.server.core.Relation;

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
            @NamedAttributeNode(value = "author", subgraph = "user-department"),
            @NamedAttributeNode(value = "assignee", subgraph = "user-department"),
        },
        subgraphs = {
            @NamedSubgraph(
                name = "user-department",
                attributeNodes = {@NamedAttributeNode("department")}
            )
        }
    ),
    @NamedEntityGraph(
        name = "task-details",
        attributeNodes = {
            @NamedAttributeNode("id"),
            @NamedAttributeNode("topic"),
            @NamedAttributeNode("description"),
            @NamedAttributeNode("createdAt"),
            @NamedAttributeNode(value = "author", subgraph = "user-department"),
            @NamedAttributeNode(value = "assignee", subgraph = "user-department"),
            @NamedAttributeNode("comments"),
        },
        subgraphs = {
            @NamedSubgraph(
                name = "user-department",
                attributeNodes = {@NamedAttributeNode("department")}
            )
        }
    )
})
@Entity
@Table(name = "task")
@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
@Relation(collectionRelation = "tasks")
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

    @OneToOne(optional = false)
    @JoinColumn(name = "author_id")
    private User author;

    @OneToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @JsonManagedReference
    @OneToMany(
        mappedBy = "task",
        fetch = FetchType.LAZY,
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

    public void addComment(TaskComment comment) {
        if (comment == null) throw new NullPointerException("Comment is null");

        comment.setTask(this);
        getComments().add(comment);
    }
}
