package org.slavbx.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.model.Task;
import org.slavbx.taskmanager.repository.TaskRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public Page<Task> getAllTasks(int page, int size){
        Sort sort = Sort.by(Sort.Direction.DESC, "priority");
        Pageable pageable = PageRequest.of(page, size, sort);
        return taskRepository.findAll(pageable);
    }

    public Task getTaskById(Long id) throws ChangeSetPersister.NotFoundException {
        return taskRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }
}
