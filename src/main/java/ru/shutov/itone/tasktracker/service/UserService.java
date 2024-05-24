package ru.shutov.itone.tasktracker.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shutov.itone.tasktracker.dto.get.UserDto;
import ru.shutov.itone.tasktracker.dto.post.UserPostDto;
import ru.shutov.itone.tasktracker.entity.User;
import ru.shutov.itone.tasktracker.exception.BusinessException;
import ru.shutov.itone.tasktracker.exception.event.EventInfoImpl;
import ru.shutov.itone.tasktracker.mapper.UserMapper;
import ru.shutov.itone.tasktracker.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> findAll() {
        return userMapper.toDto(userRepository.findAll());
    }

    public UserDto findById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new BusinessException(EventInfoImpl.NOT_FOUND, "User with id " + id + " not found"));
        return userMapper.toDto(user);
    }

    @Transactional
    public void create(UserPostDto userPostDto) {
        if (isUserExists(userPostDto.getUsername())) {
            throw new BusinessException(EventInfoImpl.ALREADY_EXISTS,
                    "User with username " + userPostDto.getUsername() + " already exists");
        }
        User user = userMapper.toModel(userPostDto);
        userRepository.save(user);
    }

    @Transactional
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    private boolean isUserExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
