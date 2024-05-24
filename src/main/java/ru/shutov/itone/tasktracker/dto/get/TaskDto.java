package ru.shutov.itone.tasktracker.dto.get;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shutov.itone.tasktracker.enums.Priority;
import ru.shutov.itone.tasktracker.enums.Status;
import ru.shutov.itone.tasktracker.enums.TaskType;

import java.util.UUID;

@Data
@NoArgsConstructor
public class TaskDto {
    private UUID id;
    private String name;
    private Priority priority;
    private UserDto assignee;
    private Status status;
    private TaskType taskType;
    private TaskDto epic;
}
