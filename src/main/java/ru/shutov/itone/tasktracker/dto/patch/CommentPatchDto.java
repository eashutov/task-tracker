package ru.shutov.itone.tasktracker.dto.patch;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentPatchDto {
    @NotBlank(message = "Текст комментария не должен быть пустым")
    private String text;
}
