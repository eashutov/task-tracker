package ru.shutov.itone.tasktracker.exception;

import lombok.Getter;
import ru.shutov.itone.tasktracker.exception.event.EventInfo;

@Getter
public class BusinessException extends RuntimeException {
    private final EventInfo eventInfo;

    public BusinessException(EventInfo eventInfo, String message) {
        super(message);
        this.eventInfo = eventInfo;
    }

    public BusinessException(EventInfo eventInfo, Throwable cause) {
        super(eventInfo.getStatus().toString(), cause);
        this.eventInfo = eventInfo;
    }

    public BusinessException(EventInfo eventInfo, String message, Throwable cause) {
        super(message, cause);
        this.eventInfo = eventInfo;
    }

}
