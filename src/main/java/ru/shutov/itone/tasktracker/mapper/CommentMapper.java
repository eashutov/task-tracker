package ru.shutov.itone.tasktracker.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.shutov.itone.tasktracker.dto.get.CommentDto;
import ru.shutov.itone.tasktracker.dto.post.CommentPostDto;
import ru.shutov.itone.tasktracker.entity.Comment;
import ru.shutov.itone.tasktracker.entity.User;
import ru.shutov.itone.tasktracker.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring",
        config = UnmappedPolicyMapperConfig.class)
public abstract class CommentMapper {

    @Autowired
    protected UserRepository userRepository;

    public abstract Comment toModel(CommentDto commentDto);

    public abstract CommentDto toDto(Comment comment);

    public abstract List<Comment> toModel(List<CommentDto> commentDtos);

    public abstract List<CommentDto> toDto(List<Comment> comments);

    public abstract Comment toModel(CommentPostDto commentPostDto);

    public User mapUser(UUID id) {
        if (id == null) {
            return null;
        }
        return userRepository.getReferenceById(id);
    }
}
