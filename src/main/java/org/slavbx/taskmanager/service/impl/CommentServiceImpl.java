package org.slavbx.taskmanager.service.impl;

import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.dto.ResponseDTO;
import org.slavbx.taskmanager.exception.NotFoundException;
import org.slavbx.taskmanager.model.Comment;
import org.slavbx.taskmanager.repository.CommentRepository;
import org.slavbx.taskmanager.service.CommentService;
import org.slavbx.taskmanager.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * Сервис для управления комментариями.
 * Предоставляет функционал для работы с комментариями, включая
 * получение комментариев с учетом пагинации и фильтрации, создание и удаление
 * комментариев
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Comment> getCommentsWithPagingAndFiltering(Specification<Comment> spec, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return commentRepository.findAll(spec, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Comment getCommentById(Long id) throws NotFoundException {
        return commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment not found with id: " + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDTO createComment(Comment comment) {
        comment.setAuthor(userService.getCurrentUser());
        Comment savedComment = commentRepository.save(comment);
        return new ResponseDTO(savedComment.getId(), "Comment was created");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDTO delete(Long id) {
        commentRepository.deleteById(id);
        return new ResponseDTO(id, "Comment was deleted");
    }
}