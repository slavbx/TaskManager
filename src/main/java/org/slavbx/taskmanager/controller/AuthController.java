package org.slavbx.taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.dto.ErrorResponseDTO;
import org.slavbx.taskmanager.security.AuthenticationService;
import org.slavbx.taskmanager.dto.JwtAuthenticationResponseDTO;
import org.slavbx.taskmanager.dto.SignInRequestDTO;
import org.slavbx.taskmanager.dto.SignUpRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Аутентификация")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно создан. Возвращает токен аутентификации",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = JwtAuthenticationResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "Пользователь с таким email уже существует",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping("/signup")
    public JwtAuthenticationResponseDTO signUp(@RequestBody @Valid SignUpRequestDTO request) {
        return authenticationService.signUp(request);
    }

    @Operation(summary = "Авторизация пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно авторизован. Возвращает токен аутентификации",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = JwtAuthenticationResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь с таким email не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
    })
    @PostMapping("/signin")
    public JwtAuthenticationResponseDTO signIn(@RequestBody @Valid SignInRequestDTO request) {
        return authenticationService.signIn(request);
    }
}
