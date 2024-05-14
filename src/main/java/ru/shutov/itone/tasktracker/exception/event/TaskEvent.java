package ru.shutov.itone.tasktracker.exception.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TaskEvent implements EventInfo {
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND, Level.INFO);

    private final HttpStatus httpStatus;
    private final Level level;

    @Override
    public HttpStatus getStatus() {
        return httpStatus;
    }
}
