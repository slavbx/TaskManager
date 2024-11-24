package org.slavbx.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * Data Transfer Object для получения учётных данных от клиента при авторизации
 */
@Builder
@Schema(description = "Запрос на авторизацию")
public record SignInRequestDTO (

    @Schema(description = "Адрес электронной почты", example = "admin@admin.com")
    @Size(min = 5, max = 255, message = "Email address must be between 5 and 255 characters long")
    @NotBlank(message = "Email address cannot be empty")
    @Email(message = "Email address must be in the format user@example.com")
    String email,

    @Schema(description = "Пароль", example = "100")
    @Size(min = 3, max = 255, message = "Password length must be between 3 and 255 characters long")
    @NotBlank(message = "Password cannot be empty")
    String password
){}
