package ru.shutov.itone.tasktracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class NamedEntity extends BaseEntity {
    @Column(name = "name")
    @NotBlank
    private String name;
}
