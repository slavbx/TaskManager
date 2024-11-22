package org.slavbx.taskmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slavbx.taskmanager.TestcontainersConfiguration;
import org.slavbx.taskmanager.dto.SignInRequestDTO;
import org.slavbx.taskmanager.dto.TaskRequestDTO;
import org.slavbx.taskmanager.model.Priority;
import org.slavbx.taskmanager.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тестирование TaskController")
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@Import(TestcontainersConfiguration.class)
@SpringBootTest
class TaskControllerTest {
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
    void getTasksPage() {
    }

    @Test
    void getTaskById() {
    }

    @Test
    void createTask() throws Exception {
        TaskRequestDTO taskRequestDTO = TaskRequestDTO.builder()
                .title("task")
                .description("task_desc")
                .performerId(2L)
                .priority(Priority.MEDIUM)
                .status(Status.WAIT)
                .build();
        String taskRequestDTOJson = new ObjectMapper().writeValueAsString(taskRequestDTO);

        mockMvc.perform(post("/tasks/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskRequestDTOJson)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Task was created"));
    }

    @Test
    void updateTask() {
    }

    @Test
    void deleteTask() {
    }

    @Test
    void setTaskPriority() {
    }

    @Test
    void setTaskStatus() {
    }

    @Test
    void setPerformerToTask() {
    }
}