package org.slavbx.taskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slavbx.taskmanager.TestcontainersConfiguration;
import org.slavbx.taskmanager.dto.SignInRequestDTO;
import org.slavbx.taskmanager.dto.SignUpRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование AuthController")
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@Import(TestcontainersConfiguration.class)
@SpringBootTest
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private String token;

    @Test
    void signUp() throws Exception {
        SignUpRequestDTO signUpRequestDTO = SignUpRequestDTO.builder()
                .email("usertest@test.com")
                .password("100")
                .name("usertest")
                .build();
        String authJson = new ObjectMapper().writeValueAsString(signUpRequestDTO);
        String response = mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        token = new ObjectMapper().readTree(response).get("token").asText();
        assertThat(token).isNotEmpty();
    }

    @Test
    void signInUserExists() throws Exception {
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
        assertThat(token).isNotEmpty();
    }

    @Test
    void signInUserNotExists() throws Exception {
        SignInRequestDTO signInRequestDTO = SignInRequestDTO.builder()
                .email("unknown@user.com")
                .password("100")
                .build();
        String authJson = new ObjectMapper().writeValueAsString(signInRequestDTO);
        mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found with email: " + signInRequestDTO.email()));
    }
}