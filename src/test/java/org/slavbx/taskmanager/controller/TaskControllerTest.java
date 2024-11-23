package org.slavbx.taskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slavbx.taskmanager.TestcontainersConfiguration;
import org.slavbx.taskmanager.dto.PriorityRequestDTO;
import org.slavbx.taskmanager.dto.SignInRequestDTO;
import org.slavbx.taskmanager.dto.StatusRequestDTO;
import org.slavbx.taskmanager.dto.TaskRequestDTO;
import org.slavbx.taskmanager.model.Priority;
import org.slavbx.taskmanager.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.greaterThan;

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
    void getTasksPage() throws Exception {
        mockMvc.perform(get("/tasks")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.empty").value(false))
                .andExpect(jsonPath("$.numberOfElements").value(greaterThan(0)));
    }

    @Test
    void getTaskById() throws Exception {
        mockMvc.perform(get("/tasks/1")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("task1_title"));
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
    void updateTask() throws Exception {
        TaskRequestDTO taskRequestDTO = TaskRequestDTO.builder()
                .title("task1_title")
                .description("task_updated_desc")
                .performerId(2L)
                .priority(Priority.MEDIUM)
                .status(Status.WAIT)
                .build();
        String taskRequestDTOJson = new ObjectMapper().writeValueAsString(taskRequestDTO);
        mockMvc.perform(put("/tasks/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskRequestDTOJson)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Task was updated"));
    }

    @Test
    void deleteTask() throws Exception {
        mockMvc.perform(delete("/tasks/2")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Task was deleted"));
    }

    @Test
    void setTaskPriority() throws Exception {
        PriorityRequestDTO priorityRequestDTO = PriorityRequestDTO.builder()
                .priority(Priority.HIGH)
                .build();
        String priorityRequestDTOJson = new ObjectMapper().writeValueAsString(priorityRequestDTO);
        mockMvc.perform(patch("/tasks/1/priority")
                .contentType(MediaType.APPLICATION_JSON)
                .content(priorityRequestDTOJson)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Priority was updated"));
    }

    @Test
    void setTaskStatus() throws Exception {
        StatusRequestDTO statusRequestDTO = StatusRequestDTO.builder()
                .status(Status.COMPLETE)
                .build();
        String statusRequestDTOJson = new ObjectMapper().writeValueAsString(statusRequestDTO);
        mockMvc.perform(patch("/tasks/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(statusRequestDTOJson)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Status was updated"));
    }

    @Test
    void setPerformerToTask() throws Exception {
        mockMvc.perform(post("/tasks/1/performer/2")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Task got new performer"));
    }
}