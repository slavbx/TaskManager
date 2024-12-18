package org.slavbx.taskmanager.repository;

import org.slavbx.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Репозиторий для хранения сущности задачи {@link Task}
 */
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
}
