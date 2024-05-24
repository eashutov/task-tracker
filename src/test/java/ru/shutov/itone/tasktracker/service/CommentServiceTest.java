package ru.shutov.itone.tasktracker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;
import ru.shutov.itone.tasktracker.dto.patch.CommentPatchDto;
import ru.shutov.itone.tasktracker.dto.post.CommentPostDto;
import ru.shutov.itone.tasktracker.entity.Comment;
import ru.shutov.itone.tasktracker.entity.Task;
import ru.shutov.itone.tasktracker.exception.BusinessException;
import ru.shutov.itone.tasktracker.mapper.CommentMapper;
import ru.shutov.itone.tasktracker.repository.CommentRepository;
import ru.shutov.itone.tasktracker.repository.TaskRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    CommentService commentService;

    @Mock
    TaskRepository taskRepository;

    @Mock
    CommentRepository commentRepository;

    @Mock
    CommentMapper commentMapper;

    @Test
    void post_shouldCreateComment() {
        UUID taskId = UUID.randomUUID();
        Task task = new Task();
        CommentPostDto commentPostDto = new CommentPostDto();
        Comment comment = new Comment();
        when(commentMapper.toModel(commentPostDto)).thenReturn(comment);
        when(taskRepository.getReferenceById(taskId)).thenReturn(task);

        commentService.post(taskId, commentPostDto);

        verify(commentRepository, times(1)).save(comment);
        verifyNoMoreInteractions(commentRepository);
    }

    @Test
    void update_shouldUpdateComment() {
        UUID id = UUID.randomUUID();
        Comment comment = new Comment();
        CommentPatchDto patch = new CommentPatchDto();
        patch.setText("Next text");
        when(commentRepository.findById(id)).thenReturn(Optional.of(comment));

        commentService.update(id, patch);

        assertNotNull(comment.getText());
        verify(commentRepository, times(1)).save(comment);
        verifyNoMoreInteractions(commentRepository);
    }

    @Test
    void update_shouldThrowBusinessException() {
        UUID id = UUID.randomUUID();
        CommentPatchDto patch = new CommentPatchDto();

        when(commentRepository.findById(id)).thenReturn(Optional.empty());

        BusinessException exception  = assertThrows(BusinessException.class,
                () -> commentService.update(id, patch));
        assertEquals(Level.DEBUG, exception.getEventInfo().getLevel());
        assertEquals(HttpStatus.NOT_FOUND, exception.getEventInfo().getStatus());
        verifyNoMoreInteractions(commentRepository);
    }

    @Test
    void delete_shouldDeleteComment() {
        UUID id = UUID.randomUUID();

        commentService.delete(id);

        verify(commentRepository, times(1)).deleteById(id);
        verifyNoMoreInteractions(commentRepository);
    }

}
