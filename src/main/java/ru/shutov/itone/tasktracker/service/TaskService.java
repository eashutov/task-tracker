package ru.shutov.itone.tasktracker.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shutov.itone.tasktracker.dto.get.CompleteTaskDto;
import ru.shutov.itone.tasktracker.dto.get.TaskDto;
import ru.shutov.itone.tasktracker.dto.patch.TaskPatchDto;
import ru.shutov.itone.tasktracker.dto.post.TaskPostDto;
import ru.shutov.itone.tasktracker.dto.request.AssigneeRequest;
import ru.shutov.itone.tasktracker.dto.request.ColRequest;
import ru.shutov.itone.tasktracker.dto.request.StatusRequest;
import ru.shutov.itone.tasktracker.dto.request.TaskRequest;
import ru.shutov.itone.tasktracker.entity.Task;
import ru.shutov.itone.tasktracker.exception.BusinessException;
import ru.shutov.itone.tasktracker.exception.event.TaskEvent;
import ru.shutov.itone.tasktracker.mapper.TaskMapper;
import ru.shutov.itone.tasktracker.repository.ColRepository;
import ru.shutov.itone.tasktracker.repository.TaskRepository;
import ru.shutov.itone.tasktracker.repository.UserRepository;
import ru.shutov.itone.tasktracker.repository.specification.TaskSpecification;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final ColRepository colRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public CompleteTaskDto findById(UUID id) {
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new BusinessException(TaskEvent.TASK_NOT_FOUND, "Task with id " + id + " not found"));
        return taskMapper.toCompleteDto(task);
    }

    public List<TaskDto> findByRequest(TaskRequest taskRequest) {
        List<Task> tasks = taskRepository.findAll(TaskSpecification.specificationFor(taskRequest));
        return taskMapper.toDto(tasks);
    }

    @Transactional
    public void create(TaskPostDto taskPostDto) {
        Task task = taskMapper.toModel(taskPostDto);
        taskRepository.save(task);
    }

    @Transactional
    public void deleteById(UUID id) {
        taskRepository.deleteById(id);
    }

    @Transactional
    public void update(UUID id, TaskPatchDto taskPatchDto) {
        Task task = taskMapper.toModel(taskPatchDto);
        task.setId(id);
        taskRepository.save(task);
    }

    @Transactional
    public void updateAssignee(UUID id, AssigneeRequest assigneeRequest) {
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new BusinessException(TaskEvent.TASK_NOT_FOUND, "Task with id " + id + " not found"));
        task.setAssignee(userRepository.getReferenceById(assigneeRequest.assignee()));
    }

    @Transactional
    public void updateStatus(UUID id, StatusRequest statusRequest) {
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new BusinessException(TaskEvent.TASK_NOT_FOUND, "Task with id " + id + " not found"));
        task.setStatus(statusRequest.status());
    }

    @Transactional
    public void updateCol(UUID id, ColRequest colRequest) {
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new BusinessException(TaskEvent.TASK_NOT_FOUND, "Task with id " + id + " not found"));
        task.setCol(colRepository.getReferenceById(colRequest.col()));
    }
}
