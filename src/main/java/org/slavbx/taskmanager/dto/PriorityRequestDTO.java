package org.slavbx.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.slavbx.taskmanager.model.Priority;

@Builder
@Schema(description = "DTO для получения приоритета задачи")
public record PriorityRequestDTO (
        //@ValidEnum(enumClass = Priority.class)
        Priority priority
){}
