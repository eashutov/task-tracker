package ru.shutov.itone.tasktracker.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.shutov.itone.tasktracker.dto.response.ErrorResponse;
import ru.shutov.itone.tasktracker.exception.BusinessException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
@Slf4j
public class ErrorControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception) {
        Level level = exception.getEventInfo().getLevel();
        HttpStatus status = exception.getEventInfo().getStatus();
        log.atLevel(level).log(exception.getMessage() + "; Status code: " + status);
        return handle(exception, status);
    }

    private ResponseEntity<ErrorResponse> handle(Exception exception, HttpStatus status) {
        return ResponseEntity.status(status).body(body(exception.getMessage(), status.value()));
    }

    private ErrorResponse body(String message, Integer code) {
        return new ErrorResponse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), message, code);
    }
}
