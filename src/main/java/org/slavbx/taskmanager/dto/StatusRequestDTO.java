package org.slavbx.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.slavbx.taskmanager.model.Status;

/**
 * Data Transfer Object для получения статуса задачи от клиента
 */
@Builder
@Schema(description = "DTO для получения статуса задачи")
public record StatusRequestDTO (

        @Schema(description = "Статус задачи", example = "WAIT")
        @NotNull(message = "Status cannot be null")
        Status status
){}