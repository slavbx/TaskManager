package org.slavbx.taskmanager.repository;

import org.slavbx.taskmanager.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
