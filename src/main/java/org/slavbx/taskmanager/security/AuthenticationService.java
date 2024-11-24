package org.slavbx.taskmanager.security;

import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.dto.JwtAuthenticationResponseDTO;
import org.slavbx.taskmanager.dto.SignInRequestDTO;
import org.slavbx.taskmanager.dto.SignUpRequestDTO;
import org.slavbx.taskmanager.model.User;
import org.slavbx.taskmanager.service.RoleService;
import org.slavbx.taskmanager.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для аутентификации пользователей
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация нового пользователя и генерация JWT для аутентификации.
     *
     * @param request объект запроса на регистрацию, содержащий имя, email и пароль пользователя.
     * @return объект {@link JwtAuthenticationResponseDTO}, содержащий сгенерированный JWT
     */
    public JwtAuthenticationResponseDTO signUp(SignUpRequestDTO request) {
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(List.of(roleService.getUserRole()))
                .build();
        userService.create(user);
        String jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponseDTO(jwt);
    }

    /**
     * Аутентификация пользователя и генерация JWT.
     *
     * @param request объект запроса на вход, содержащий email и пароль пользователя.
     * @return объект {@link JwtAuthenticationResponseDTO}, содержащий сгенерированный JWT.
     * @throws RuntimeException если аутентификация не удалась
     */
    public JwtAuthenticationResponseDTO signIn(SignInRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        ));
        UserDetails user = userService
                .userDetailsService()
                .loadUserByUsername(request.email());
        String jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponseDTO(jwt);
    }
}
