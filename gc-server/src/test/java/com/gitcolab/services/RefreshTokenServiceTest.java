package com.gitcolab.services;

import com.gitcolab.entity.RefreshToken;
import com.gitcolab.entity.User;
import com.gitcolab.exceptions.TokenRefreshException;
import com.gitcolab.repositories.RefreshTokenRepository;
import com.gitcolab.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
//@TestPropertySource(properties = "gitcolab.app.jwtRefreshExpirationMs=10000")
class RefreshTokenServiceTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
//        refreshTokenService.refreshTokenDurationMs = 60000L; // Set a test value for refreshTokenDurationMs
    }

    @Test
    void testFindByToken() {
        String token = "sampleToken";
        RefreshToken refreshToken = new RefreshToken();
        when(refreshTokenRepository.findByToken(token)).thenReturn(Optional.of(refreshToken));

        Optional<RefreshToken> result = refreshTokenService.findByToken(token);

        assertTrue(result.isPresent());
        assertEquals(refreshToken, result.get());
        verify(refreshTokenRepository).findByToken(token);
    }

//    @Test
//    void testCreateRefreshToken() {
//        Long userId = 1L;
//        RefreshToken refreshToken = new RefreshToken();
//        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
//        when(refreshTokenRepository.save(refreshToken)).thenReturn(1);
//
//        RefreshToken result = refreshTokenService.createRefreshToken(userId);
//        // check the problem here, which leads to exception
//
//        assertNotNull(result);
//        verify(userRepository).findById(userId);
//        verify(refreshTokenRepository).save(refreshToken);
//    }

    @Test
    void testVerifyExpiration() {
        RefreshToken validToken = createValidRefreshToken();
        RefreshToken expiredToken = createExpiredRefreshToken();

        RefreshToken resultValid = refreshTokenService.verifyExpiration(validToken);
        assertThrows(TokenRefreshException.class, () -> refreshTokenService.verifyExpiration(expiredToken));

        assertNotNull(resultValid);
        assertEquals(validToken, resultValid);
        assertThrows(TokenRefreshException.class, () -> refreshTokenService.verifyExpiration(expiredToken));
    }

    private RefreshToken createValidRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpiryDate(Instant.now().plusMillis(60000L));
        return refreshToken;
    }

    private RefreshToken createExpiredRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpiryDate(Instant.now().minusMillis(60000L));
        return refreshToken;
    }
}
