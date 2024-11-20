package org.slavbx.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.model.Role;
import org.slavbx.taskmanager.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}
