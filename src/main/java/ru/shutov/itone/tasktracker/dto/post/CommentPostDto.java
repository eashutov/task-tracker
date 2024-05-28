package ru.shutov.itone.tasktracker.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentPostDto {
    @NotBlank(message = "Текст комментария не должен быть пустым")
    private String text;
}
