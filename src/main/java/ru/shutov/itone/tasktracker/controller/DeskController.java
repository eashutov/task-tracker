package ru.shutov.itone.tasktracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.shutov.itone.tasktracker.dto.get.CompleteDeskDto;
import ru.shutov.itone.tasktracker.dto.get.DeskDto;
import ru.shutov.itone.tasktracker.dto.post.DeskPostDto;
import ru.shutov.itone.tasktracker.service.DeskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/desk")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class DeskController {
    private final DeskService deskService;

    @Operation(summary = "Получение всех досок")
    @GetMapping
    public List<DeskDto> findAll() {
        return deskService.findAll();
    }

    @Operation(summary = "Получение полной (включая все колонки и задачи в каждой) доски по ID")
    @GetMapping("/{id}")
    public CompleteDeskDto findDesk(@PathVariable("id") UUID id) {
        return deskService.findById(id);
    }

    @Operation(summary = "Удаление доски по ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID id) {
        deskService.deleteById(id);
    }

    @Operation(summary = "Создание доски")
    @PostMapping
    public void create(@RequestBody @Valid DeskPostDto deskPostDto) {
        deskService.create(deskPostDto);
    }
}
