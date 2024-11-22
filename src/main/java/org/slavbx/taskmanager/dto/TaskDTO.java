package org.slavbx.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slavbx.taskmanager.model.Priority;
import org.slavbx.taskmanager.model.Status;

import java.time.LocalDateTime;

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
