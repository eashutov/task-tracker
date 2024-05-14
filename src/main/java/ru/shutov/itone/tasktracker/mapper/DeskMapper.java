package ru.shutov.itone.tasktracker.mapper;

import org.mapstruct.Mapper;
import ru.shutov.itone.tasktracker.dto.get.DeskDto;
import ru.shutov.itone.tasktracker.dto.post.DeskPostDto;
import ru.shutov.itone.tasktracker.dto.get.CompleteDeskDto;
import ru.shutov.itone.tasktracker.entity.Desk;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ColMapper.class, UserMapper.class})
public interface DeskMapper {
    DeskDto toDto(Desk desk);

    List<DeskDto> toDto(List<Desk> desks);

    CompleteDeskDto toCompleteDto(Desk desk);

    Desk toModel(DeskPostDto deskPostDto);
}
