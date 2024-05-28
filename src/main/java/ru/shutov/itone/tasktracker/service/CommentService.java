package ru.shutov.itone.tasktracker.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.shutov.itone.tasktracker.dto.patch.CommentPatchDto;
import ru.shutov.itone.tasktracker.dto.post.CommentPostDto;
import ru.shutov.itone.tasktracker.entity.Comment;
import ru.shutov.itone.tasktracker.entity.Task;
import ru.shutov.itone.tasktracker.entity.User;
import ru.shutov.itone.tasktracker.exception.BusinessException;
import ru.shutov.itone.tasktracker.exception.event.EventInfoImpl;
import ru.shutov.itone.tasktracker.mapper.CommentMapper;
import ru.shutov.itone.tasktracker.repository.CommentRepository;
import ru.shutov.itone.tasktracker.repository.TaskRepository;
import ru.shutov.itone.tasktracker.security.UserDetailsImpl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Transactional
    public void post(UUID taskId, CommentPostDto commentPostDto) {
        Comment comment = commentMapper.toModel(commentPostDto);
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new BusinessException(EventInfoImpl.NOT_FOUND, "Task with id: " + taskId + " not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();

        comment.setAuthor(user);
        comment.setTask(task);
        comment.setCreatedAt(Timestamp.from(Instant.now()));
        commentRepository.save(comment);

        task.setLastUpdate(Timestamp.from(Instant.now()));
        taskRepository.save(task);
    }

    @Transactional
    public void update(UUID id, CommentPatchDto commentPatchDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new BusinessException(EventInfoImpl.NOT_FOUND, "Comment with id " + id + " not found"));
        comment.setText(commentPatchDto.getText());
        commentRepository.save(comment);
    }

    @Transactional
    public void delete(UUID id) {
        commentRepository.deleteById(id);
    }
}
