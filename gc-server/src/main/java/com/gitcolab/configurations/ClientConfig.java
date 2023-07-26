package com.gitcolab.configurations;

import com.gitcolab.services.AtlassianServiceClient;
import io.netty.channel.ChannelOption;
import lombok.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;


@Configuration
public class ClientConfig {
    @Bean
    public AtlassianServiceClient atlassianServiceClient(){
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 20000);
        WebClient webClient=WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("https://api.atlassian.com")
                .build();
        HttpServiceProxyFactory factory= HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).build();
        return factory.createClient(AtlassianServiceClient.class);
    }
}
