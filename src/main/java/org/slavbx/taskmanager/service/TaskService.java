package org.slavbx.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.dto.ResponseDTO;
import org.slavbx.taskmanager.exception.NotFoundException;
import org.slavbx.taskmanager.model.Priority;
import org.slavbx.taskmanager.model.Status;
import org.slavbx.taskmanager.model.Task;
import org.slavbx.taskmanager.model.User;
import org.slavbx.taskmanager.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * Сервис для управления задачами.
 * Предоставляет функционал для работы с задачами, включая
 * получение задач с учетом пагинации и фильтрации, создание, обновление,
 * удаление задач, а также установку исполнителей, приоритетов и статусов задач
 */
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    /**
     * Получение задач с учетом пагинации и фильтрации.
     *
     * @param spec       спецификация для фильтрации задач
     * @param pageNumber номер страницы для возврата
     * @param pageSize   размер страницы (количество задач)
     * @return объект Page с задачами, соответствующими спецификации
     * @throws NotFoundException если задачи не найдены
     */
    public Page<Task> getTasksWithPagingAndFiltering(Specification<Task> spec, int pageNumber, int pageSize) throws NotFoundException {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Task> page = taskRepository.findAll(spec, pageable);
        if (page.isEmpty()) {
            throw new NotFoundException("Tasks not found");
        }
        return taskRepository.findAll(spec, pageable);
    }

    /**
     * Получение задачи по уникальному идентификатору.
     *
     * @param id уникальный идентификатор задачи
     * @return объект Task, соответствующий указанному идентификатору
     * @throws NotFoundException если задача не найдена
     */
    public Task getTaskById(Long id) throws NotFoundException {
        return taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found with id: " + id));
    }

    /**
     * Получение задачи по уникальному идентификатору.
     *
     * @param taskId уникальный идентификатор задачи
     * @return объект Task, соответствующий указанному идентификатору
     * @throws NotFoundException если задача не найдена
     */
    public ResponseDTO setPerformerToTask(Long taskId, Long performerId) throws NotFoundException {
        Task task = getTaskById(taskId);
        User user = userService.getUserById(performerId);
        task.setPerformer(user);
        taskRepository.save(task);
        return new ResponseDTO(task.getId(), "Task got new performer");
    }

    /**
     * Создание новой задачи.
     * @param task объект Task, представляющий новую задачу
     * @return объект ResponseDTO с идентификатором созданной задачи и сообщением о результате
     */
    public ResponseDTO create(Task task) {
        task.setAuthor(userService.getCurrentUser());
        Task savedTask = taskRepository.save(task);
        return new ResponseDTO(savedTask.getId(), "Task was created");
    }

    /**
     * Удаление задачи по уникальному идентификатору.
     *
     * @param id уникальный идентификатор задачи, которую нужно удалить
     * @return объект ResponseDTO с идентификатором удаленной задачи и сообщением о результате
     * @throws NotFoundException если задача не найдена
     */
    public ResponseDTO delete(Long id) throws NotFoundException {
        if (!taskRepository.existsById(id)) {
            throw new NotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
        return new ResponseDTO(id, "Task was deleted");
    }

    /**
     * Обновление существующей задачи.
     *
     * @param id   уникальный идентификатор задачи
     * @param task объект Task, содержащий новые данные для обновления
     * @return объект ResponseDTO с идентификатором обновленной задачи и сообщением о результате
     * @throws NotFoundException если задача не найдена
     */
    public ResponseDTO update(Long id, Task task) {
        Task updatedTask = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found with id: " + id));
        updatedTask.setDescription(task.getDescription());
        updatedTask.setPerformer(task.getPerformer());
        updatedTask.setTitle(task.getTitle());
        updatedTask.setPriority(task.getPriority());
        updatedTask.setStatus(task.getStatus());
        taskRepository.save(updatedTask);
        return new ResponseDTO(id, "Task was updated");
    }

    /**
     * Установка приоритета для задачи.
     *
     * @param id       уникальный идентификатор задачи
     * @param priority новый приоритет для задачи
     * @return объект ResponseDTO с идентификатором задачи и сообщением о результате
     * @throws NotFoundException если задача не найдена
     */
    public ResponseDTO setTaskPriority(Long id, Priority priority) throws NotFoundException {
        Task updatedTask = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found with id: " + id));
        updatedTask.setPriority(priority);
        taskRepository.save(updatedTask);
        return new ResponseDTO(id, "Priority was updated");
    }

    /**
     * Установка статуса для задачи.
     *
     * @param id     уникальный идентификатор задачи
     * @param status новый статус для задачи
     * @return объект ResponseDTO с идентификатором задачи и сообщением о результате
     * @throws NotFoundException если задача не найдена
     */
    public ResponseDTO setTaskStatus(Long id, Status status) throws NotFoundException {
        Task updatedTask = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found with id: " + id));
        updatedTask.setStatus(status);
        taskRepository.save(updatedTask);
        return new ResponseDTO(id, "Status was updated");
    }
}
