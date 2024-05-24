package ru.shutov.itone.tasktracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.shutov.itone.tasktracker.dto.get.ShortColDto;
import ru.shutov.itone.tasktracker.dto.post.ColPostDto;
import ru.shutov.itone.tasktracker.dto.request.ColNameRequest;
import ru.shutov.itone.tasktracker.dto.request.ColPositionRequest;
import ru.shutov.itone.tasktracker.service.ColService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/desk/col")
@RequiredArgsConstructor
public class ColController {
    private final ColService colService;

    @Operation(summary = "Создание колонки для указанной доски")
    @PostMapping("/{deskId}")
    public void create(@PathVariable("deskId") UUID deskId,
                       @RequestBody @Valid ColPostDto colPostDto) {
        colService.create(deskId, colPostDto);
    }

    @Operation(summary = "Получение всех колонок указанной доски")
    @GetMapping("/{deskId}")
    public List<ShortColDto> findByDesk(@PathVariable("deskId") UUID deskId) {
        return colService.findByDesk(deskId);
    }

    @Operation(summary = "Удаление колонки по ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID id) {
        colService.delete(id);
    }

    @Operation(summary = "Частичное обновление колонки: изменение позиции на доске")
    @PatchMapping("/{id}/position")
    public void updatePosition(@PathVariable("id") UUID id,
                               @RequestBody ColPositionRequest colPositionRequest) {
        colService.updatePosition(id, colPositionRequest);
    }

    @Operation(summary = "Частичное обновление колонки: изменение названия колонки")
    @PatchMapping("/{id}/name")
    public void updateName(@PathVariable("id") UUID id,
                           @RequestBody ColNameRequest colNameRequest) {
        colService.updateName(id, colNameRequest);
    }
}
