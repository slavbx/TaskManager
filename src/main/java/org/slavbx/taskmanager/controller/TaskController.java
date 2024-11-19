package org.slavbx.taskmanager.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.dto.TaskDTO;
import org.slavbx.taskmanager.mapper.TaskMapper;
import org.slavbx.taskmanager.service.TaskService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping
    public ResponseEntity<Page<TaskDTO>> getTasksPage(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                     @RequestParam(defaultValue = "10") @Min(0) @Max(100) int size) {
        return ResponseEntity.ok(taskMapper.tasksPageToTaskDTOsPage(taskService.getAllTasks(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable @Min(0) Long id) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(taskMapper.taskToTaskDTO(taskService.getTaskById(id)));
    }




}
