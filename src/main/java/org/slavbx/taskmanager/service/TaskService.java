package org.slavbx.taskmanager.service;

import org.slavbx.taskmanager.dto.ResponseDTO;
import org.slavbx.taskmanager.exception.NotFoundException;
import org.slavbx.taskmanager.model.Priority;
import org.slavbx.taskmanager.model.Status;
import org.slavbx.taskmanager.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

/**
 * Интерфейс для управления задачами.
 * Предоставляет функционал для работы с задачами, включая
 * получение задач с учетом пагинации и фильтрации, создание, обновление,
 * удаление задач, а также установку исполнителей, приоритетов и статусов задач
 */
public interface TaskService {
    /**
     * Получение задач с учетом пагинации и фильтрации.
     *
     * @param spec       спецификация для фильтрации задач
     * @param pageNumber номер страницы для возврата
     * @param pageSize   размер страницы (количество задач)
     * @return объект Page с задачами, соответствующими спецификации
     * @throws NotFoundException если задачи не найдены
     */
    Page<Task> getTasksWithPagingAndFiltering(Specification<Task> spec, int pageNumber, int pageSize) throws NotFoundException;

    /**
     * Получение задачи по уникальному идентификатору.
     *
     * @param id уникальный идентификатор задачи
     * @return объект Task, соответствующий указанному идентификатору
     * @throws NotFoundException если задача не найдена
     */
    Task getTaskById(Long id) throws NotFoundException;

    /**
     * Получение задачи по уникальному идентификатору.
     *
     * @param taskId уникальный идентификатор задачи
     * @return объект Task, соответствующий указанному идентификатору
     * @throws NotFoundException если задача не найдена
     */
    ResponseDTO setPerformerToTask(Long taskId, Long performerId) throws NotFoundException;

    /**
     * Создание новой задачи.
     *
     * @param task объект Task, представляющий новую задачу
     * @return объект ResponseDTO с идентификатором созданной задачи и сообщением о результате
     */
    ResponseDTO create(Task task);

    /**
     * Удаление задачи по уникальному идентификатору.
     *
     * @param id уникальный идентификатор задачи, которую нужно удалить
     * @return объект ResponseDTO с идентификатором удаленной задачи и сообщением о результате
     * @throws NotFoundException если задача не найдена
     */
    ResponseDTO delete(Long id) throws NotFoundException;

    /**
     * Обновление существующей задачи.
     *
     * @param id   уникальный идентификатор задачи
     * @param task объект Task, содержащий новые данные для обновления
     * @return объект ResponseDTO с идентификатором обновленной задачи и сообщением о результате
     * @throws NotFoundException если задача не найдена
     */
    ResponseDTO update(Long id, Task task);

    /**
     * Установка приоритета для задачи.
     *
     * @param id       уникальный идентификатор задачи
     * @param priority новый приоритет для задачи
     * @return объект ResponseDTO с идентификатором задачи и сообщением о результате
     * @throws NotFoundException если задача не найдена
     */
    ResponseDTO setTaskPriority(Long id, Priority priority) throws NotFoundException;

    /**
     * Установка статуса для задачи.
     *
     * @param id     уникальный идентификатор задачи
     * @param status новый статус для задачи
     * @return объект ResponseDTO с идентификатором задачи и сообщением о результате
     * @throws NotFoundException если задача не найдена
     */
    ResponseDTO setTaskStatus(Long id, Status status) throws NotFoundException;
}
