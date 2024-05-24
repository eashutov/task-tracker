package ru.shutov.itone.tasktracker.exception.event;

import lombok.AllArgsConstructor;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum EventInfoImpl implements EventInfo {
    NOT_FOUND(HttpStatus.NOT_FOUND, Level.DEBUG),
    ALREADY_EXISTS(HttpStatus.BAD_REQUEST, Level.DEBUG);

    private final HttpStatus httpStatus;
    private final Level level;

    @Override
    public HttpStatus getStatus() {
        return httpStatus;
    }

    @Override
    public Level getLevel() {
        return level;
    }
}
