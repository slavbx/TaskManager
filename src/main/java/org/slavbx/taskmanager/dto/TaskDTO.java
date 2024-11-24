package org.slavbx.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.slavbx.taskmanager.model.Priority;
import org.slavbx.taskmanager.model.Status;

/**
 * Data Transfer Object для отправки задачи клиенту
 */
@Builder
@Schema(description = "DTO для отправки задачи")
public record TaskDTO (
    Long id,
    String title,
    String description,
    Long authorId,
    Long performerId,
    Priority priority,
    Status status
){}
