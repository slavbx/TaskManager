package org.slavbx.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.slavbx.taskmanager.model.Priority;

@Builder
@Schema(description = "Входной DTO для приоритета задачи")
public record PriorityRequestDTO (
        //@ValidEnum(enumClass = Priority.class)
        Priority priority
){}
