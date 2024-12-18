package org.slavbx.taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Управление задачами")
@SecurityRequirement(name = "bearerAuth")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @Operation(summary = "Получение списка задач постранично")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Получен список задач",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Задачи не найдены",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("")
    public ResponseEntity<Page<TaskDTO>> getTasksPage(@RequestParam(defaultValue = "0") @Min(0) int pageNumber,
                                                      @RequestParam(defaultValue = "10") @Min(0) @Max(100) int pageSize,
                                                      Long authorId, Long performerId) {
        Specification<Task> spec = Specification.where(TaskSpecifications.hasAuthor(authorId)
                .and(TaskSpecifications.hasPerformer(performerId)));
        return ResponseEntity.ok(taskMapper.tasksPageToTaskDTOsPage(taskService
                .getTasksWithPagingAndFiltering(spec, pageNumber, pageSize)));
    }

    @Operation(summary = "Получение задачи по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача получена",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TaskDTO.class))),
            @ApiResponse(responseCode = "404", description = "Задача не найдена",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable @Min(0) Long id) {
        return ResponseEntity.ok(taskMapper.taskToTaskDTO(taskService.getTaskById(id)));
    }

    @Operation(summary = "Создание задачи")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача создана",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TaskDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createTask(@RequestBody TaskRequestDTO taskRequestDTO) {
        return ResponseEntity.ok(taskService.create(taskMapper.taskRequestDTOToTask(taskRequestDTO)));
    }

    @Operation(summary = "Обновление задачи")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача обновлена",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Задача не найдена",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateTask(@PathVariable @Min(0) Long id,
                                                  @RequestBody TaskRequestDTO taskRequestDTO) {
        return ResponseEntity.ok(taskService.update(id, taskMapper.taskRequestDTOToTask(taskRequestDTO)));
    }

    @Operation(summary = "Удаление задачи")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача удалена",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Задача не найдена",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteTask(@PathVariable @Min(0) Long id) {
        return ResponseEntity.ok(taskService.delete(id));
    }

    @Operation(summary = "Установка приоритета задачи")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Приоритет установлен",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Задача не найдена",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/priority")
    public ResponseEntity<ResponseDTO> setTaskPriority(@PathVariable @Min(0) Long id,
                                                       @RequestBody PriorityRequestDTO priorityRequestDTO) {
        return ResponseEntity.ok(taskService.setTaskPriority(id, priorityRequestDTO.priority()));
    }

    @Operation(summary = "Установка статуса задачи")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус установлен",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Задача не найдена",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PreAuthorize("hasRole('ROLE_ADMIN') or @taskSecurity.hasPerformer(#id)")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ResponseDTO> setTaskStatus(@PathVariable @Min(0) Long id,
                                                     @RequestBody StatusRequestDTO statusRequestDTO) {
        return ResponseEntity.ok(taskService.setTaskStatus(id, statusRequestDTO.status()));
    }

    @Operation(summary = "Установка исполнителя задачи")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Исполнитель установлен",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Задача или исполнитель не найдены",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{taskId}/performer/{performerId}")
    public ResponseEntity<ResponseDTO> setPerformerToTask(@PathVariable @Min(0) Long taskId,
                                                          @PathVariable @Min(0) Long performerId) {
        return ResponseEntity.ok(taskService.setPerformerToTask(taskId, performerId));
    }
}
