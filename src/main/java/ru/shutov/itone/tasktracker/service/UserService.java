package ru.shutov.itone.tasktracker.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.shutov.itone.tasktracker.dto.get.UserDto;
import ru.shutov.itone.tasktracker.dto.post.AuthenticationDto;
import ru.shutov.itone.tasktracker.dto.post.UserPostDto;
import ru.shutov.itone.tasktracker.dto.response.JwtResponse;
import ru.shutov.itone.tasktracker.entity.User;
import ru.shutov.itone.tasktracker.exception.BusinessException;
import ru.shutov.itone.tasktracker.exception.event.EventInfoImpl;
import ru.shutov.itone.tasktracker.mapper.UserMapper;
import ru.shutov.itone.tasktracker.repository.UserRepository;
import ru.shutov.itone.tasktracker.security.JwtUtil;
import ru.shutov.itone.tasktracker.security.UserDetailsImpl;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final AuthenticationProvider authenticationProvider;

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
    public JwtResponse register(UserPostDto userPostDto) {
        if (isUserExists(userPostDto.getUsername())) {
            throw new BusinessException(EventInfoImpl.ALREADY_EXISTS,
                    "User with username " + userPostDto.getUsername() + " already exists");
        }
        User user = userMapper.toModel(userPostDto);
        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getUsername());
        return new JwtResponse(token);
    }

    public JwtResponse login(AuthenticationDto authenticationDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authenticationDto.getUsername(),
                        authenticationDto.getPassword());
        authenticationProvider.authenticate(authenticationToken);
        String token = jwtUtil.generateToken(authenticationDto.getUsername());
        return new JwtResponse(token);
    }

    @Transactional
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void delete() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
            userRepository.delete(user);
        } else {
            throw new AccessDeniedException("Not authenticated");
        }
    }

    private boolean isUserExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
