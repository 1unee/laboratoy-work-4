package com.oneune.laboratory.work.configs.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties(prefix = "app")
@PropertySource("classpath:application.yml")
public record AppProperties(String version, String name, String description) {
}
