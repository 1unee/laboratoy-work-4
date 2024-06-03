package com.oneune.laboratory.work.configs;

import com.oneune.laboratory.work.configs.properties.ServerProperties;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
@EnableConfigurationProperties(ServerProperties.class)
@Log4j2
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        log.info("Custom model mapper bean was autowired");
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        return modelMapper;
    }
}
