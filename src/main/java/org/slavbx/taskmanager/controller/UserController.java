package org.slavbx.taskmanager.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.model.*;
import org.slavbx.taskmanager.repository.CommentRepository;
import org.slavbx.taskmanager.repository.RoleRepository;
import org.slavbx.taskmanager.repository.TaskRepository;
import org.slavbx.taskmanager.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;





    @GetMapping("/hello")
    public String hello() {
        Role roleAdmin = Role.builder().name("ADMIN1").build();
        Role roleUser = Role.builder().name("USER1").build();
        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);

        Set<Role> roles = new HashSet<>();
        roles.add(roleAdmin);
        roles.add(roleUser);
        User userAdmin = User.builder().email("admin@admin.com1").password("pswadmin").name("administrator1")
                .roles(roles).build();
        User userUser = User.builder().email("user@user.com1").password("pswuser").name("username1")
                .roles(new HashSet<>() {{ add(roleUser);}}).build();
        userRepository.save(userAdmin);
        userRepository.save(userUser);

        Task task1 = Task.builder().title("task1_title1").description("task1_desc").author(userAdmin).performer(userUser)
                .status(Status.WAIT).priority(Priority.MEDIUM).build();
        Task task2 = Task.builder().title("task2_title1").description("task2_desc").author(userAdmin).performer(userUser)
                .status(Status.WAIT).priority(Priority.MEDIUM).build();
        taskRepository.save(task1);
        taskRepository.save(task2);

        Comment comment1 = Comment.builder().user(userUser).task(task1).text("I post this comment for task1 here").build();
        Comment comment2 = Comment.builder().user(userUser).task(task2).text("I post this comment for task2 here").build();
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        return "Hello";
    }
}
