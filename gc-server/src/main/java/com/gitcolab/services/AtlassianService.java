package com.gitcolab.services;

import com.gitcolab.configurations.ClientConfig;
import com.gitcolab.dro.atlassian.GetAccessTokenRequest;
import com.gitcolab.dro.atlassian.GetAccessTokenResponse;
import com.gitcolab.dto.MessageResponse;
import com.gitcolab.entity.EnumIntegrationType;
import com.gitcolab.entity.Integration;
import com.gitcolab.repositories.IntegrationRepository;
import com.gitcolab.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AtlassianService {
    IntegrationRepository integrationRepository;
    UserRepository userRepository;

    private ClientConfig clientConfig;


    public AtlassianService(IntegrationRepository integrationRepository, UserRepository userRepository,ClientConfig clientConfig) {
        this.integrationRepository = integrationRepository;
        this.userRepository = userRepository;
        this.clientConfig=clientConfig;
    }

    public ResponseEntity<?> getAccessToken(GetAccessTokenRequest getAccessTokenRequest, UserDetailsImpl userDetails){
        if (getAccessTokenRequest.getCode() == null || getAccessTokenRequest.getCode().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Atlassian auth code is invalid."));
        }

        AtlassianServiceClient atlassianServiceClient = clientConfig.atlassianServiceClient();

        ResponseEntity<GetAccessTokenResponse> response=atlassianServiceClient.getAccessToken(getAccessTokenRequest);

        Optional<Integration> integration = integrationRepository.getByEmail(userDetails.getEmail(),EnumIntegrationType.ATLASSIAN);

        if(!integration.isPresent()) {
            integrationRepository.save(new Integration(EnumIntegrationType.ATLASSIAN, response.getBody().getAccess_token(), userDetails.getId()));
        } else {
           integrationRepository.update(new Integration(EnumIntegrationType.ATLASSIAN, response.getBody().getAccess_token(), userDetails.getId()));
        }
        return response;
    }

}
