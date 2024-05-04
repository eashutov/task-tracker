package ru.shutov.itone.tasktracker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import ru.shutov.itone.tasktracker.model.BaseEntity;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
public class User extends BaseEntity {
    @Column(name = "username", unique = true, nullable = false)
    @NotBlank
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "author")
    private Set<Task> authoredTasks;

    @OneToMany(mappedBy = "assignee")
    private Set<Task> assignedTasks;

    @OneToMany(mappedBy = "author")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "creator")
    private Set<Desk> desks;
}
