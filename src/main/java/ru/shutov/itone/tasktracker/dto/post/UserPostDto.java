package ru.shutov.itone.tasktracker.dto.post;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPostDto {
    @NotBlank(message = "Имя пользователя не должно быть пустым")
    private String username;

    private String firstName;

    private String lastName;

    @Email(message = "Некорректный email")
    @NotNull(message = "Email не должен быть null")
    private String email;
}
