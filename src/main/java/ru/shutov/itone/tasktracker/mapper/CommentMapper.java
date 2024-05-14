package ru.shutov.itone.tasktracker.mapper;

import org.mapstruct.Mapper;
import ru.shutov.itone.tasktracker.dto.get.CommentDto;
import ru.shutov.itone.tasktracker.entity.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toModel(CommentDto commentDto);

    CommentDto toDto(Comment comment);

    List<Comment> toModel(List<CommentDto> commentDtos);

    List<CommentDto> toDto(List<Comment> comments);
}
