package org.slavbx.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.slavbx.taskmanager.annotation.ValidEnum;
import org.slavbx.taskmanager.annotation.ValidEnumPriority;
import org.slavbx.taskmanager.model.Priority;

@Builder
@Schema(description = "DTO для получения приоритета задачи")
public record PriorityRequestDTO (

        @Schema(description = "Приоритет задачи", example = "MEDIUM")
        @NotNull(message = "Priority cannot be null")
        @ValidEnumPriority
        Priority priority
){}
