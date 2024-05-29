package ru.shutov.itone.tasktracker.dto.post;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shutov.itone.tasktracker.enums.Role;

import java.util.UUID;

@Data
@NoArgsConstructor
public class MembershipPostDto {
    private UUID member;
    private Role role;
}
