package ru.shutov.itone.tasktracker.dto.get;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ShortColDto {
    private UUID id;
    private String name;
    private Integer position;
}
