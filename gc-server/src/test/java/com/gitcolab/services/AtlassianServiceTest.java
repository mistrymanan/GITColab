package com.gitcolab.services;

import com.gitcolab.configurations.ClientConfig;
import com.gitcolab.dro.atlassian.GetAccessTokenRequest;
import com.gitcolab.dro.atlassian.GetAccessTokenResponse;
import com.gitcolab.dto.MessageResponse;
import com.gitcolab.entity.EnumIntegrationType;
import com.gitcolab.entity.Integration;
import com.gitcolab.repositories.IntegrationRepository;
import com.gitcolab.repositories.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AtlassianServiceTest {
    
    @Mock
    private IntegrationRepository integrationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClientConfig clientConfig;

    @InjectMocks
    private AtlassianService atlassianService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAccessToken_WithValidCode() {
        
        GetAccessTokenRequest request = new GetAccessTokenRequest();
        request.setCode("valid_code");

        UserDetailsImpl userDetails = new UserDetailsImpl(1l,"manan","manan.mistry@dal.ca","password",null);


        GetAccessTokenResponse response = new GetAccessTokenResponse("sample_access_token", "3600", "read write");
        ResponseEntity<GetAccessTokenResponse> responseEntity = ResponseEntity.ok(response);

        
        AtlassianServiceClient atlassianServiceClient = mock(AtlassianServiceClient.class);
        when(clientConfig.atlassianServiceClient()).thenReturn(atlassianServiceClient);
        when(atlassianServiceClient.getAccessToken(request)).thenReturn(responseEntity);

        
        when(integrationRepository.getByEmail(userDetails.getEmail(), EnumIntegrationType.ATLASSIAN)).thenReturn(Optional.empty());

        
        ResponseEntity<?> result = atlassianService.getAccessToken(request, userDetails);

        
        verify(integrationRepository, times(1)).save(any(Integration.class));
        verify(integrationRepository, times(0)).update(any(Integration.class));

        
        assertEquals(responseEntity, result);
    }

    @Test
    public void testGetAccessToken_WithInvalidCode() {
        
        GetAccessTokenRequest request = new GetAccessTokenRequest();
        request.setCode("");

        UserDetailsImpl userDetails = new UserDetailsImpl(1l,"manan","manan.mistry@dal.ca","password",null);


        ResponseEntity<?> result = atlassianService.getAccessToken(request, userDetails);

        
        verify(integrationRepository, times(0)).save(any(Integration.class));
        verify(integrationRepository, times(0)).update(any(Integration.class));

        
        assertEquals(ResponseEntity.badRequest().body(new MessageResponse("Atlassian auth code is invalid.")), result);
    }

    @Test
    public void testGetAccessToken_WithExistingIntegration() {
        
        GetAccessTokenRequest request = new GetAccessTokenRequest();
        request.setCode("valid_code");

        UserDetailsImpl userDetails = new UserDetailsImpl(1l,"manan","manan.mistry@dal.ca","password",null);

        GetAccessTokenResponse response = new GetAccessTokenResponse("sample_access_token", "3600", "read write");
        ResponseEntity<GetAccessTokenResponse> responseEntity = ResponseEntity.ok(response);

        
        AtlassianServiceClient atlassianServiceClient = mock(AtlassianServiceClient.class);
        when(clientConfig.atlassianServiceClient()).thenReturn(atlassianServiceClient);
        when(atlassianServiceClient.getAccessToken(request)).thenReturn(responseEntity);

        
        Integration existingIntegration = new Integration(EnumIntegrationType.ATLASSIAN, "existing_token", userDetails.getId());
        when(integrationRepository.getByEmail(userDetails.getEmail(), EnumIntegrationType.ATLASSIAN)).thenReturn(Optional.of(existingIntegration));

        
        ResponseEntity<?> result = atlassianService.getAccessToken(request, userDetails);

        
        verify(integrationRepository, times(0)).save(any(Integration.class));
        verify(integrationRepository, times(1)).update(any(Integration.class));

        
        assertEquals(responseEntity, result);
    }
}