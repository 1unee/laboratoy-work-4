package com.oneune.laboratory.work.configs.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties(prefix = "server")
@PropertySource("classpath:application.yml")
public record ServerProperties(Servlet servlet, Api api) {

    public record Servlet(String contextPath) {}
    public record Version(String users, String auth, String admin) {}
    public record Api(Version version) {}
}
