package ru.shutov.itone.tasktracker.dto.request;

import ru.shutov.itone.tasktracker.enums.Priority;
import ru.shutov.itone.tasktracker.enums.Status;
import ru.shutov.itone.tasktracker.enums.TaskType;

import java.sql.Timestamp;
import java.util.UUID;

public record TaskRequest(
        Priority priority,
        UUID assignee,
        Timestamp createdAtFrom,
        Timestamp createdAtTo,
        Status status,
        UUID author,
        TaskType taskType,
        UUID epic
) { }
