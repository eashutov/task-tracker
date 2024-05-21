package ru.shutov.itone.tasktracker.mapper;

import org.mapstruct.Mapper;
import ru.shutov.itone.tasktracker.dto.get.ColDto;
import ru.shutov.itone.tasktracker.dto.post.ColPostDto;
import ru.shutov.itone.tasktracker.entity.Col;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {TaskMapper.class},
        config = UnmappedPolicyMapperConfig.class)
public interface ColMapper {
    Col toModel(ColDto colDto);

    ColDto toDto(Col col);

    List<Col> toModel(List<ColDto> colDtos);

    List<ColDto> toDto(List<Col> cols);

    Col toModel(ColPostDto colPostDto);
}
