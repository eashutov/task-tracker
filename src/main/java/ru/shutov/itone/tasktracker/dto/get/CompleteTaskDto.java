package ru.shutov.itone.tasktracker.dto.get;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shutov.itone.tasktracker.enums.Priority;
import ru.shutov.itone.tasktracker.enums.Status;
import ru.shutov.itone.tasktracker.enums.TaskType;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CompleteTaskDto {
    private UUID id;
    private ShortColDto col;
    private String name;
    private Priority priority;
    private String description;
    private UserDto assignee;
    private Timestamp createdAt;
    private Timestamp lastUpdate;
    private Status status;
    private UserDto author;
    private TaskType taskType;
    private TaskDto epic;
    private List<CommentDto> comments;
}
