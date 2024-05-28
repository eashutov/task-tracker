package ru.shutov.itone.tasktracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shutov.itone.tasktracker.enums.Role;
import ru.shutov.itone.tasktracker.model.BaseEntity;

@Entity
@Table(name = "desks_memberships")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Membership extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member", referencedColumnName = "id", nullable = false, updatable = false)
    private User member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "desk", referencedColumnName = "id", nullable = false, updatable = false)
    private Desk desk;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;
}
