package org.slavbx.taskmanager.security;

import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.model.Task;
import org.slavbx.taskmanager.repository.TaskRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Компонент для проверки прав доступа к задачам
 */
@Component
@RequiredArgsConstructor
public class TaskSecurity {
    private final TaskRepository taskRepository;

    /**
     * Проверяет, является ли текущий пользователь исполнителем задачи.
     *
     * @param taskId уникальный идентификатор задачи
     * @return true, если текущий пользователь является исполнителем задачи; false в противном случае
     * @throws RuntimeException если задача с указанным идентификатором не найдена
     */
    public boolean hasPerformer(Long taskId) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        return  task.getPerformer() != null && task.getPerformer().getEmail().equals(currentUserEmail);
    }
}
