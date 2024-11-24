package org.slavbx.taskmanager.service;

import org.slavbx.taskmanager.model.Role;

/**
 * Интерфейс для управления ролями пользователей.
 * Предоставляет функционал для получения роли пользователя из
 * репозитория ролей
 */
public interface RoleService {
    /**
     * Получение роли пользователя с именем "ROLE_USER".
     *
     * @return объект Role, представляющий роль пользователя
     */
    Role getUserRole();
}
