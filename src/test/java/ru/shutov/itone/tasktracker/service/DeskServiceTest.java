package ru.shutov.itone.tasktracker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;
import ru.shutov.itone.tasktracker.dto.get.CompleteDeskDto;
import ru.shutov.itone.tasktracker.dto.get.DeskDto;
import ru.shutov.itone.tasktracker.dto.get.UserDto;
import ru.shutov.itone.tasktracker.dto.post.DeskPostDto;
import ru.shutov.itone.tasktracker.entity.Desk;
import ru.shutov.itone.tasktracker.entity.User;
import ru.shutov.itone.tasktracker.exception.BusinessException;
import ru.shutov.itone.tasktracker.mapper.DeskMapper;
import ru.shutov.itone.tasktracker.repository.DeskRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeskServiceTest {

    @InjectMocks
    DeskService deskService;

    @Mock
    DeskRepository deskRepository;

    @Mock
    DeskMapper deskMapper;

    @Test
    void findAll_shouldReturnAllDesks() {
        User kenny = new User();
        UserDto kennyDto = new UserDto();
        kennyDto.setUsername("kenny");
        User tony = new User();
        UserDto tonyDto = new UserDto();
        tonyDto.setUsername("tony");
        Desk desk1 = new Desk();
        desk1.setCreator(kenny);
        Desk desk2 = new Desk();
        desk2.setCreator(tony);
        List<Desk> desks = List.of(desk1, desk2);
        DeskDto deskDtoKenny = new DeskDto();
        deskDtoKenny.setCreator(kennyDto);
        DeskDto deskDtoTony = new DeskDto();
        deskDtoTony.setCreator(tonyDto);
        List<DeskDto> expected = List.of(deskDtoKenny, deskDtoTony);

        when(deskRepository.findAll()).thenReturn(desks);
        when(deskMapper.toDto(desks)).thenReturn(expected);

        List<DeskDto> actual = deskService.findAll();

        assertNotNull(actual);
        assertIterableEquals(expected, actual);
        assertEquals(2, actual.size());

        verify(deskRepository, times(1)).findAll();
        verifyNoMoreInteractions(deskRepository);
    }

    @Test
    void create_shouldCreateTask() {
        DeskPostDto dto = new DeskPostDto();
        Desk desk = new Desk();

        when(deskMapper.toModel(dto)).thenReturn(desk);

        deskService.create(dto);

        verify(deskRepository, times(1)).save(desk);
        verifyNoMoreInteractions(deskRepository);
    }

    @Test
    void delete_shouldDeleteTask() {
        UUID id = UUID.randomUUID();

        deskService.deleteById(id);

        verify(deskRepository, times(1)).deleteById(id);
        verifyNoMoreInteractions(deskRepository);
    }

    @Test
    void findById_shouldReturnCompleteDeskDto() {
        UUID id = UUID.randomUUID();
        Desk desk = new Desk();
        String deskName = "Desk name";
        CompleteDeskDto expected = new CompleteDeskDto();
        expected.setName(deskName);

        when(deskRepository.findById(id)).thenReturn(Optional.of(desk));
        when(deskMapper.toCompleteDto(desk)).thenReturn(expected);

        CompleteDeskDto actual = deskService.findById(id);
        assertEquals(expected.getName(), actual.getName());

        verify(deskRepository, times(1)).findById(id);
        verifyNoMoreInteractions(deskRepository);
    }

    @Test
    void findById_shouldThrowBusinessException_DeskNotFound() {
        UUID id = UUID.randomUUID();

        when(deskRepository.findById(id)).thenReturn(Optional.empty());

        BusinessException exception  = assertThrows(BusinessException.class,
                () -> deskService.findById(id));
        assertEquals(Level.DEBUG, exception.getEventInfo().getLevel());
        assertEquals(HttpStatus.NOT_FOUND, exception.getEventInfo().getStatus());

        verify(deskRepository, times(1)).findById(id);
        verifyNoMoreInteractions(deskRepository);
        verifyNoInteractions(deskMapper);
    }

}
