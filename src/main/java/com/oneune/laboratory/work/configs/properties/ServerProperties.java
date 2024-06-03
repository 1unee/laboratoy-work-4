package com.oneune.laboratory.work.configs.properties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties(prefix = "server")
@PropertySource("classpath:application.yml")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Data
public class ServerProperties {

    Servlet servlet;
    Api api;

    public record Servlet(String contextPath) {}
    public record Version(String users, String auth) {}
    public record Api(Version version) {}
}
