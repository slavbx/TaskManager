package org.slavbx.taskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slavbx.taskmanager.TestcontainersConfiguration;
import org.slavbx.taskmanager.dto.CommentRequestDTO;
import org.slavbx.taskmanager.dto.SignInRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тестирование CommentController")
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@Import(TestcontainersConfiguration.class)
@SpringBootTest
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private String token;

    @BeforeEach
    void setUp() throws Exception {
        SignInRequestDTO signInRequestDTO = SignInRequestDTO.builder()
                .email("admin@admin.com")
                .password("100")
                .build();
        String authJson = new ObjectMapper().writeValueAsString(signInRequestDTO);
        String response = mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        token = new ObjectMapper().readTree(response).get("token").asText();
    }

    @Test
    void getCommentsPage() throws Exception {
        mockMvc.perform(get("/comments")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.empty").value(false))
                .andExpect(jsonPath("$.numberOfElements").value(greaterThan(0)));
    }

    @Test
    void getCommentById() throws Exception {
        mockMvc.perform(get("/comments/1")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.text").value("I post this comment for task1 here"));
    }

    @Test
    void createComment() throws Exception {
        CommentRequestDTO commentRequestDTO = CommentRequestDTO.builder()
                .text("I post this comment for task1 here")
                .taskId(1L)
                .build();
        String commentRequestDTOJson = new ObjectMapper().writeValueAsString(commentRequestDTO);
        mockMvc.perform(post("/comments/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(commentRequestDTOJson)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Comment was created"));
    }

    @Test
    void deleteComment() throws Exception {
        mockMvc.perform(delete("/comments/2")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Comment was deleted"));
    }
}