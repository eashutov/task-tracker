package ru.shutov.itone.tasktracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shutov.itone.tasktracker.model.NamedEntity;

import java.util.List;

@NamedEntityGraph(
        name = "desk-with-tasks",
        attributeNodes = {
                @NamedAttributeNode(value = "creator"),
                @NamedAttributeNode(value = "cols", subgraph = "col-tasks")
        },
        subgraphs = {
                @NamedSubgraph(name = "col-tasks", attributeNodes = {
                        @NamedAttributeNode(value = "tasks", subgraph = "task-assignee-epic")
                }),
                @NamedSubgraph(name = "task-assignee-epic", attributeNodes = {
                        @NamedAttributeNode(value = "assignee"),
                        @NamedAttributeNode(value = "epic")
                })
        }
)
@Entity
@Table(name = "desks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Desk extends NamedEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator", referencedColumnName = "id", updatable = false, nullable = false)
    private User creator;

    @OneToMany(mappedBy = "desk")
    private List<Col> cols;

    @OneToMany(mappedBy = "desk")
    private List<Membership> memberships;
}
