package ru.shutov.itone.tasktracker.dto.get;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shutov.itone.tasktracker.enums.Role;

import java.util.UUID;

@Data
@NoArgsConstructor
public class MemberDto {
    private UUID member;
    private Role role;
}
