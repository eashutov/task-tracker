package ru.shutov.itone.tasktracker.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.shutov.itone.tasktracker.model.NamedEntity;

import java.util.Set;

@Entity
@Table(name = "desks")
@Getter
@Setter
@Builder
public class Desk extends NamedEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator", referencedColumnName = "id", nullable = false)
    private User creator;

    @OneToMany(mappedBy = "desk")
    private Set<Col> cols;
}
