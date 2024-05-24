package ru.shutov.itone.tasktracker.dto.request;

import lombok.Builder;
import lombok.Data;
import ru.shutov.itone.tasktracker.enums.Priority;
import ru.shutov.itone.tasktracker.enums.Status;
import ru.shutov.itone.tasktracker.enums.TaskType;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
public class TaskRequest {
    private Priority priority;
    private UUID assignee;
    private Timestamp createdAtFrom;
    private Timestamp createdAtTo;
    private Status status;
    private UUID author;
    private TaskType taskType;
    private UUID epic;
}
