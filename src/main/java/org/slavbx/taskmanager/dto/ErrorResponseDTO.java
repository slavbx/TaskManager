package org.slavbx.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "DTO для ответа с ошибкой на запрос")
public record ErrorResponseDTO(
    String message
){}