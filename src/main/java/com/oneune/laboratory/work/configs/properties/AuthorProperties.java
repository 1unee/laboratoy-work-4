package com.oneune.laboratory.work.configs.properties;

import com.oneune.laboratory.work.store.dtos.PersonalDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties(prefix = "author")
@PropertySource("classpath:application.yml")
public record AuthorProperties(String username,
                               String email,
                               PersonalDto personal,
                               Addition addition) {

    public record Addition(University university) {}
    public record University(String group) {}
}
