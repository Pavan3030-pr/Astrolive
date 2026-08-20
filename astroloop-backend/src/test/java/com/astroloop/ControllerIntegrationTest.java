package com.astroloop;

import com.astroloop.dto.LoginRequest;
import com.astroloop.dto.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class ControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_validRequest_returnsToken() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("integration@test.com");
        request.setPassword("test12345");
        request.setFirstName("Integration");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.email").value("integration@test.com"))
                .andExpect(jsonPath("$.firstName").value("Integration"))
                .andExpect(jsonPath("$.hasProfile").value(false));
    }

    @Test
    void register_duplicateEmail_returnsError() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("dup@test.com");
        request.setPassword("test12345");
        request.setFirstName("Dup");

        // First registration
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Duplicate
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Email already registered"));
    }

    @Test
    void login_validCredentials_returnsToken() throws Exception {
        // Register first
        RegisterRequest regReq = new RegisterRequest();
        regReq.setEmail("logintest@test.com");
        regReq.setPassword("test12345");
        regReq.setFirstName("Login");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(regReq)))
                .andExpect(status().isOk());

        // Login
        LoginRequest loginReq = new LoginRequest();
        loginReq.setEmail("logintest@test.com");
        loginReq.setPassword("test12345");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.email").value("logintest@test.com"));
    }

    @Test
    void login_wrongPassword_returnsUnauthorized() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("demo@astroloop.com");
        request.setPassword("wrongpassword");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void dashboard_withoutToken_returnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/dashboard"))
                .andExpect(status().isForbidden());
    }

    @Test
    void premiumProducts_withoutAuth_works() throws Exception {
        // Premium products require auth
        mockMvc.perform(get("/api/premium/products"))
                .andExpect(status().isForbidden());
    }

    @Test
    void healthCheck_works() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }
}
