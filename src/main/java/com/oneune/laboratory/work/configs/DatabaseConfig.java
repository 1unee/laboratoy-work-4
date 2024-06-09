package com.oneune.laboratory.work.configs;

import lombok.extern.log4j.Log4j2;
import org.h2.tools.Server;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
@Log4j2
public class DatabaseConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    @ConditionalOnProperty(prefix = "spring.datasource", name = "driver-class-name", havingValue = "org.h2.Driver")
    public Server h2Server() throws SQLException {
        log.info("TCP h2 server bean was autowired");
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }
}
