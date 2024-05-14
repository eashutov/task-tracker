package ru.shutov.itone.tasktracker.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.shutov.itone.tasktracker.enums.Priority;
import ru.shutov.itone.tasktracker.enums.Status;
import ru.shutov.itone.tasktracker.enums.TaskType;
import ru.shutov.itone.tasktracker.model.NamedEntity;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task extends NamedEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col", referencedColumnName = "id")
    private Col col;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee", referencedColumnName = "id")
    private User assignee;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;

    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp lastUpdate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "author", referencedColumnName = "id")
    private User author;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "epic", referencedColumnName = "id")
    private Task epic;

    @OneToMany(mappedBy = "task")
    private List<Comment> comments;
}
