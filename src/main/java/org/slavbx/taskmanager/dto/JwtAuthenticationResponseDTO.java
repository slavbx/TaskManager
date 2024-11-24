package org.slavbx.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * Data Transfer Object для отправки токена доступа клиенту при успешной авторизации
 */
@Builder
@Schema(description = "DTO для отправки токена доступа")
public record JwtAuthenticationResponseDTO (
        @Schema(description = "Токен доступа", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyMjUwNj...")
        String token
){}