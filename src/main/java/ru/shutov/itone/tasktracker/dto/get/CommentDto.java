package ru.shutov.itone.tasktracker.dto.get;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CommentDto {
    private UUID id;
    private String text;
    private Timestamp createdAt;
    private UserDto author;
}
