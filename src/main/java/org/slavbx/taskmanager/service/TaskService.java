package org.slavbx.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.dto.ResponseDTO;
import org.slavbx.taskmanager.exception.NotFoundException;
import org.slavbx.taskmanager.model.Priority;
import org.slavbx.taskmanager.model.Status;
import org.slavbx.taskmanager.model.Task;
import org.slavbx.taskmanager.model.User;
import org.slavbx.taskmanager.repository.TaskRepository;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    public Page<Task> getTasksWithPagingAndFiltering(Specification<Task> spec, int pageNumber, int pageSize) throws NotFoundException {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Task> page = taskRepository.findAll(spec, pageable);
        if (page.isEmpty()) {
            throw new NotFoundException("Tasks not found");
        }
        return taskRepository.findAll(spec, pageable);
    }

    public Task getTaskById(Long id) throws NotFoundException {
        return taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found with id: " + id));
    }

    public ResponseDTO setPerformerToTask(Long taskId, Long performerId) throws NotFoundException {
        Task task = getTaskById(taskId);
        User user = userService.getUserById(performerId);
        task.setPerformer(user);
        taskRepository.save(task);
        return new ResponseDTO(task.getId(), "Task got new performer");
    }

    public ResponseDTO create(Task task) {
        task.setAuthor(userService.getCurrentUser());
        Task savedTask = taskRepository.save(task);
        return new ResponseDTO(savedTask.getId(), "Task was created");
    }

    public ResponseDTO delete(Long id) throws NotFoundException {
        if (!taskRepository.existsById(id)) {
            throw new NotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
        return new ResponseDTO(id, "Task was deleted");
    }

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

    public ResponseDTO setTaskPriority(Long id, Priority priority) throws NotFoundException {
        Task updatedTask = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found with id: " + id));
        updatedTask.setPriority(priority);
        taskRepository.save(updatedTask);
        return new ResponseDTO(id, "Priority was updated");
    }

    public ResponseDTO setTaskStatus(Long id, Status status) throws NotFoundException {
        Task updatedTask = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found with id: " + id));
        updatedTask.setStatus(status);
        taskRepository.save(updatedTask);
        return new ResponseDTO(id, "Status was updated");
    }
}
