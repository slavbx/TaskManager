package org.slavbx.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.dto.ResponseDTO;
import org.slavbx.taskmanager.exception.NotFoundException;
import org.slavbx.taskmanager.model.Comment;
import org.slavbx.taskmanager.repository.CommentRepository;
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
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;

    /**
     * Получение комментариев с учетом пагинации и фильтрации.
     *
     * @param spec       спецификация для фильтрации комментариев
     * @param pageNumber номер страницы для возврата
     * @param pageSize   размер страницы (количество комментариев)
     * @return объект Page с комментариями, соответствующими спецификации
     */
    public Page<Comment> getCommentsWithPagingAndFiltering(Specification<Comment> spec, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return commentRepository.findAll(spec, pageable);
    }

    /**
     * Получение комментария по уникальному идентификатору.
     *
     * @param id уникальный идентификатор комментария
     * @return объект Comment, соответствующий указанному идентификатору
     * @throws NotFoundException если комментарий не найден
     */
    public Comment getCommentById(Long id) throws NotFoundException {
        return commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment not found with id: " + id));
    }

    /**
     * Создание нового комментария.
     *
     * @param comment объект Comment, представляющий новый комментарий
     * @return объект ResponseDTO с идентификатором созданного комментария и соответствующим сообщением
     */
    public ResponseDTO createComment(Comment comment) {
        comment.setAuthor(userService.getCurrentUser());
        Comment savedComment = commentRepository.save(comment);
        return new ResponseDTO(savedComment.getId(), "Comment was created");
    }

    /**
     * Удаление комментария по уникальному идентификатору.
     *
     * @param id уникальный идентификатор комментария, который нужно удалить
     * @return объект ResponseDTO с идентификатором удаленного комментария и соответствующим сообщением
     */
    public ResponseDTO delete(Long id) {
        commentRepository.deleteById(id);
        return new ResponseDTO(id, "Comment was deleted");
    }
}