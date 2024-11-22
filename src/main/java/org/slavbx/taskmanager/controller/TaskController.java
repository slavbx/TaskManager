package org.slavbx.taskmanager.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.dto.*;
import org.slavbx.taskmanager.mapper.TaskMapper;
import org.slavbx.taskmanager.model.Task;
import org.slavbx.taskmanager.repository.specification.TaskSpecifications;
import org.slavbx.taskmanager.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Schema(description = "Создание ")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping
    public ResponseEntity<Page<TaskDTO>> getTasksPage(@RequestParam(defaultValue = "0") @Min(0) int pageNumber,
                                                      @RequestParam(defaultValue = "10") @Min(0) @Max(100) int pageSize,
                                                      Long authorId, Long performerId) {
        Specification<Task> spec = Specification.where(TaskSpecifications.hasAuthor(authorId)
                .and(TaskSpecifications.hasPerformer(performerId)));
        return ResponseEntity.ok(taskMapper.tasksPageToTaskDTOsPage(taskService.getTasksWithPagingAndFiltering(spec, pageNumber, pageSize)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable @Min(0) Long id) {
        return ResponseEntity.ok(taskMapper.taskToTaskDTO(taskService.getTaskById(id)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createTask(@RequestBody TaskRequestDTO taskRequestDTO) {
        return ResponseEntity.ok(taskService.create(taskMapper.taskRequestDTOToTask(taskRequestDTO)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateTask(@PathVariable @Min(0) Long id,
                                                  @RequestBody TaskRequestDTO taskRequestDTO) {
        return ResponseEntity.ok(taskService.update(id, taskMapper.taskRequestDTOToTask(taskRequestDTO)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteTask(@PathVariable @Min(0) Long id) {
        return ResponseEntity.ok(taskService.delete(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/priority")
    public ResponseEntity<ResponseDTO> setTaskPriority(@PathVariable @Min(0) Long id,
                                                       @RequestBody PriorityRequestDTO priorityRequestDTO) {
        return ResponseEntity.ok(taskService.setTaskPriority(id, priorityRequestDTO.priority()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @taskSecurity.hasPerformer(#id)")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ResponseDTO> setTaskStatus(@PathVariable @Min(0) Long id,
                                                     @RequestBody StatusRequestDTO statusRequestDTO) {
        return ResponseEntity.ok(taskService.setTaskStatus(id, statusRequestDTO.status()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{taskId}/setperformer/{performerId}")
    public ResponseEntity<ResponseDTO> setPerformerToTask(@PathVariable @Min(0) Long taskId,
                                                          @PathVariable @Min(0) Long performerId) {
        return ResponseEntity.ok(taskService.setPerformerToTask(taskId, performerId));
    }




}
