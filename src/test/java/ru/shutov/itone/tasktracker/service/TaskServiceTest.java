package ru.shutov.itone.tasktracker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.event.Level;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import ru.shutov.itone.tasktracker.dto.get.CompleteTaskDto;
import ru.shutov.itone.tasktracker.dto.get.TaskDto;
import ru.shutov.itone.tasktracker.dto.patch.TaskPatchDto;
import ru.shutov.itone.tasktracker.dto.post.TaskPostDto;
import ru.shutov.itone.tasktracker.dto.request.AssigneeRequest;
import ru.shutov.itone.tasktracker.dto.request.ColRequest;
import ru.shutov.itone.tasktracker.dto.request.StatusRequest;
import ru.shutov.itone.tasktracker.dto.request.TaskRequest;
import ru.shutov.itone.tasktracker.entity.Col;
import ru.shutov.itone.tasktracker.entity.Task;
import ru.shutov.itone.tasktracker.entity.User;
import ru.shutov.itone.tasktracker.enums.Priority;
import ru.shutov.itone.tasktracker.enums.Status;
import ru.shutov.itone.tasktracker.enums.TaskType;
import ru.shutov.itone.tasktracker.exception.BusinessException;
import ru.shutov.itone.tasktracker.mapper.TaskMapper;
import ru.shutov.itone.tasktracker.repository.ColRepository;
import ru.shutov.itone.tasktracker.repository.TaskRepository;
import ru.shutov.itone.tasktracker.repository.UserRepository;
import ru.shutov.itone.tasktracker.repository.specification.TaskSpecification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @InjectMocks
    TaskService taskService;

    @Mock
    ColRepository colRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    TaskRepository taskRepository;

    @Mock
    TaskMapper taskMapper;

    @Test
    void findById_shouldReturnCompleteDto() {
        UUID id = UUID.randomUUID();
        CompleteTaskDto dto = new CompleteTaskDto();
        dto.setId(id);
        Task task = new Task();
        task.setId(id);

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        when(taskMapper.toCompleteDto(task)).thenReturn(dto);

        taskService.findById(id);

        verify(taskRepository, times(1)).findById(id);
        verify(taskMapper, times(1)).toCompleteDto(task);
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void findById_shouldThrowBusinessException_TaskNotFound() {
        UUID id = UUID.randomUUID();

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> taskService.findById(id));
        assertEquals(Level.DEBUG, exception.getEventInfo().getLevel());
        assertEquals(HttpStatus.NOT_FOUND, exception.getEventInfo().getStatus());

        verify(taskRepository, times(1)).findById(id);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(taskMapper);
    }

    @Test
    void findByRequest_shouldReturnTasksWithSpecification() {
        List<Task> tasks = List.of(
                Task.builder()
                        .taskType(TaskType.TASK)
                        .priority(Priority.CRITICAL)
                        .build()
        );
        TaskDto dto = new TaskDto();
        dto.setTaskType(TaskType.TASK);
        dto.setPriority(Priority.CRITICAL);

        List<TaskDto> expected = List.of(dto);
        TaskRequest request = TaskRequest.builder()
                .taskType(TaskType.TASK)
                .priority(Priority.CRITICAL)
                .build();

        Specification<Task> spec = TaskSpecification.specificationFor(request);
        try (MockedStatic<TaskSpecification> mockedStatic = mockStatic(TaskSpecification.class)) {
            mockedStatic.when(() -> TaskSpecification.specificationFor(any(TaskRequest.class)))
                    .thenReturn(spec);

            when(taskRepository.findAll(spec)).thenReturn(tasks);
            when(taskMapper.toDto(tasks)).thenReturn(expected);

            List<TaskDto> actual = taskService.findByRequest(request);

            assertNotNull(actual);
            assertIterableEquals(expected, actual);
            assertEquals(1, actual.size());

            verify(taskRepository, times(1)).findAll(spec);
            verify(taskMapper, times(1)).toDto(tasks);
            verifyNoMoreInteractions(taskRepository);
        }
    }

    @Test
    void findByRequest_shouldReturnNothing_NoSuitableEntityFoundByTaskRequest() {
        List<Task> tasks = Collections.emptyList();
        List<TaskDto> expected = Collections.emptyList();
        TaskRequest request = TaskRequest.builder()
                .taskType(TaskType.EPIC)
                .priority(Priority.MAJOR)
                .build();

        Specification<Task> spec = TaskSpecification.specificationFor(request);
        try (MockedStatic<TaskSpecification> mockedStatic = mockStatic(TaskSpecification.class)) {
            mockedStatic.when(() -> TaskSpecification.specificationFor(any(TaskRequest.class)))
                    .thenReturn(spec);

            when(taskRepository.findAll(spec)).thenReturn(tasks);
            when(taskMapper.toDto(tasks)).thenReturn(expected);

            List<TaskDto> actual = taskService.findByRequest(request);

            assertNotNull(actual);
            assertIterableEquals(expected, actual);
            assertEquals(0, actual.size());

            verify(taskRepository, times(1)).findAll(spec);
            verify(taskMapper, times(1)).toDto(tasks);
            verifyNoMoreInteractions(taskRepository);
        }
    }

    @Test
    void findByRequest_shouldReturnAllTasks_TaskRequestFieldsIsNull() {
        List<Task> tasks = List.of(
                Task.builder()
                        .taskType(TaskType.TASK)
                        .priority(Priority.CRITICAL)
                        .build(),
                Task.builder()
                        .taskType(TaskType.BUG)
                        .priority(Priority.BLOCKER)
                        .build(),
                Task.builder()
                        .taskType(TaskType.EPIC)
                        .priority(Priority.MAJOR)
                        .build()
        );
        TaskDto task = new TaskDto();
        task.setTaskType(TaskType.TASK);
        task.setPriority(Priority.CRITICAL);

        TaskDto bug = new TaskDto();
        bug.setTaskType(TaskType.BUG);
        bug.setPriority(Priority.BLOCKER);

        TaskDto epic = new TaskDto();
        epic.setTaskType(TaskType.EPIC);
        epic.setPriority(Priority.MAJOR);

        List<TaskDto> expected = List.of(task, bug, epic);
        TaskRequest request = TaskRequest.builder().build();

        Specification<Task> spec = TaskSpecification.specificationFor(request);
        try (MockedStatic<TaskSpecification> mockedStatic = mockStatic(TaskSpecification.class)) {
            mockedStatic.when(() -> TaskSpecification.specificationFor(any(TaskRequest.class)))
                    .thenReturn(spec);

            when(taskRepository.findAll(spec)).thenReturn(tasks);
            when(taskMapper.toDto(tasks)).thenReturn(expected);

            List<TaskDto> actual = taskService.findByRequest(request);

            assertNotNull(actual);
            assertIterableEquals(expected, actual);
            assertEquals(3, actual.size());

            verify(taskRepository, times(1)).findAll(spec);
            verify(taskMapper, times(1)).toDto(tasks);
            verifyNoMoreInteractions(taskRepository);
        }
    }

    @Test
    void create_shouldCreateTask() {
        TaskPostDto dto = new TaskPostDto();
        Task task = new Task();

        when(taskMapper.toModel(dto)).thenReturn(task);

        taskService.create(dto);

        verify(taskRepository, times(1)).save(task);
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void delete_shouldDeleteTask() {
        UUID id = UUID.randomUUID();

        taskService.deleteById(id);

        verify(taskRepository, times(1)).deleteById(id);
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void update_shouldUpdateTask() {
        UUID id = UUID.randomUUID();
        TaskPatchDto dto = new TaskPatchDto();
        Task task = new Task();

        when(taskMapper.toModel(dto)).thenReturn(task);

        taskService.update(id, dto);

        verify(taskMapper, times(1)).toModel(dto);
        verify(taskRepository, times(1)).save(task);
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void updateAssignee_shouldUpdateAssigneeByRequest() {
        UUID id = UUID.randomUUID();
        UUID assigneeId = UUID.randomUUID();
        User assignee = new User();
        Task task = new Task();
        AssigneeRequest request = new AssigneeRequest(assigneeId);

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        when(userRepository.getReferenceById(assigneeId)).thenReturn(assignee);

        taskService.updateAssignee(id, request);

        verify(taskRepository, times(1)).findById(id);
        verify(userRepository, times(1)).getReferenceById(assigneeId);
        verifyNoMoreInteractions(taskRepository);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void updateAssignee_shouldThrowBusinessException_TaskByIdNotFound() {
        UUID id = UUID.randomUUID();
        UUID assigneeId = UUID.randomUUID();
        AssigneeRequest request = new AssigneeRequest(assigneeId);

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () ->
                taskService.updateAssignee(id, request));

        assertEquals(Level.DEBUG, exception.getEventInfo().getLevel());
        assertEquals(HttpStatus.NOT_FOUND, exception.getEventInfo().getStatus());

        verify(taskRepository, times(1)).findById(id);
        verifyNoMoreInteractions(taskRepository);
        verifyNoInteractions(userRepository);
    }

    @Test
    void updateStatus_shouldUpdateStatusByRequest() {
        UUID id = UUID.randomUUID();
        Task task = new Task();
        StatusRequest request = new StatusRequest(Status.OPENED);

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));

        taskService.updateStatus(id, request);

        verify(taskRepository, times(1)).findById(id);
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void updateStatus_shouldThrowBusinessException_TaskByIdNotFound() {
        UUID id = UUID.randomUUID();
        StatusRequest request = new StatusRequest(Status.OPENED);

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () ->
                taskService.updateStatus(id, request));

        assertEquals(Level.DEBUG, exception.getEventInfo().getLevel());
        assertEquals(HttpStatus.NOT_FOUND, exception.getEventInfo().getStatus());

        verify(taskRepository, times(1)).findById(id);
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void updateCol_shouldUpdateColByRequest() {
        UUID id = UUID.randomUUID();
        UUID colId = UUID.randomUUID();
        Col col = new Col();
        Task task = new Task();
        ColRequest request = new ColRequest(colId);

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        when(colRepository.getReferenceById(colId)).thenReturn(col);

        taskService.updateCol(id, request);

        verify(taskRepository, times(1)).findById(id);
        verify(colRepository, times(1)).getReferenceById(colId);
        verifyNoMoreInteractions(taskRepository);
        verifyNoMoreInteractions(colRepository);
    }

    @Test
    void updateCol_shouldThrowBusinessException_TaskByIdNotFound() {
        UUID id = UUID.randomUUID();
        UUID colId = UUID.randomUUID();
        ColRequest request = new ColRequest(colId);

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () ->
                taskService.updateCol(id, request));

        assertEquals(Level.DEBUG, exception.getEventInfo().getLevel());
        assertEquals(HttpStatus.NOT_FOUND, exception.getEventInfo().getStatus());

        verify(taskRepository, times(1)).findById(id);
        verifyNoMoreInteractions(taskRepository);
        verifyNoInteractions(colRepository);
    }
}
