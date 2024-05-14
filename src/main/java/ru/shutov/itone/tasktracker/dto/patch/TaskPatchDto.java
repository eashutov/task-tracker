package ru.shutov.itone.tasktracker.dto.patch;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shutov.itone.tasktracker.enums.Priority;
import ru.shutov.itone.tasktracker.enums.Status;
import ru.shutov.itone.tasktracker.enums.TaskType;

import java.util.UUID;

@Data
@NoArgsConstructor
public class TaskPatchDto {
    @NotBlank
    private String name;
    private UUID col;
    private Priority priority;
    private String description;
    private UUID assignee;
    private Status status;
    private TaskType taskType;
    private UUID epic;
}
