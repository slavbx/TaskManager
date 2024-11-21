package org.slavbx.taskmanager.repository;

import org.slavbx.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByName(String name);
    boolean existsByEmail(String email);
}
