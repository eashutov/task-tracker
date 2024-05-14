package ru.shutov.itone.tasktracker.dto.get;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserDto {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
}
