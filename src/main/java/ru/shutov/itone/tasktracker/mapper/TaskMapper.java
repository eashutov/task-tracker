package ru.shutov.itone.tasktracker.mapper;

import org.mapstruct.Mapper;
import ru.shutov.itone.tasktracker.dto.get.CompleteTaskDto;
import ru.shutov.itone.tasktracker.dto.get.TaskDto;
import ru.shutov.itone.tasktracker.dto.patch.TaskPatchDto;
import ru.shutov.itone.tasktracker.dto.post.TaskPostDto;
import ru.shutov.itone.tasktracker.entity.Task;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CommentMapper.class})
public interface TaskMapper {
    TaskDto toDto(Task task);

    List<TaskDto> toDto(List<Task> tasks);

    CompleteTaskDto toCompleteDto(Task task);

    Task toModel(TaskPostDto taskPostDto);

    Task toModel(TaskPatchDto taskPatchDto);
}
