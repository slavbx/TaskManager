package org.slavbx.taskmanager.repository;

import org.slavbx.taskmanager.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для хранения сущности роли {@link Role}.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
