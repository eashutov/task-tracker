package ru.shutov.itone.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.shutov.itone.tasktracker.dto.request.ColNameRequest;
import ru.shutov.itone.tasktracker.dto.request.ColPositionRequest;
import ru.shutov.itone.tasktracker.service.ColService;

import java.util.UUID;

@RestController
@RequestMapping("/col")
@RequiredArgsConstructor
public class ColController {
    private final ColService colService;

    @DeleteMapping("/{id}")
    public void deleteCol(@PathVariable("id") UUID id) {
        colService.deleteCol(id);
    }

    @PatchMapping("/{id}/position")
    public void updatePosition(@PathVariable("id") UUID id,
                               @RequestBody ColPositionRequest colPositionRequest) {
        colService.updatePosition(id, colPositionRequest);
    }

    @PatchMapping("/{id}/name")
    public void updateName(@PathVariable("id") UUID id,
                           @RequestBody ColNameRequest colNameRequest) {
        colService.updateName(id, colNameRequest);
    }
}
