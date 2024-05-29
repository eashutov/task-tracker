package ru.shutov.itone.tasktracker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;
import ru.shutov.itone.tasktracker.dto.post.ColPostDto;
import ru.shutov.itone.tasktracker.dto.request.ColNameRequest;
import ru.shutov.itone.tasktracker.dto.request.ColPositionRequest;
import ru.shutov.itone.tasktracker.entity.Col;
import ru.shutov.itone.tasktracker.entity.Desk;
import ru.shutov.itone.tasktracker.exception.BusinessException;
import ru.shutov.itone.tasktracker.mapper.ColMapper;
import ru.shutov.itone.tasktracker.repository.ColRepository;
import ru.shutov.itone.tasktracker.repository.DeskRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ColServiceTest {
    @InjectMocks
    ColService colService;

    @Mock
    ColRepository colRepository;

    @Mock
    ColMapper colMapper;

    @Mock
    DeskRepository deskRepository;

    @Test
    void create_shouldCreateNewCol() {
        UUID deskId = UUID.randomUUID();
        ColPostDto dto = new ColPostDto();
        Col col = new Col();
        Desk desk = new Desk();

        when(colMapper.toModel(dto)).thenReturn(col);
        when(deskRepository.getReferenceById(deskId)).thenReturn(desk);
        when(colRepository.findMaxPositionByDeskId(desk)).thenReturn(3);

        colService.create(deskId, dto);

        verify(colRepository, times(1)).save(col);
        verify(deskRepository, times(1)).getReferenceById(deskId);
        verifyNoMoreInteractions(colRepository);
        verifyNoMoreInteractions(deskRepository);
    }

    @Test
    void updateName_shouldUpdateName() {
        UUID id = UUID.randomUUID();
        Col col = new Col();
        ColNameRequest request = new ColNameRequest("Name");

        when(colRepository.findById(id)).thenReturn(Optional.of(col));

        colService.updateName(id, request);

        verify(colRepository, times(1)).findById(id);
        verifyNoMoreInteractions(colRepository);
    }

    @Test
    void updateName_shouldThrowBusinessException_ColNotFound() {
        UUID id = UUID.randomUUID();
        ColNameRequest request = new ColNameRequest("Name");

        when(colRepository.findById(id)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> colService.updateName(id, request));
        assertEquals(Level.DEBUG, exception.getEventInfo().getLevel());
        assertEquals(HttpStatus.NOT_FOUND, exception.getEventInfo().getStatus());

        verify(colRepository, times(1)).findById(id);
        verifyNoMoreInteractions(colRepository);
    }

    @Test
    void delete_shouldDeleteCol() {
        UUID id = UUID.randomUUID();

        colService.delete(id);

        verify(colRepository, times(1)).deleteById(id);
        verifyNoMoreInteractions(colRepository);
    }

    @Test
    void updatePosition_shouldUpdateColPositions() {
        UUID id = UUID.randomUUID();
        int position = 1;
        ColPositionRequest request = new ColPositionRequest(position);
        Desk desk = new Desk();
        Col col = new Col();
        col.setPosition(0);
        col.setDesk(desk);
        Col colWithPosition = new Col();
        colWithPosition.setPosition(position);

        when(colRepository.findById(id)).thenReturn(Optional.of(col));
        when(colRepository.findByDeskOrderByPositionAsc(desk)).thenReturn(List.of(col, colWithPosition));

        colService.updatePosition(id, request);

        verify(colRepository, times(1)).findById(id);
        verify(colRepository, times(1)).findByDeskOrderByPositionAsc(desk);
        verifyNoMoreInteractions(colRepository);
    }

    @Test
    void updatePosition_shouldThrowBusinessException_ColNotFound() {
        UUID id = UUID.randomUUID();
        ColPositionRequest request = new ColPositionRequest(2);

        when(colRepository.findById(id)).thenReturn(Optional.empty());

        BusinessException exception  = assertThrows(BusinessException.class,
                () -> colService.updatePosition(id, request));
        assertEquals(Level.DEBUG, exception.getEventInfo().getLevel());
        assertEquals(HttpStatus.NOT_FOUND, exception.getEventInfo().getStatus());

        verify(colRepository, times(1)).findById(id);
        verifyNoMoreInteractions(colRepository);
    }
}
