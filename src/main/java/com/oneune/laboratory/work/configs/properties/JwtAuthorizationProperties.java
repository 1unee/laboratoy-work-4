package com.oneune.laboratory.work.configs.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.time.Duration;

@ConfigurationProperties(prefix = "auth.jwt")
@PropertySource("classpath:application.yml")
public record JwtAuthorizationProperties(String secret, Duration lifetime) {
}
