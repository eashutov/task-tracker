package ru.shutov.itone.tasktracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.shutov.itone.tasktracker.dto.patch.CommentPatchDto;
import ru.shutov.itone.tasktracker.dto.post.CommentPostDto;
import ru.shutov.itone.tasktracker.service.CommentService;

import java.util.UUID;

@RestController
@RequestMapping("/desk/{deskId}/task/comment")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "Создание комментария для указанной задачи")
    @PostMapping("/{taskId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void postComment(@PathVariable("deskId") UUID deskId,
                            @PathVariable("taskId") UUID taskId,
                            @RequestBody @Valid CommentPostDto commentPostDto) {
        commentService.post(taskId, commentPostDto);
    }

    @Operation(summary = "Обновление комментария по ID")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("deskId") UUID deskId,
                       @PathVariable("id") UUID id,
                       @RequestBody @Valid CommentPatchDto commentPatchDto) {
        commentService.update(id, commentPatchDto);
    }

    @Operation(summary = "Удаление комментария по ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("deskId") UUID deskId, @PathVariable UUID id) {
        commentService.delete(id);
    }
}
