package ru.shutov.itone.tasktracker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;
import ru.shutov.itone.tasktracker.dto.get.UserDto;
import ru.shutov.itone.tasktracker.dto.post.UserPostDto;
import ru.shutov.itone.tasktracker.entity.User;
import ru.shutov.itone.tasktracker.exception.BusinessException;
import ru.shutov.itone.tasktracker.mapper.UserMapper;
import ru.shutov.itone.tasktracker.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Test
    void findAll_shouldReturnAllUserDtos() {
        User user1 = new User();
        user1.setUsername("annieloredo");
        user1.setFirstName("Annie");
        user1.setLastName("Loredo");
        User user2 = new User();
        user2.setUsername("kenniest");
        user2.setFirstName("Kenny");
        user2.setLastName("Stern");
        List<User> users = List.of(user1, user2);

        UserDto annie = new UserDto();
        annie.setFirstName(users.get(0).getFirstName());
        annie.setLastName(users.get(0).getLastName());
        annie.setUsername(users.get(0).getUsername());

        UserDto kenny = new UserDto();
        kenny.setFirstName(users.get(1).getFirstName());
        kenny.setLastName(users.get(1).getLastName());
        kenny.setUsername(users.get(1).getUsername());

        List<UserDto> userDtos = List.of(annie, kenny);

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDto(users)).thenReturn(userDtos);

        List<UserDto> actual = userService.findAll();

        assertNotNull(actual);
        assertIterableEquals(userDtos, actual);
        assertEquals(2, actual.size());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnUserDto() {
        UUID id = UUID.randomUUID();
        User user = new User();
        UserDto dto = new UserDto();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(dto);

        userService.findById(id);

        verify(userRepository, times(1)).findById(id);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void findById_shouldThrowBusinessException_UserNotFound() {
        UUID id = UUID.randomUUID();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        BusinessException exception  = assertThrows(BusinessException.class,
                () -> userService.findById(id));
        assertEquals(Level.DEBUG, exception.getEventInfo().getLevel());
        assertEquals(HttpStatus.NOT_FOUND, exception.getEventInfo().getStatus());

        verify(userRepository, times(1)).findById(id);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(userMapper);
    }

    @Test
    void create_shouldCreateUser() {
        UserPostDto dto = new UserPostDto();
        dto.setUsername("Tony");
        User user = new User();

        when(userRepository.findByUsername(dto.getUsername())).thenReturn(Optional.empty());
        when(userMapper.toModel(dto)).thenReturn(user);

        userService.create(dto);

        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).findByUsername(dto.getUsername());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void create_shouldThrowBusinessException_UsernameAlreadyExists() {
        UserPostDto dto = new UserPostDto();
        dto.setUsername("Tony");
        User user = new User();
        user.setUsername("Tony");

        when(userRepository.findByUsername(dto.getUsername())).thenReturn(Optional.of(user));

        BusinessException exception  = assertThrows(BusinessException.class,
                () -> userService.create(dto));
        assertEquals(Level.DEBUG, exception.getEventInfo().getLevel());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getEventInfo().getStatus());

        verify(userRepository, times(1)).findByUsername(dto.getUsername());
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(userMapper);
    }

    @Test
    void delete_shouldDeleteComment() {
        UUID id = UUID.randomUUID();

        userService.deleteById(id);

        verify(userRepository, times(1)).deleteById(id);
        verifyNoMoreInteractions(userRepository);
    }
}
