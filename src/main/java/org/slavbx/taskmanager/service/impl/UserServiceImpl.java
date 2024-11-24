package org.slavbx.taskmanager.service.impl;

import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.exception.AlreadyExistsException;
import org.slavbx.taskmanager.exception.NotFoundException;
import org.slavbx.taskmanager.model.User;
import org.slavbx.taskmanager.repository.UserRepository;
import org.slavbx.taskmanager.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Сервис для управления пользователями.
 * Предоставляет функционал для работы с пользователями, включая
 * получение пользователей по id и email, создание пользователей,
 * а также получение текущего пользователя
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUserById(Long id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUserByEmail(String email) throws NotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found with email: " + email));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User create(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new AlreadyExistsException("User with email: " + user.getEmail() + " already exists");
        }
        if (userRepository.existsByName(user.getName())) {
            throw new AlreadyExistsException("User with name: " + user.getName() + " already exists");
        }
        return save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetailsService userDetailsService() {
        return email -> getUserByEmail(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByEmail(email);
    }
}
