package ru.shutov.itone.tasktracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.shutov.itone.tasktracker.dto.get.MemberDto;
import ru.shutov.itone.tasktracker.dto.post.MembershipPostDto;
import ru.shutov.itone.tasktracker.service.MembershipService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/desk/{deskId}/membership")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class MembershipController {
    private final MembershipService membershipService;

    @Operation(summary = "Посмотреть всех участников доски с указанным ID")
    @GetMapping
    public List<MemberDto> findAllMembers(@PathVariable("deskId") UUID deskId) {
        return membershipService.findAllMembers(deskId);
    }

    @Operation(summary = "Добавить участника к доске с указанным ID")
    @PostMapping
    public void addMember(@PathVariable("deskId") UUID deskId, @RequestBody MembershipPostDto membershipPostDto) {
        membershipService.addMember(deskId, membershipPostDto);
    }

    @Operation(summary = "Убрать участника доски с указанным ID")
    @DeleteMapping("/{memberId}")
    public void removeMember(@PathVariable("deskId") UUID deskId, @PathVariable("memberId") UUID memberId) {
        membershipService.removeMember(deskId, memberId);
    }
}
