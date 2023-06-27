package com.gitcolab.services;

import com.gitcolab.dto.*;
import com.gitcolab.entity.RefreshToken;
import com.gitcolab.entity.User;
import com.gitcolab.repositories.UserRepository;
import com.gitcolab.security.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private RefreshTokenService refreshTokenService;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(authenticationManager, userRepository, encoder, jwtUtils, refreshTokenService);

    }

    @Test
    public void testAuthenticateUser() {
        
    }

    @Test
    public void testRefreshToken() {
        // Set up mock behavior
        TokenRefreshRequest request = new TokenRefreshRequest("refresh-token");
        RefreshToken refreshToken = mock(RefreshToken.class);
        User user = mock(User.class);
        String newJwtToken = "new-jwt-token";
        TokenRefreshResponse expectedResponse = new TokenRefreshResponse(newJwtToken, request.getRefreshToken());

        when(refreshTokenService.findByToken(request.getRefreshToken())).thenReturn(Optional.of(refreshToken));
        when(refreshTokenService.verifyExpiration(refreshToken)).thenReturn(refreshToken);
        when(refreshToken.getUser()).thenReturn(user);
        when(user.getUsername()).thenReturn("username");
        when(jwtUtils.generateTokenFromUsername("username")).thenReturn(newJwtToken);
        
        ResponseEntity<?> response = userService.refreshtoken(request);
        
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testRegisterUser() {
        // Set up mock behavior
        RegisterUserRequest registerUserRequest = new RegisterUserRequest("username", "email", "password", "uFname", "uLname");
        User user = mock(User.class);
        MessageResponse expectedResponse = new MessageResponse("User registered successfully!");

        when(userRepository.existsByUsername(registerUserRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(registerUserRequest.getEmail())).thenReturn(false);
        when(encoder.encode(registerUserRequest.getPassword())).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenReturn(1);
        
        ResponseEntity<?> response = userService.registerUser(registerUserRequest);
        
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testResetPassword() {
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest("abc@email.com", "ABC@123", "ABC@123");
        User user = mock(User.class);

        when(userRepository.existsByEmail(resetPasswordRequest.getEmail())).thenReturn(false);
        when(userRepository.getUserByEmail(resetPasswordRequest.getEmail())).thenReturn(Optional.ofNullable(user));

        ResponseEntity<?> response1 = userService.resetPassword(resetPasswordRequest);
        assertEquals(new MessageResponse("Error: User is not exist in system!"), response1.getBody());

    }
}
