package com.academy.microservices.order.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientBuilderConfig {
    @LoadBalanced
    @Bean
    RestClient.Builder restClientBuilder(){
        return RestClient.builder();
    }
}