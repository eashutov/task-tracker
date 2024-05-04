package ru.shutov.itone.tasktracker.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.shutov.itone.tasktracker.model.NamedEntity;

import java.util.Set;

@Entity
@Table(name = "cols")
@Getter
@Setter
@Builder
public class Col extends NamedEntity {
    @ManyToOne
    @JoinColumn(name = "desk", referencedColumnName = "id", nullable = false)
    private Desk desk;

    @Column(name = "position")
    private Integer position;

    @OneToMany(mappedBy = "col")
    private Set<Task> tasks;
}
