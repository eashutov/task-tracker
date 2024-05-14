package ru.shutov.itone.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.shutov.itone.tasktracker.dto.get.DeskDto;
import ru.shutov.itone.tasktracker.dto.get.CompleteDeskDto;
import ru.shutov.itone.tasktracker.dto.post.ColPostDto;
import ru.shutov.itone.tasktracker.dto.post.DeskPostDto;
import ru.shutov.itone.tasktracker.service.DeskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/desk")
@RequiredArgsConstructor
public class DeskController {
    private final DeskService deskService;

    @GetMapping
    public List<DeskDto> findAll() {
        return deskService.findAll();
    }

    @GetMapping("/{id}")
    public CompleteDeskDto findDesk(@PathVariable("id") UUID id) {
        return deskService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID id) {
        deskService.deleteById(id);
    }

    @PostMapping
    public void create(@RequestBody DeskPostDto deskPostDto) {
        deskService.create(deskPostDto);
    }

    @PostMapping("/{id}/col")
    public void createCol(@PathVariable("id") UUID id,
                          @RequestBody ColPostDto colPostDto) {
        deskService.createCol(id, colPostDto);
    }
}
