package ru.shutov.itone.tasktracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shutov.itone.tasktracker.model.BaseEntity;

import java.util.List;

@NamedEntityGraph(
        name = "user-with-memberships",
        attributeNodes = {
                @NamedAttributeNode(value = "memberships", subgraph = "desk")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "desk",
                        attributeNodes = {
                                @NamedAttributeNode(value = "desk")
                        }
                )
        }
)
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Column(name = "username", unique = true, nullable = false)
    @NotBlank
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "author")
    private List<Task> authoredTasks;

    @OneToMany(mappedBy = "assignee")
    private List<Task> assignedTasks;

    @OneToMany(mappedBy = "author")
    private List<Comment> comments;

    @OneToMany(mappedBy = "creator")
    private List<Desk> desks;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    @NotNull
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Membership> memberships;
}
