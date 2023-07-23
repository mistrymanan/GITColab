package com.gitcolab.services;
import com.gitcolab.dto.GithubAuthRequest;
import com.gitcolab.dto.MessageResponse;
import com.gitcolab.repositories.IntegrationRepository;
import com.gitcolab.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class GithubServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    IntegrationRepository integrationRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private GithubService githubService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        githubService = new GithubService(integrationRepository, userRepository);
    }

    @Test
    void testGetAccessToken_InvalidAuthCode() {
        GithubAuthRequest request = new GithubAuthRequest();
        request.setCode(null);

        ResponseEntity<?> response = githubService.getAccessToken(request);
        MessageResponse expectedResponse = new MessageResponse("Github auth code is invalid.");

        Assertions.assertEquals(400, response.getStatusCodeValue());
        Assertions.assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testGetAccessToken_InvalidAuthCodeWithEmail() {
        GithubAuthRequest request = new GithubAuthRequest();
        request.setCode(null);
        request.setEmail("example@example.com");

        ResponseEntity<?> response = githubService.getAccessToken(request);
        MessageResponse expectedResponse = new MessageResponse("Github auth code is invalid.");
        Assertions.assertEquals(400, response.getStatusCodeValue());
        Assertions.assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testGetAccessToken_InvalidEmail() {
        GithubAuthRequest request = new GithubAuthRequest();
        request.setCode("valid_code");
        request.setEmail(null);

        ResponseEntity<?> response = githubService.getAccessToken(request);
        MessageResponse expectedResponse = new MessageResponse("Github auth code is invalid.");

        Assertions.assertEquals(400, response.getStatusCodeValue());
        Assertions.assertEquals(expectedResponse, response.getBody());
    }
}
