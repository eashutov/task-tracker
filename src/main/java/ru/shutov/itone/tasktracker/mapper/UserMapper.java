package ru.shutov.itone.tasktracker.mapper;

import org.mapstruct.Mapper;
import ru.shutov.itone.tasktracker.dto.get.UserDto;
import ru.shutov.itone.tasktracker.dto.post.UserPostDto;
import ru.shutov.itone.tasktracker.entity.User;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {CommentMapper.class},
        config = UnmappedPolicyMapperConfig.class)
public interface UserMapper {
    User toModel(UserDto userDto);

    UserDto toDto(User user);

    List<User> toModel(List<UserDto> userDtos);

    List<UserDto> toDto(List<User> users);

    User toModel(UserPostDto userPostDto);
}
