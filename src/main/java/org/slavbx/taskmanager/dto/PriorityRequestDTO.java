package org.slavbx.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.slavbx.taskmanager.model.Priority;

@Builder
@Schema(description = "DTO для получения приоритета задачи")
public record PriorityRequestDTO (

        @Schema(description = "Приоритет задачи", example = "MEDIUM")
        @NotNull(message = "Priority cannot be null")
        Priority priority
){}
