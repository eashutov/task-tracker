package ru.shutov.itone.tasktracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.shutov.itone.tasktracker.dto.get.CompleteTaskDto;
import ru.shutov.itone.tasktracker.dto.get.TaskDto;
import ru.shutov.itone.tasktracker.dto.patch.TaskPatchDto;
import ru.shutov.itone.tasktracker.dto.post.TaskPostDto;
import ru.shutov.itone.tasktracker.dto.request.AssigneeRequest;
import ru.shutov.itone.tasktracker.dto.request.ColRequest;
import ru.shutov.itone.tasktracker.dto.request.StatusRequest;
import ru.shutov.itone.tasktracker.dto.request.TaskRequest;
import ru.shutov.itone.tasktracker.service.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/desk/{deskId}/task")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class TaskController {
    private final TaskService taskService;

    @Operation(summary = "Получение списка задач по критериям")
    @GetMapping
    public List<TaskDto> findBy(@PathVariable("deskId") UUID deskId, TaskRequest taskRequest) {
        return taskService.findByRequest(taskRequest);
    }

    @Operation(summary = "Получение подробного описания задачи")
    @GetMapping("/{id}")
    public CompleteTaskDto find(@PathVariable("deskId") UUID deskId, @PathVariable("id") UUID id) {
        return taskService.findById(id);
    }

    @Operation(summary = "Создание задачи")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable("deskId") UUID deskId, @RequestBody @Valid TaskPostDto taskPostDto) {
        taskService.create(taskPostDto);
    }

    @Operation(summary = "Удаление задачи по ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("deskId") UUID deskId, @PathVariable("id") UUID id) {
        taskService.deleteById(id);
    }

    @Operation(summary = "Обновление задачи по ID")
    @PatchMapping("/{id}")
    public void update(@PathVariable("deskId") UUID deskId,
                       @PathVariable("id") UUID id,
                       @RequestBody @Valid TaskPatchDto taskPatchDto) {
        taskService.update(id, taskPatchDto);
    }

    @Operation(summary = "Частичное обновление задачи: смена текущего исполнителя")
    @PatchMapping("/{id}/assignee")
    public void updateAssignee(@PathVariable("deskId") UUID deskId,
                               @PathVariable("id") UUID id,
                               @RequestBody AssigneeRequest assigneeRequest) {
        taskService.updateAssignee(id, assigneeRequest);
    }

    @Operation(summary = "Частичное обновление задачи: смена текущего статуса")
    @PatchMapping("/{id}/status")
    public void updateStatus(@PathVariable("deskId") UUID deskId,
                             @PathVariable("id") UUID id,
                             @RequestBody StatusRequest statusRequest) {
        taskService.updateStatus(id, statusRequest);
    }

    @Operation(summary = "Частичное обновление задачи: смена текущей колонки")
    @PatchMapping("/{id}/col")
    public void updateCol(@PathVariable("deskId") UUID deskId,
                          @PathVariable("id") UUID id,
                          @RequestBody ColRequest colRequest) {
        taskService.updateCol(id, colRequest);
    }
}
