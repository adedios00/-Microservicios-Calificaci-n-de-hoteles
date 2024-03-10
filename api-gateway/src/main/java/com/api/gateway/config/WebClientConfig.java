package com.api.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    public WebClient.Builder builder (){
        return WebClient.builder();
    }
}
