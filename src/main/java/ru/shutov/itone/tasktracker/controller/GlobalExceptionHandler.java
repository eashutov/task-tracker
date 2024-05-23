package ru.shutov.itone.tasktracker.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.shutov.itone.tasktracker.dto.response.ErrorResponse;
import ru.shutov.itone.tasktracker.exception.BusinessException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestControllerAdvice
@Slf4j
@Hidden
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException exception) {
        log.error("Unexpected exception: {}", exception.getMessage());
        exception.printStackTrace();
        return handle(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.debug(exception.getMessage(), exception);
        return handleBindValidationException(exception);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        return handle(exception, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception) {
        Level level = exception.getEventInfo().getLevel();
        HttpStatus status = exception.getEventInfo().getStatus();
        log.atLevel(level).log(exception.getMessage() + "; Status code: " + status.value());
        return handle(exception, status);
    }

    private ResponseEntity<ErrorResponse> handle(Exception exception, HttpStatus status) {
        return ResponseEntity.status(status).body(body(exception.getMessage(), status.value()));
    }

    private ErrorResponse body(String message, Integer code) {
        return new ErrorResponse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), message, code);
    }

    private ResponseEntity<ErrorResponse> handleBindValidationException(BindException exception) {
        String message = IntStream.range(0, exception.getErrorCount()).mapToObj(i -> i + 1 + ". " +
                exception.getAllErrors().get(i).getDefaultMessage()).collect(Collectors.joining("; "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body(message, 400));
    }
}
