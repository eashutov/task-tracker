package ru.shutov.itone.tasktracker.exception.event;

import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;

public interface EventInfo {
    HttpStatus getStatus();

    Level getLevel();
}
