package ru.shutov.itone.tasktracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.shutov.itone.tasktracker.dto.post.AuthenticationDto;
import ru.shutov.itone.tasktracker.dto.post.UserPostDto;
import ru.shutov.itone.tasktracker.dto.response.JwtResponse;
import ru.shutov.itone.tasktracker.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtResponse register(@RequestBody @Valid UserPostDto userPostDto) {
        return userService.register(userPostDto);
    }

    @Operation(summary = "Аутентификация пользователя")
    @PostMapping("/login")
    public JwtResponse login(@RequestBody @Valid AuthenticationDto authenticationDto) {
        return userService.login(authenticationDto);
    }

    @Operation(summary = "Удаление аккаунта")
    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "Bearer Authentication")
    public void delete() {
        userService.delete();
    }
}
