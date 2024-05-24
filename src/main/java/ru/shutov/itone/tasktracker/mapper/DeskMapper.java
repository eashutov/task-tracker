package ru.shutov.itone.tasktracker.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public abstract class DeskMapper {

    @Autowired
    protected UserRepository userRepository;

    public abstract DeskDto toDto(Desk desk);

    public abstract List<DeskDto> toDto(List<Desk> desks);

    public abstract CompleteDeskDto toCompleteDto(Desk desk);

    public abstract Desk toModel(DeskPostDto deskPostDto);

    public User mapUser(UUID id) {
        if (id == null) {
            return null;
        }
        return userRepository.getReferenceById(id);
    }
}
