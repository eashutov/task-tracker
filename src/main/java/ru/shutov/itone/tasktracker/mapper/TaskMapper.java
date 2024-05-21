package ru.shutov.itone.tasktracker.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.shutov.itone.tasktracker.dto.get.CompleteTaskDto;
import ru.shutov.itone.tasktracker.dto.get.TaskDto;
import ru.shutov.itone.tasktracker.dto.patch.TaskPatchDto;
import ru.shutov.itone.tasktracker.dto.post.TaskPostDto;
import ru.shutov.itone.tasktracker.entity.Col;
import ru.shutov.itone.tasktracker.entity.Task;
import ru.shutov.itone.tasktracker.entity.User;
import ru.shutov.itone.tasktracker.repository.ColRepository;
import ru.shutov.itone.tasktracker.repository.TaskRepository;
import ru.shutov.itone.tasktracker.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring",
        uses = {UserMapper.class, CommentMapper.class},
        config = UnmappedPolicyMapperConfig.class)
@RequiredArgsConstructor
public abstract class TaskMapper {
    protected ColRepository colRepository;
    protected UserRepository userRepository;
    protected TaskRepository taskRepository;

    public abstract TaskDto toDto(Task task);

    public abstract List<TaskDto> toDto(List<Task> tasks);

    public abstract CompleteTaskDto toCompleteDto(Task task);

    public abstract Task toModel(TaskPostDto taskPostDto);

    public abstract Task toModel(TaskPatchDto taskPatchDto);

    public Col mapCol(UUID id) {
        return colRepository.getReferenceById(id);
    }

    public Task mapTask(UUID id) {
        return taskRepository.getReferenceById(id);
    }
}
