package ru.shutov.itone.tasktracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shutov.itone.tasktracker.model.NamedEntity;

import java.util.List;

@Entity
@Table(name = "cols")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Col extends NamedEntity {
    @ManyToOne
    @JoinColumn(name = "desk", referencedColumnName = "id", updatable = false, nullable = false)
    private Desk desk;

    @Column(name = "position")
    private Integer position;

    @OneToMany(mappedBy = "col")
    private List<Task> tasks;
}
