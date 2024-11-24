package org.slavbx.taskmanager.service;

import org.slavbx.taskmanager.dto.ResponseDTO;
import org.slavbx.taskmanager.exception.NotFoundException;
import org.slavbx.taskmanager.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

/**
 * Интерфейс для управления комментариями.
 * Предоставляет функционал для работы с комментариями, включая
 * получение комментариев с учетом пагинации и фильтрации, создание и удаление
 * комментариев
 */
public interface CommentService {
    /**
     * Получение комментариев с учетом пагинации и фильтрации.
     *
     * @param spec       спецификация для фильтрации комментариев
     * @param pageNumber номер страницы для возврата
     * @param pageSize   размер страницы (количество комментариев)
     * @return объект Page с комментариями, соответствующими спецификации
     */
    Page<Comment> getCommentsWithPagingAndFiltering(Specification<Comment> spec, int pageNumber, int pageSize);

    /**
     * Получение комментария по уникальному идентификатору.
     *
     * @param id уникальный идентификатор комментария
     * @return объект Comment, соответствующий указанному идентификатору
     * @throws NotFoundException если комментарий не найден
     */
    Comment getCommentById(Long id) throws NotFoundException;

    /**
     * Создание нового комментария.
     *
     * @param comment объект Comment, представляющий новый комментарий
     * @return объект ResponseDTO с идентификатором созданного комментария и соответствующим сообщением
     */
    ResponseDTO createComment(Comment comment);

    /**
     * Удаление комментария по уникальному идентификатору.
     *
     * @param id уникальный идентификатор комментария, который нужно удалить
     * @return объект ResponseDTO с идентификатором удаленного комментария и соответствующим сообщением
     */
    ResponseDTO delete(Long id);
}
