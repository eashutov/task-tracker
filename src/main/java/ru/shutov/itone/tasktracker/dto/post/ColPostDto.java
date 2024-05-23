package ru.shutov.itone.tasktracker.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ColPostDto {
    @NotBlank(message = "Название колонки не должно быть пустым")
    private String name;
    private Integer position;
}
