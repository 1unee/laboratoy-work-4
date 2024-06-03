package com.oneune.laboratory.work.configs.properties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import java.time.Duration;

@ConfigurationProperties(prefix = "auth.jwt")
@PropertySource("classpath:application.yml")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Data
public class JwtAuthorizationProperties {
    String secret;
    Duration lifetime;
}
