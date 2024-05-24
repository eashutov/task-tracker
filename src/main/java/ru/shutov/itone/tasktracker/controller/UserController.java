package ru.shutov.itone.tasktracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.shutov.itone.tasktracker.dto.get.UserDto;
import ru.shutov.itone.tasktracker.dto.post.UserPostDto;
import ru.shutov.itone.tasktracker.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Получение всех пользователей")
    @GetMapping()
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @Operation(summary = "Получение пользователя по ID")
    @GetMapping("/{id}")
    public UserDto findById(@PathVariable UUID id) {
        return userService.findById(id);
    }

    @Operation(summary = "Добавление пользователя")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid UserPostDto userPostDto) {
        userService.create(userPostDto);
    }

    @Operation(summary = "Удаление пользователя по ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable UUID id) {
        userService.delete(id);
    }
}
