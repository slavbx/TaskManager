package org.slavbx.taskmanager.security;

import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.model.Role;
import org.slavbx.taskmanager.model.User;
import org.slavbx.taskmanager.service.RoleService;
import org.slavbx.taskmanager.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponseDTO signUp(SignUpRequestDTO request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of(roleService.getUserRole()))
                .build();
        userService.create(user);
        String jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponseDTO(jwt);
    }

    public JwtAuthenticationResponseDTO signIn(SignInRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));
        UserDetails user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());
        String jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponseDTO(jwt);
    }
}
