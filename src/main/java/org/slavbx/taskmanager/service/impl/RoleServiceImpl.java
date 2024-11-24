package org.slavbx.taskmanager.service.impl;

import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.model.Role;
import org.slavbx.taskmanager.repository.RoleRepository;
import org.slavbx.taskmanager.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * Сервис для управления ролями пользователей.
 * Предоставляет функционал для получения роли пользователя из
 * репозитория ролей
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}
