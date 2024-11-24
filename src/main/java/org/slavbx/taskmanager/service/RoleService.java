package org.slavbx.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.model.Role;
import org.slavbx.taskmanager.repository.RoleRepository;
import org.springframework.stereotype.Service;

/**
 * Сервис для управления ролями пользователей.
 * Предоставляет функционал для получения роли пользователя из
 * репозитория ролей
 */
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    /**
     * Получение роли пользователя с именем "ROLE_USER".
     * @return объект Role, представляющий роль пользователя
     */
    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}
