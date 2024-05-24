package ru.shutov.itone.tasktracker.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shutov.itone.tasktracker.dto.patch.CommentPatchDto;
import ru.shutov.itone.tasktracker.dto.post.CommentPostDto;
import ru.shutov.itone.tasktracker.entity.Comment;
import ru.shutov.itone.tasktracker.entity.Task;
import ru.shutov.itone.tasktracker.exception.BusinessException;
import ru.shutov.itone.tasktracker.exception.event.EventInfoImpl;
import ru.shutov.itone.tasktracker.mapper.CommentMapper;
import ru.shutov.itone.tasktracker.repository.CommentRepository;
import ru.shutov.itone.tasktracker.repository.TaskRepository;

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
        Task task = taskRepository.getReferenceById(taskId);

        comment.setTask(task);
        commentRepository.save(comment);
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
