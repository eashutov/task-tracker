package ru.shutov.itone.tasktracker.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeskPostDto {
    @NotBlank(message = "Название доски не должно быть пустым")
    private String name;
}
