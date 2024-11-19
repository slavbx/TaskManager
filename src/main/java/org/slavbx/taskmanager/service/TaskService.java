package org.slavbx.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.dto.ResponseDTO;
import org.slavbx.taskmanager.exception.NotFoundException;
import org.slavbx.taskmanager.model.Task;
import org.slavbx.taskmanager.model.User;
import org.slavbx.taskmanager.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    public Page<Task> getAllTasks(int page, int size){
        Sort sort = Sort.by(Sort.Direction.DESC, "priority");
        Pageable pageable = PageRequest.of(page, size, sort);
        return taskRepository.findAll(pageable);
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
        task.setId(null);
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
}
