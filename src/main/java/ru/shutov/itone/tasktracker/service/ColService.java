package ru.shutov.itone.tasktracker.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shutov.itone.tasktracker.dto.request.ColNameRequest;
import ru.shutov.itone.tasktracker.dto.request.ColPositionRequest;
import ru.shutov.itone.tasktracker.entity.Col;
import ru.shutov.itone.tasktracker.entity.Desk;
import ru.shutov.itone.tasktracker.exception.BusinessException;
import ru.shutov.itone.tasktracker.exception.event.ColEvent;
import ru.shutov.itone.tasktracker.repository.ColRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ColService {
    private final ColRepository colRepository;

    @Transactional
    public void deleteCol(UUID colId) {
        colRepository.deleteById(colId);
    }

    @Transactional
    public void updatePosition(UUID id, ColPositionRequest colPositionRequest) {
        Integer position = colPositionRequest.position();
        Col col = colRepository.findById(id).orElseThrow(() ->
                new BusinessException(ColEvent.COL_NOT_FOUND, "Col with id " + id + " not found"));
        Desk desk = col.getDesk();
        desk.getCols()
                .stream()
                .filter((c) -> c.getPosition().equals(position))
                .findAny()
                .ifPresent((c) -> c.setPosition(col.getPosition()));
        col.setPosition(position);
    }

    @Transactional
    public void updateName(UUID id, ColNameRequest colNameRequest) {
        Col col = colRepository.findById(id).orElseThrow(() ->
                new BusinessException(ColEvent.COL_NOT_FOUND, "Col with id " + id + " not found"));
        col.setName(colNameRequest.name());
    }
}
