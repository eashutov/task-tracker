package ru.shutov.itone.tasktracker.controller;

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
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<TaskDto> findBy(@RequestBody TaskRequest taskRequest) {
        return taskService.findByRequest(taskRequest);
    }

    @GetMapping("/{id}")
    public CompleteTaskDto find(@PathVariable("id") UUID id) {
        return taskService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody TaskPostDto taskPostDto) {
        taskService.create(taskPostDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID id) {
        taskService.deleteById(id);
    }

    @PatchMapping("/{id}")
    public void update(@PathVariable("id") UUID id,
                       @RequestBody TaskPatchDto taskPatchDto) {
        taskService.update(id, taskPatchDto);
    }

    @PatchMapping("/{id}/assignee")
    public void updateAssignee(@PathVariable("id") UUID id,
                               @RequestBody AssigneeRequest assigneeRequest) {
        taskService.updateAssignee(id, assigneeRequest);
    }

    @PatchMapping("/{id}/status")
    public void updateStatus(@PathVariable("id") UUID id,
                             @RequestBody StatusRequest statusRequest) {
        taskService.updateStatus(id, statusRequest);
    }

    @PatchMapping("/{id}/col")
    public void updateCol(@PathVariable("id") UUID id,
                          @RequestBody ColRequest colRequest) {
        taskService.updateCol(id, colRequest);
    }
}
