package org.slavbx.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.slavbx.taskmanager.model.Priority;
import org.slavbx.taskmanager.model.Status;

@Builder
@Schema(description = "DTO для отправки задач")
public record TaskDTO (
    Long id,
    String title,
    String description,
    Long authorId,
    Long performerId,
    Priority priority,
    Status status
){}
