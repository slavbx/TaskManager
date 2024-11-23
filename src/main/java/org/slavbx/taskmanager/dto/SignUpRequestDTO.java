package org.slavbx.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Schema(description = "Запрос на регистрацию")
public record SignUpRequestDTO (

    @Schema(description = "Адрес электронной почты", example = "example@gmail.com")
    @Size(min = 5, max = 255, message = "Email must contain between 5 to 255 symbols")
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email must be in format user@example.com")
    String email,

    @Schema(description = "Имя пользователя", example = "James")
    @Size(min = 3, max = 50, message = "Username must contain between 3 to 50 symbols")
    @NotBlank(message = "Username cannot be empty")
    String name,

    @Schema(description = "Пароль", example = "my@123password")
    @Size(max = 255, message = "Password must contains not more than 255 symbols")
    @NotBlank(message = "Password cannot be empty")
    String password
){}
