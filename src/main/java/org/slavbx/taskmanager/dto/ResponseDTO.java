package org.slavbx.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "DTO для ответа на запрос")
public record ResponseDTO (
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    Long entityId,
    String message
){}