package ru.shutov.itone.tasktracker.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import ru.shutov.itone.tasktracker.dto.get.CompleteDeskDto;
import ru.shutov.itone.tasktracker.dto.get.DeskDto;
import ru.shutov.itone.tasktracker.dto.post.DeskPostDto;
import ru.shutov.itone.tasktracker.entity.Desk;
import ru.shutov.itone.tasktracker.entity.User;
import ru.shutov.itone.tasktracker.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring",
        uses = {ColMapper.class, UserMapper.class},
        config = UnmappedPolicyMapperConfig.class)
@RequiredArgsConstructor
public abstract class DeskMapper {
    protected UserRepository userRepository;

    public abstract DeskDto toDto(Desk desk);

    public abstract List<DeskDto> toDto(List<Desk> desks);

    public abstract CompleteDeskDto toCompleteDto(Desk desk);

    public abstract Desk toModel(DeskPostDto deskPostDto);

    public User mapUser(UUID id) {
        return userRepository.getReferenceById(id);
    }
}
