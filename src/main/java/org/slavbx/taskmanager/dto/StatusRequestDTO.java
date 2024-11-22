package org.slavbx.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.slavbx.taskmanager.model.Status;

@Builder
@Schema(description = "Входной DTO для статуса задачи")
public record StatusRequestDTO (
        //@ValidEnum(enumClass = Status.class)
        Status status
){}