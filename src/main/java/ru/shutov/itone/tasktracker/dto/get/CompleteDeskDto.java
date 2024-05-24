package ru.shutov.itone.tasktracker.dto.get;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CompleteDeskDto {
    private UUID id;
    private String name;
    private UserDto creator;
    private List<ColDto> cols;
}
