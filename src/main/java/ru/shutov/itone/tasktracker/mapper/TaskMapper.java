package ru.shutov.itone.tasktracker.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.shutov.itone.tasktracker.dto.get.CompleteTaskDto;
import ru.shutov.itone.tasktracker.dto.get.TaskDto;
import ru.shutov.itone.tasktracker.dto.patch.TaskPatchDto;
import ru.shutov.itone.tasktracker.dto.post.TaskPostDto;
import ru.shutov.itone.tasktracker.entity.Col;
import ru.shutov.itone.tasktracker.entity.Task;
import ru.shutov.itone.tasktracker.repository.ColRepository;
import ru.shutov.itone.tasktracker.repository.TaskRepository;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring",
        uses = {UserMapper.class, CommentMapper.class},
        config = UnmappedPolicyMapperConfig.class)
public abstract class TaskMapper {

    @Autowired
    protected ColRepository colRepository;

    @Autowired
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
        if (id == null) {
            return null;
        }
        return taskRepository.getReferenceById(id);
    }
}
