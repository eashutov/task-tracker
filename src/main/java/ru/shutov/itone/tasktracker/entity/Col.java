package ru.shutov.itone.tasktracker.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.shutov.itone.tasktracker.model.NamedEntity;

import java.util.List;

@Entity
@Table(name = "cols")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Col extends NamedEntity {
    @ManyToOne
    @JoinColumn(name = "desk", referencedColumnName = "id", nullable = false)
    private Desk desk;

    @Column(name = "position")
    private Integer position;

    @OneToMany(mappedBy = "col")
    private List<Task> tasks;
}
