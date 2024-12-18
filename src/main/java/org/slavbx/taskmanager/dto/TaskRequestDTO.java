package org.slavbx.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.slavbx.taskmanager.model.Priority;
import org.slavbx.taskmanager.model.Status;

/**
 * Data Transfer Object для получения задачи от клиента
 */
@Builder
@Schema(description = "DTO для получения задачи")
public record TaskRequestDTO(
        @Schema(description = "Название задачи", example = "Задача для группы")
        @NotBlank(message = "Title cannot be empty")
        String title,
        String description,
        Long performerId,
        Priority priority,
        Status status
){}
