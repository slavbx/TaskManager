package org.slavbx.taskmanager.security;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию")
public class SignInRequestDTO {

    @Schema(description = "Адрес электронной почты", example = "example@gmail.com")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    @Schema(description = "Пароль", example = "my_1secret1_password")
    @Size(min = 3, max = 255, message = "Длина пароля должна быть от 3 до 255 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
