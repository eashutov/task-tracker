package ru.shutov.itone.tasktracker.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.shutov.itone.tasktracker.entity.Membership;
import ru.shutov.itone.tasktracker.entity.User;
import ru.shutov.itone.tasktracker.enums.Role;
import ru.shutov.itone.tasktracker.repository.MembershipRepository;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class DeskAccessManager {
    private final MembershipRepository membershipRepository;

    public boolean hasAccess(Authentication authentication, UUID deskId) {
        return anyRole(authentication, deskId, Role.ADMIN, Role.MEMBER);
    }

    public boolean hasAdminAccess(Authentication authentication, UUID deskId) {
        return anyRole(authentication, deskId, Role.ADMIN);
    }

    private boolean anyRole(Authentication authentication, UUID deskId, Role... roles) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        Predicate<Role> predicate = role -> Arrays.asList(roles).contains(role);
        return membershipRepository.findByMemberAndDeskId(user, deskId).stream()
                .map(Membership::getRole)
                .anyMatch(predicate);
    }
}
