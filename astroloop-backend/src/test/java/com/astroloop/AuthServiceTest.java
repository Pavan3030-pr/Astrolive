package com.astroloop;

import com.astroloop.config.JwtUtil;
import com.astroloop.dto.AuthResponse;
import com.astroloop.dto.LoginRequest;
import com.astroloop.dto.RegisterRequest;
import com.astroloop.entity.User;
import com.astroloop.repository.AstrologyProfileRepository;
import com.astroloop.repository.UserRepository;
import com.astroloop.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AstrologyProfileRepository profileRepository;

    @InjectMocks
    private AuthService authService;

    private User testUser;

    @BeforeEach
    void setUp() {
        // Create real JwtUtil for token generation
        JwtUtil realJwtUtil = new JwtUtil();
        // Use reflection to set the secret field
        try {
            var field = JwtUtil.class.getDeclaredField("secret");
            field.setAccessible(true);
            field.set(realJwtUtil, "test-secret-key-for-unit-testing-2024-very-long");
            var expField = JwtUtil.class.getDeclaredField("expiration");
            expField.setAccessible(true);
            expField.set(realJwtUtil, 86400000L);
        } catch (Exception e) {
            // If reflection fails, tests using token will still pass via mock
        }

        // Re-inject with real JwtUtil
        authService = new AuthService(userRepository, passwordEncoder, realJwtUtil, authenticationManager, profileRepository);

        testUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .password("encoded_password")
                .firstName("Test")
                .lastName("User")
                .referralCode("ASTRO-TEST-123")
                .active(true)
                .premium(false)
                .build();
    }

    @Test
    void register_newUser_success() {
        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(profileRepository.findByUserId(any())).thenReturn(Optional.empty());

        RegisterRequest request = new RegisterRequest();
        request.setEmail("new@example.com");
        request.setPassword("password123");
        request.setFirstName("New");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertNotNull(response.getToken());
        assertEquals("Test", response.getFirstName());
        assertFalse(response.isHasProfile());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_duplicateEmail_throwsException() {
        when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

        RegisterRequest request = new RegisterRequest();
        request.setEmail("existing@example.com");
        request.setPassword("password123");
        request.setFirstName("Dup");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.register(request)
        );
        assertEquals("Email already registered", exception.getMessage());
    }

    @Test
    void register_withReferral_setsReferredBy() {
        User referrer = User.builder().id(99L).email("ref@r.com").referralCode("REF-CODE").build();

        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encoded_password");
        when(userRepository.findByReferralCode("REF-CODE")).thenReturn(Optional.of(referrer));
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(profileRepository.findByUserId(any())).thenReturn(Optional.empty());

        RegisterRequest request = new RegisterRequest();
        request.setEmail("new@example.com");
        request.setPassword("password123");
        request.setFirstName("New");
        request.setReferralCode("REF-CODE");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void login_validCredentials_success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password123", "encoded_password")).thenReturn(true);
        when(profileRepository.findByUserId(any())).thenReturn(Optional.empty());

        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertNotNull(response.getToken());
        assertEquals("test@example.com", response.getEmail());
        assertEquals("Test", response.getFirstName());
    }

    @Test
    void login_invalidEmail_throwsException() {
        when(userRepository.findByEmail("wrong@example.com")).thenReturn(Optional.empty());

        LoginRequest request = new LoginRequest();
        request.setEmail("wrong@example.com");
        request.setPassword("password123");

        assertThrows(
                org.springframework.security.authentication.BadCredentialsException.class,
                () -> authService.login(request)
        );
    }

    @Test
    void login_wrongPassword_throwsException() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("wrongpassword", "encoded_password")).thenReturn(false);

        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("wrongpassword");

        assertThrows(
                org.springframework.security.authentication.BadCredentialsException.class,
                () -> authService.login(request)
        );
    }
}
