package ru.shutov.itone.tasktracker.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticationDto {
    @NotBlank(message = "Имя пользователя не должно быть пустым")
    private String username;

    private String password;
}
