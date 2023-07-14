package com.gitcolab.configurations;

import com.gitcolab.services.AtlassianServiceClient;
import lombok.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfig {
    @Bean
    AtlassianServiceClient atlassianServiceClient(){
        WebClient webClient=WebClient.builder()
                .baseUrl("https://api.atlassian.com")
                .build();
        HttpServiceProxyFactory factory= HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).build();
        return factory.createClient(AtlassianServiceClient.class);
    }
}
