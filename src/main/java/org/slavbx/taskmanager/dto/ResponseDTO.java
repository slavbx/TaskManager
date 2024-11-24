package org.slavbx.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * Data Transfer Object для отправки ответа клиенту на его запрос
 */
@Builder
@Schema(description = "DTO для ответа на запрос")
public record ResponseDTO (
    @Schema(description = "Идентификатор сущности, которая упоминается в ответе", example = "1")
    Long entityId,
    @Schema(description = "Сообщение", example = "Successful response")
    String message
){}