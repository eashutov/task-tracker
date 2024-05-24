package ru.shutov.itone.tasktracker.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CommentPostDto {
    @NotBlank(message = "Текст комментария не должен быть пустым")
    private String text;
    private Timestamp createdAt;
    @NotNull(message = "ID автора не может быть null")
    private UUID author;
}
