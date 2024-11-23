package org.slavbx.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.exception.AlreadyExistsException;
import org.slavbx.taskmanager.exception.NotFoundException;
import org.slavbx.taskmanager.model.User;
import org.slavbx.taskmanager.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserById(Long id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    public User getUserByEmail(String email) throws NotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found with email: " + email));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User create(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new AlreadyExistsException("User with email: " + user.getEmail() + " already exists");
        }
        if (userRepository.existsByName(user.getName())) {
            throw new AlreadyExistsException("User with name: " + user.getName() + " already exists");
        }

        return save(user);
    }

    public UserDetailsService userDetailsService() {
        return this::getUserByEmail;
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByEmail(email);
    }
}
