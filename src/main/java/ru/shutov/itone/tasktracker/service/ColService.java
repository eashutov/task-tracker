package ru.shutov.itone.tasktracker.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shutov.itone.tasktracker.dto.get.ShortColDto;
import ru.shutov.itone.tasktracker.dto.post.ColPostDto;
import ru.shutov.itone.tasktracker.dto.request.ColNameRequest;
import ru.shutov.itone.tasktracker.dto.request.ColPositionRequest;
import ru.shutov.itone.tasktracker.entity.Col;
import ru.shutov.itone.tasktracker.entity.Desk;
import ru.shutov.itone.tasktracker.exception.BusinessException;
import ru.shutov.itone.tasktracker.exception.event.EventInfoImpl;
import ru.shutov.itone.tasktracker.mapper.ColMapper;
import ru.shutov.itone.tasktracker.repository.ColRepository;
import ru.shutov.itone.tasktracker.repository.DeskRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ColService {
    private final ColRepository colRepository;
    private final ColMapper colMapper;
    private final DeskRepository deskRepository;

    public List<ShortColDto> findByDesk(UUID deskId) {
        return colMapper.toShortColDto(colRepository.findByDeskId(deskId));
    }

    @Transactional
    public void delete(UUID colId) {
        colRepository.deleteById(colId);
    }

    @Transactional
    public void updatePosition(UUID id, ColPositionRequest colPositionRequest) {
        Integer position = colPositionRequest.position();
        Col col = colRepository.findById(id).orElseThrow(() ->
                new BusinessException(EventInfoImpl.NOT_FOUND, "Col with id " + id + " not found"));
        Desk desk = col.getDesk();

        List<Col> colsOrderedByPosition = colRepository.findByDeskOrderByPositionAsc(desk);
        if (col.getPosition() > position) {
            colsOrderedByPosition.stream()
                    .skip(position)
                    .limit(col.getPosition())
                    .forEach((column) -> column.setPosition(column.getPosition() + 1));
        } else {
            colsOrderedByPosition.stream()
                    .skip(col.getPosition())
                    .limit(position)
                    .forEach((column) -> column.setPosition(column.getPosition() - 1));
        }
        col.setPosition(position);
    }

    @Transactional
    public void updateName(UUID id, ColNameRequest colNameRequest) {
        Col col = colRepository.findById(id).orElseThrow(() ->
                new BusinessException(EventInfoImpl.NOT_FOUND, "Col with id " + id + " not found"));
        col.setName(colNameRequest.name());
    }

    @Transactional
    public void create(UUID id, ColPostDto colPostDto) {
        Col col = colMapper.toModel(colPostDto);
        Desk deskProxy = deskRepository.getReferenceById(id);
        Integer maxPosition = colRepository.findMaxPositionByDeskId(deskProxy);
        col.setPosition(maxPosition + 1);
        col.setDesk(deskProxy);
        colRepository.save(col);
    }
}
