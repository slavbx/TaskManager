package org.slavbx.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.slavbx.taskmanager.model.Priority;
import org.slavbx.taskmanager.model.Status;

@Builder
@Schema(description = "Входной DTO для задач")
public record TaskRequestDTO(
    String title,
    String description,
    @Positive
    @NotEmpty
    Long performerId,
    Priority priority,
    Status status
){}
