package org.slavbx.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * Data Transfer Object для отправки ответа с ошибкой клиенту на его запрос
 */
@Builder
@Schema(description = "DTO для ответа с ошибкой на запрос")
public record ErrorResponseDTO(
    @Schema(description = "Сообщение", example = "Unsuccessful response")
    String message
){}