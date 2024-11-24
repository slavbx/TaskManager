package org.slavbx.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.exception.AlreadyExistsException;
import org.slavbx.taskmanager.exception.NotFoundException;
import org.slavbx.taskmanager.model.User;
import org.slavbx.taskmanager.repository.UserRepository;
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
public class UserService {
    private final UserRepository userRepository;

    /**
     * Получение пользователя по id.
     *
     * @param id уникальный идентификатор пользователя.
     * @return объект User, соответствующий указанному идентификатору.
     * @throws NotFoundException если пользователь не найден
     */
    public User getUserById(Long id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    /**
     * Получение пользователя по адресу электронной почты.
     * @param email адрес электронной почты пользователя.
     * @return объект User, соответствующий указанному адресу электронной почты.
     * @throws NotFoundException если пользователь не найден
     */
    public User getUserByEmail(String email) throws NotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found with email: " + email));
    }

    /**
     * Сохранение пользователя.
     * @param user объект User, который нужно сохранить.
     * @return сохраненный объект User
     */
    public User save(User user) {
        return userRepository.save(user);
    }


    /**
     * Создание нового пользователя.
     * @param user объект User, представляющий нового пользователя.
     * @return объект User, который был создан.
     * @throws AlreadyExistsException если пользователь с таким email или именем уже существует
     */
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
     * Получение сервиса для работы с пользователями.
     * @return объект UserDetailsService, в котором выполняется загрузка пользователя по email
     */
    public UserDetailsService userDetailsService() {
        return email -> getUserByEmail(email);
    }

    /**
     * Получение текущего аутентифицированного пользователя.
     * @return объект User, представляющий текущего пользователя
     */
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByEmail(email);
    }
}
