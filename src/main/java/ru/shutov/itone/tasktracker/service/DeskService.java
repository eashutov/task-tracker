package ru.shutov.itone.tasktracker.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shutov.itone.tasktracker.dto.get.DeskDto;
import ru.shutov.itone.tasktracker.dto.post.ColPostDto;
import ru.shutov.itone.tasktracker.dto.post.DeskPostDto;
import ru.shutov.itone.tasktracker.dto.get.CompleteDeskDto;
import ru.shutov.itone.tasktracker.entity.Col;
import ru.shutov.itone.tasktracker.entity.Desk;
import ru.shutov.itone.tasktracker.exception.BusinessException;
import ru.shutov.itone.tasktracker.exception.event.DeskEvent;
import ru.shutov.itone.tasktracker.mapper.ColMapper;
import ru.shutov.itone.tasktracker.mapper.DeskMapper;
import ru.shutov.itone.tasktracker.repository.ColRepository;
import ru.shutov.itone.tasktracker.repository.DeskRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeskService {
    private final ColRepository colRepository;
    private final DeskRepository deskRepository;
    private final DeskMapper deskMapper;
    private final ColMapper colMapper;

    public List<DeskDto> findAll() {
        return deskMapper.toDto(deskRepository.findAll());
    }

    public CompleteDeskDto findById(UUID id) {
        Desk desk = deskRepository.findById(id).orElseThrow(() ->
                new BusinessException(DeskEvent.DESK_NOT_FOUND, "Desk with id " + id + " not found"));
        return deskMapper.toCompleteDto(desk);
    }

    @Transactional
    public void deleteById(UUID id) {
        deskRepository.deleteById(id);
    }

    @Transactional
    public void create(DeskPostDto deskPostDto) {
        Desk desk = deskMapper.toModel(deskPostDto);
        deskRepository.save(desk);
    }

    @Transactional
    public void createCol(UUID id, ColPostDto colPostDto) {
        Col col = colMapper.toModel(colPostDto);
        col.setDesk(deskRepository.getReferenceById(id));
        colRepository.save(col);
    }
}
