package com.example.demo.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("application.properties")
@Configuration
@NoArgsConstructor
@Getter
public class SberModelConfig {
    @Value("${sber.chat_url}")
    private String urlChat;
    @Value("${sber.token_url}")
    private String tokenUrl;
    @Value("${sber.rq_uid}")
    private String rqUID;
    @Value("${sber.model}")
    private String model;
    @Value("${sber.authorization_key}")
    private String authorizationKey;
}
