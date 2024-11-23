package org.slavbx.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "DTO для ответа на запрос")
public record ResponseDTO (
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Идентификатор сущности, которая упоминается в ответе", example = "1")
    Long entityId,
    @Schema(description = "Сообщение", example = "Entity was updated")
    String message
){}