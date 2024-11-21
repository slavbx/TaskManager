package org.slavbx.taskmanager.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.dto.PriorityDTO;
import org.slavbx.taskmanager.dto.ResponseDTO;
import org.slavbx.taskmanager.dto.TaskDTO;
import org.slavbx.taskmanager.mapper.TaskMapper;
import org.slavbx.taskmanager.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Schema(description = "Создание ")
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
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable @Min(0) Long id) {
        return ResponseEntity.ok(taskMapper.taskToTaskDTO(taskService.getTaskById(id)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createTask(@RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.create(taskMapper.taskDTOToTask(taskDTO)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateTask(@PathVariable @Min(0) Long id, @RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.update(id, taskMapper.taskDTOToTask(taskDTO)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteTask(@PathVariable @Min(0) Long id) {
        return ResponseEntity.ok(taskService.delete(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/priority")
    public ResponseEntity<ResponseDTO> setTaskPriority(@PathVariable @Min(0) Long id, @RequestBody PriorityDTO priorityDTO) {
        return ResponseEntity.ok(taskService.setTaskPriority(id, priorityDTO.getPriority()));
    }







//    @PostMapping("/{taskId}/setperformer/{performerId}")
//    public ResponseEntity<ResponseDTO> setPerformerToTask(@PathVariable @Min(0) Long taskId,
//                                                          @PathVariable @Min(0) Long performerId) {
//        return ResponseEntity.ok(taskService.setPerformerToTask(taskId, performerId));
//    }




}
